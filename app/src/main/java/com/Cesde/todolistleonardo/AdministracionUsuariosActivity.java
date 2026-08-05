package com.Cesde.todolistleonardo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * ==========================================================
 * AdministracionUsuariosActivity
 * ==========================================================
 *
 * Panel administrativo.
 *
 * Funcionalidades:
 * - Listar usuarios en tiempo real.
 * - Cambiar roles.
 * - Eliminar usuarios.
 * - Regresar a la lista de tareas.
 *
 * ==========================================================
 */
public class AdministracionUsuariosActivity extends AppCompatActivity
        implements UsuarioAdapter.OnUsuarioListener {


    private RecyclerView rvUsuarios;


    private Button btnVolverTareas;


    private final List<Usuario> listaUsuarios =
            new ArrayList<>();


    private UsuarioAdapter adapter;


    private FirebaseFirestore db;


    private ListenerRegistration listenerRegistration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(
                R.layout.activity_administracion_usuarios
        );


        inicializarFirebase();


        inicializarVistas();


        configurarRecyclerView();


        configurarEventos();


        escucharUsuarios();


    }



    /**
     * Inicializa Firestore.
     */
    private void inicializarFirebase() {


        db =
                FirebaseFirestore.getInstance();


    }



    /**
     * Inicializa componentes visuales.
     */
    private void inicializarVistas() {


        rvUsuarios =
                findViewById(
                        R.id.rvUsuarios
                );


        btnVolverTareas =
                findViewById(
                        R.id.btnVolverTareas
                );


    }



    /**
     * Configura RecyclerView.
     */
    private void configurarRecyclerView() {


        rvUsuarios.setLayoutManager(
                new LinearLayoutManager(this)
        );


        adapter =
                new UsuarioAdapter(
                        listaUsuarios,
                        this
                );


        rvUsuarios.setAdapter(adapter);


        rvUsuarios.setHasFixedSize(true);


    }



    /**
     * Eventos de botones.
     */
    private void configurarEventos() {


        btnVolverTareas.setOnClickListener(
                v -> volverTareas()
        );


    }



    /**
     * Regresa a MainActivity.
     */
    private void volverTareas() {


        Intent intent =
                new Intent(
                        AdministracionUsuariosActivity.this,
                        MainActivity.class
                );


        startActivity(intent);


        finish();


    }



    /**
     * Escucha cambios en usuarios.
     */
    private void escucharUsuarios() {


        listenerRegistration =

                db.collection(
                                Constantes.COLLECTION_USUARIOS
                        )

                        .addSnapshotListener(

                                (
                                        QuerySnapshot snapshots,
                                        FirebaseFirestoreException error
                                ) -> {


                                    if (error != null) {


                                        Toast.makeText(

                                                this,

                                                "Error al cargar usuarios",

                                                Toast.LENGTH_SHORT

                                        ).show();


                                        return;

                                    }



                                    if (snapshots == null) {

                                        return;

                                    }



                                    listaUsuarios.clear();



                                    for (
                                            DocumentChange cambio :
                                            snapshots.getDocumentChanges()
                                    ) {


                                        Usuario usuario =

                                                cambio.getDocument()
                                                        .toObject(
                                                                Usuario.class
                                                        );


                                        listaUsuarios.add(usuario);


                                    }



                                    adapter.notifyDataSetChanged();



                                }

                        );


    }

    /**
     * Cambiar rol del usuario.
     */
    @Override
    public void onCambiarRol(
            @NonNull Usuario usuario
    ) {


        String nuevoRol;


        if (
                usuario.getRol()
                        .equals(Constantes.ROL_ADMIN)
        ) {


            nuevoRol =
                    Constantes.ROL_EMPLEADO;


        } else {


            nuevoRol =
                    Constantes.ROL_ADMIN;


        }



        new AlertDialog.Builder(this)

                .setTitle(
                        "Cambiar Rol"
                )

                .setMessage(

                        "¿Desea cambiar el rol del usuario?\n\n"

                                + usuario.getCorreo()

                                + "\n\nNuevo rol: "

                                + nuevoRol

                )

                .setPositiveButton(
                        "Sí",
                        (dialog, which) -> {


                            cambiarRolFirestore(
                                    usuario,
                                    nuevoRol
                            );


                        }
                )

                .setNegativeButton(
                        "Cancelar",
                        null
                )

                .show();



    }



    /**
     * Actualiza el rol en Firestore.
     */
    private void cambiarRolFirestore(
            Usuario usuario,
            String nuevoRol
    ) {


        db.collection(
                        Constantes.COLLECTION_USUARIOS
                )

                .document(
                        usuario.getUid()
                )

                .update(
                        Constantes.CAMPO_ROL,
                        nuevoRol
                )

                .addOnSuccessListener(
                        unused -> {


                            Toast.makeText(

                                    this,

                                    "Rol actualizado correctamente",

                                    Toast.LENGTH_SHORT

                            ).show();



                        }
                )

                .addOnFailureListener(
                        e -> {


                            Toast.makeText(

                                    this,

                                    e.getMessage(),

                                    Toast.LENGTH_LONG

                            ).show();



                        }
                );


    }



    /**
     * Eliminar usuario.
     */
    @Override
    public void onEliminarUsuario(
            @NonNull Usuario usuario
    ) {


        new AlertDialog.Builder(this)

                .setTitle(
                        "Eliminar Usuario"
                )

                .setMessage(

                        "¿Desea eliminar el usuario?\n\n"

                                + usuario.getCorreo()

                )

                .setPositiveButton(
                        "Eliminar",
                        (dialog, which) -> {


                            eliminarUsuarioFirestore(
                                    usuario
                            );


                        }
                )

                .setNegativeButton(
                        "Cancelar",
                        null
                )

                .show();


    }



    /**
     * Elimina documento del usuario
     * en Firestore.
     */
    private void eliminarUsuarioFirestore(
            Usuario usuario
    ) {


        db.collection(
                        Constantes.COLLECTION_USUARIOS
                )

                .document(
                        usuario.getUid()
                )

                .delete()

                .addOnSuccessListener(
                        unused -> {


                            Toast.makeText(

                                    this,

                                    "Usuario eliminado correctamente",

                                    Toast.LENGTH_SHORT

                            ).show();



                        }
                )

                .addOnFailureListener(
                        e -> {


                            Toast.makeText(

                                    this,

                                    "Error: "
                                            + e.getMessage(),

                                    Toast.LENGTH_LONG

                            ).show();



                        }
                );


    }



    /**
     * Libera listener de Firestore.
     */
    @Override
    protected void onDestroy() {


        super.onDestroy();



        if (
                listenerRegistration != null
        ) {


            listenerRegistration.remove();


        }


    }



    /**
     * Navegación con botón atrás.
     */
    @Override
    public void onBackPressed() {


        volverTareas();


    }


}