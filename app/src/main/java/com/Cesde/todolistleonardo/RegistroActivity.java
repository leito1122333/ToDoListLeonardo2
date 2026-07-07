package com.Cesde.todolistleonardo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {

    private EditText etCorreoRegistro;
    private EditText etPasswordRegistro;

    private Button btnRegistrar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        etCorreoRegistro = findViewById(R.id.etCorreoRegistro);
        etPasswordRegistro = findViewById(R.id.etPasswordRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {

        String correo = etCorreoRegistro.getText().toString().trim();
        String password = etPasswordRegistro.getText().toString().trim();

        if (correo.isEmpty()) {
            Toast.makeText(this,
                    "Ingrese un correo",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this,
                    "Ingrese una contraseña",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this,
                    "La contraseña debe tener mínimo 6 caracteres",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegistrar.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(correo, password)
                .addOnSuccessListener(authResult -> {

                    Toast.makeText(this,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT).show();

                    finish();
                })
                .addOnFailureListener(e -> {

                    btnRegistrar.setEnabled(true);

                    Toast.makeText(this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}