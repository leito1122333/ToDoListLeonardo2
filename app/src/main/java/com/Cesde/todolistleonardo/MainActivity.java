package com.Cesde.todolistleonardo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * ==========================================================
 * MainActivity
 * ==========================================================
 *
 * Pantalla principal de la aplicación.
 *
 * Funcionalidades:
 *
 * - Crear tareas.
 * - Mostrar únicamente tareas del usuario autenticado.
 * - Actualización en tiempo real con Firestore.
 * - Control de roles.
 * - Acceso al panel administrativo.
 * - Cierre de sesión.
 *
 * ==========================================================
 */
public class MainActivity extends AppCompatActivity {


    private EditText etTitulo;
    private EditText etDescripcion;


    private Button btnGuardar;
    private Button btnCerrarSesion;
    private Button btnPanelUsuarios;
    private Button btnVolverLogin;


    private RecyclerView rvTareas;


    private ArrayList<Tarea> listaTareas;

    private TareaAdapter adapter;


    private FirebaseFirestore db;

    private FirebaseAuth auth;


    private SesionManager sesionManager;


    private String rolUsuario;


    private ListenerRegistration listenerRegistration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);



        inicializarFirebase();


        inicializarVistas();


        inicializarRecyclerView();


        configurarPermisos();


        configurarEventos();


        cargarTareas();


    }



    /**
     * Inicializa Firebase y sesión.
     */
    private void inicializarFirebase() {


        db = FirebaseFirestore.getInstance();


        auth = FirebaseAuth.getInstance();


        sesionManager =
                new SesionManager(this);



        rolUsuario =
                sesionManager.getRol();


    }



    /**
     * Relaciona los elementos XML.
     */
    private void inicializarVistas() {


        etTitulo =
                findViewById(R.id.etTitulo);



        etDescripcion =
                findViewById(R.id.etDescripcion);



        btnGuardar =
                findViewById(R.id.btnGuardar);



        btnCerrarSesion =
                findViewById(R.id.btnCerrarSesion);



        btnPanelUsuarios =
                findViewById(R.id.btnPanelUsuarios);



        rvTareas =
                findViewById(R.id.rvTareas);



    }



    /**
     * Configuración RecyclerView.
     */
    private void inicializarRecyclerView() {



        listaTareas =
                new ArrayList<>();



        adapter =
                new TareaAdapter(
                        this,
                        listaTareas
                );



        rvTareas.setLayoutManager(
                new LinearLayoutManager(this)
        );



        rvTareas.setHasFixedSize(true);



        rvTareas.setAdapter(adapter);



    }



    /**
     * Configuración de permisos por rol.
     */
    private void configurarPermisos() {



        if(Constantes.ROL_ADMIN.equals(rolUsuario)) {



            btnPanelUsuarios.setVisibility(
                    View.VISIBLE
            );



        } else {



            btnPanelUsuarios.setVisibility(
                    View.GONE
            );


        }


    }



    /**
     * Configura eventos de botones.
     */
    private void configurarEventos() {


        btnGuardar.setOnClickListener(v ->
                guardarTarea()
        );



        btnCerrarSesion.setOnClickListener(v ->
                cerrarSesion()
        );



        btnPanelUsuarios.setOnClickListener(v ->
                abrirPanelAdministracion()
        );


    }




    /**
     * Abre el panel administrativo.
     */
    private void abrirPanelAdministracion() {


        Intent intent =
                new Intent(
                        MainActivity.this,
                        AdministracionUsuariosActivity.class
                );



        startActivity(intent);


    }





    /**
     * Guarda una tarea asociada al usuario actual.
     */
    private void guardarTarea() {


        String titulo =
                etTitulo.getText()
                        .toString()
                        .trim();



        String descripcion =
                etDescripcion.getText()
                        .toString()
                        .trim();




        if(titulo.isEmpty()
                || descripcion.isEmpty()) {



            Toast.makeText(

                    this,

                    "Complete todos los campos",

                    Toast.LENGTH_SHORT

            ).show();



            return;


        }





        String uidUsuario =
                sesionManager.getUid();




        if(uidUsuario == null) {



            Toast.makeText(

                    this,

                    "Sesión inválida",

                    Toast.LENGTH_SHORT

            ).show();



            return;


        }





        Map<String,Object> tarea =
                new HashMap<>();




        tarea.put(

                Constantes.CAMPO_UID,

                uidUsuario

        );



        tarea.put(

                Constantes.CAMPO_TITULO,

                titulo

        );



        tarea.put(

                Constantes.CAMPO_DESCRIPCION,

                descripcion

        );



        tarea.put(

                Constantes.CAMPO_ESTADO,

                Constantes.ESTADO_PENDIENTE

        );






        db.collection(
                        Constantes.COLLECTION_TAREAS
                )

                .add(tarea)



                .addOnSuccessListener(
                        documentReference -> {



                            Toast.makeText(

                                    this,

                                    "Tarea guardada",

                                    Toast.LENGTH_SHORT

                            ).show();




                            etTitulo.setText("");



                            etDescripcion.setText("");



                        }



                )



                .addOnFailureListener(e -> {



                    Toast.makeText(

                            this,

                            "Error: "
                                    + e.getMessage(),

                            Toast.LENGTH_LONG

                    ).show();



                });



    }






    /**
     * Carga únicamente las tareas
     * pertenecientes al usuario autenticado.
     */
    private void cargarTareas() {



        String uidUsuario =
                sesionManager.getUid();




        if(uidUsuario == null) {


            Toast.makeText(

                    this,

                    "Usuario no encontrado",

                    Toast.LENGTH_SHORT

            ).show();



            return;


        }






        listenerRegistration = db

                .collection(
                        Constantes.COLLECTION_TAREAS
                )


                .whereEqualTo(

                        Constantes.CAMPO_UID,

                        uidUsuario

                )



                .addSnapshotListener(
                        (snapshots, error) -> {



                            if(error != null) {



                                Toast.makeText(

                                        this,

                                        "Error cargando tareas",

                                        Toast.LENGTH_SHORT

                                ).show();



                                return;


                            }





                            if(snapshots == null){

                                return;

                            }





                            listaTareas.clear();





                            for(DocumentSnapshot documento :
                                    snapshots.getDocuments()) {



                                Tarea tarea =
                                        documento.toObject(
                                                Tarea.class
                                        );



                                if(tarea != null) {



                                    tarea.setId(
                                            documento.getId()
                                    );



                                    listaTareas.add(
                                            tarea
                                    );


                                }


                            }





                            adapter.notifyDataSetChanged();



                        });



    }
    /**
     * Cierra la sesión actual.
     */
    private void cerrarSesion() {


        if(listenerRegistration != null) {


            listenerRegistration.remove();


        }



        auth.signOut();



        sesionManager.cerrarSesion();




        Intent intent =
                new Intent(
                        MainActivity.this,
                        LoginActivity.class
                );



        intent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
        );



        startActivity(intent);



        finish();



    }





    /**
     * Libera el listener de Firestore
     * cuando la Activity se destruye.
     */
    @Override
    protected void onDestroy() {


        super.onDestroy();



        if(listenerRegistration != null) {


            listenerRegistration.remove();


        }


    }



}