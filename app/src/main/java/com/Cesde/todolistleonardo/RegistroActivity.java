package com.Cesde.todolistleonardo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * ==========================================================
 * RegistroActivity
 * ==========================================================
 *
 * Permite crear usuarios nuevos.
 *
 * Funcionalidades:
 * - Registro mediante Firebase Authentication.
 * - Creación del documento usuario en Firestore.
 * - Asignación de rol inicial.
 * - Navegación hacia Login.
 *
 * ==========================================================
 */
public class RegistroActivity extends AppCompatActivity {


    private EditText etCorreoRegistro;

    private EditText etPasswordRegistro;


    private Button btnRegistrar;

    private Button btnIrLogin;


    private FirebaseAuth auth;

    private FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_registro
        );


        inicializarFirebase();

        inicializarVistas();

        configurarEventos();

    }



    /**
     * Inicializa Firebase.
     */
    private void inicializarFirebase() {


        auth =
                FirebaseAuth.getInstance();


        db =
                FirebaseFirestore.getInstance();


    }



    /**
     * Inicializa componentes visuales.
     */
    private void inicializarVistas() {


        etCorreoRegistro =
                findViewById(
                        R.id.etCorreoRegistro
                );


        etPasswordRegistro =
                findViewById(
                        R.id.etPasswordRegistro
                );


        btnRegistrar =
                findViewById(
                        R.id.btnRegistrar
                );


        btnIrLogin =
                findViewById(
                        R.id.btnIrLogin
                );


    }



    /**
     * Configura eventos de botones.
     */
    private void configurarEventos() {


        btnRegistrar.setOnClickListener(
                v -> registrarUsuario()
        );



        btnIrLogin.setOnClickListener(
                v -> {


                    Intent intent =
                            new Intent(
                                    RegistroActivity.this,
                                    LoginActivity.class
                            );


                    startActivity(intent);


                    finish();


                }
        );


    }



    /**
     * Registro del usuario.
     */
    private void registrarUsuario() {


        String correo =
                etCorreoRegistro
                        .getText()
                        .toString()
                        .trim();


        String password =
                etPasswordRegistro
                        .getText()
                        .toString()
                        .trim();



        if (correo.isEmpty()
                || password.isEmpty()) {


            Toast.makeText(

                    this,

                    "Complete todos los campos",

                    Toast.LENGTH_SHORT

            ).show();


            return;

        }



        auth.createUserWithEmailAndPassword(
                        correo,
                        password
                )

                .addOnCompleteListener(task -> {


                    if (task.isSuccessful()) {


                        FirebaseUser usuario =
                                auth.getCurrentUser();


                        if (usuario != null) {


                            guardarUsuarioFirestore(
                                    usuario
                            );


                        }


                    } else {


                        Toast.makeText(

                                this,

                                task.getException()
                                        .getMessage(),

                                Toast.LENGTH_LONG

                        ).show();


                    }


                });


    }

    /**
     * Guarda la información del usuario
     * en la colección usuarios de Firestore.
     */
    private void guardarUsuarioFirestore(
            FirebaseUser usuario
    ) {


        Map<String, Object> datosUsuario =
                new HashMap<>();


        datosUsuario.put(
                Constantes.CAMPO_UID,
                usuario.getUid()
        );


        datosUsuario.put(
                Constantes.CAMPO_CORREO,
                usuario.getEmail()
        );


        datosUsuario.put(
                Constantes.CAMPO_ROL,
                Constantes.ROL_EMPLEADO
        );


        datosUsuario.put(
                Constantes.CAMPO_NOMBRE,
                "Usuario"
        );



        db.collection(
                        Constantes.COLLECTION_USUARIOS
                )

                .document(
                        usuario.getUid()
                )

                .set(datosUsuario)

                .addOnSuccessListener(
                        unused -> {


                            Toast.makeText(

                                    this,

                                    "Usuario registrado correctamente",

                                    Toast.LENGTH_SHORT

                            ).show();



                            Intent intent =
                                    new Intent(
                                            RegistroActivity.this,
                                            LoginActivity.class
                                    );



                            startActivity(intent);



                            finish();



                        }

                )

                .addOnFailureListener(
                        e -> {


                            Toast.makeText(

                                    this,

                                    "Error guardando usuario: "
                                            + e.getMessage(),

                                    Toast.LENGTH_LONG

                            ).show();



                        }
                );


    }



    /**
     * Evita regresar a registro si el usuario
     * ya completó el proceso.
     */
    @Override
    public void onBackPressed() {


        Intent intent =
                new Intent(
                        RegistroActivity.this,
                        LoginActivity.class
                );


        startActivity(intent);


        finish();


    }


}