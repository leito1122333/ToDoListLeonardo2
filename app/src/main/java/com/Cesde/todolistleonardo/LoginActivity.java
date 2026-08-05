package com.Cesde.todolistleonardo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etPassword;

    private Button btnLogin;
    private Button btnIrRegistro;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SesionManager sesionManager;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        sesionManager = new SesionManager(this);

        if (mAuth.getCurrentUser() != null && sesionManager.haySesion()) {

            Intent intent = new Intent(
                    LoginActivity.this,
                    MainActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sesionManager = new SesionManager(this);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        btnLogin.setOnClickListener(v -> iniciarSesion());

        btnIrRegistro.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    RegistroActivity.class
            );

            startActivity(intent);

        });
    }

    /**
     * Inicia sesión y obtiene el rol del usuario desde Firestore.
     */
    private void iniciarSesion() {

        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (correo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese un correo",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (password.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese una contraseña",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        btnLogin.setEnabled(false);

        mAuth.signInWithEmailAndPassword(correo, password)

                .addOnSuccessListener(authResult -> {

                    String uid = authResult.getUser().getUid();

                    db.collection(Constantes.COLLECTION_USUARIOS)
                            .document(uid)
                            .get()

                            .addOnSuccessListener(documentSnapshot -> {

                                if (!documentSnapshot.exists()) {

                                    btnLogin.setEnabled(true);

                                    Toast.makeText(
                                            LoginActivity.this,
                                            "No existe información del usuario.",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    mAuth.signOut();

                                    return;
                                }

                                Usuario usuario =
                                        documentSnapshot.toObject(Usuario.class);

                                if (usuario == null) {

                                    btnLogin.setEnabled(true);

                                    Toast.makeText(
                                            LoginActivity.this,
                                            "Error al cargar los datos del usuario.",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    mAuth.signOut();

                                    return;
                                }

                                sesionManager.guardarSesion(
                                        usuario.getUid(),
                                        usuario.getCorreo(),
                                        usuario.getNombre(),
                                        usuario.getRol()
                                );

                                Toast.makeText(
                                        LoginActivity.this,
                                        "Bienvenido " + usuario.getRol(),
                                        Toast.LENGTH_SHORT
                                ).show();

                                Intent intent = new Intent(
                                        LoginActivity.this,
                                        MainActivity.class
                                );

                                intent.setFlags(
                                        Intent.FLAG_ACTIVITY_NEW_TASK |
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                );

                                startActivity(intent);

                                finish();

                            })

                            .addOnFailureListener(e -> {

                                btnLogin.setEnabled(true);

                                Toast.makeText(
                                        LoginActivity.this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();

                            });

                })

                .addOnFailureListener(e -> {

                    btnLogin.setEnabled(true);

                    Toast.makeText(
                            LoginActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                });

    }

}