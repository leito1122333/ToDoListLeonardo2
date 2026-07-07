package com.Cesde.todolistleonardo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo;
    private EditText etPassword;

    private Button btnLogin;
    private Button btnIrRegistro;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            startActivity(
                    new Intent(this, MainActivity.class)
            );

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        btnLogin.setOnClickListener(v -> iniciarSesion());

        btnIrRegistro.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            LoginActivity.this,
                            RegistroActivity.class
                    );

            startActivity(intent);
        });
    }

    private void iniciarSesion() {

        String correo =
                etCorreo.getText().toString().trim();

        String password =
                etPassword.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(
                        correo,
                        password
                )
                .addOnSuccessListener(authResult -> {

                    Toast.makeText(
                            this,
                            "Inicio de sesión correcto",
                            Toast.LENGTH_SHORT
                    ).show();

                    startActivity(
                            new Intent(
                                    this,
                                    MainActivity.class
                            )
                    );

                    finish();
                })
                .addOnFailureListener(e -> {

                    btnLogin.setEnabled(true);

                    Toast.makeText(
                            this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}