package com.Cesde.todolistleonardo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etTitulo, etDescripcion;
    Button btnGuardar, btnCerrarSesion;

    RecyclerView rvTareas;

    ArrayList<Tarea> listaTareas;
    TareaAdapter adapter;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        rvTareas = findViewById(R.id.rvTareas);

        listaTareas = new ArrayList<>();

        adapter = new TareaAdapter(
                MainActivity.this,
                listaTareas
        );

        rvTareas.setLayoutManager(
                new LinearLayoutManager(this)
        );

        rvTareas.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> guardarTarea());

        btnCerrarSesion.setOnClickListener(v -> {

            auth.signOut();

            startActivity(
                    new Intent(
                            MainActivity.this,
                            LoginActivity.class
                    )
            );

            finish();
        });

        cargarTareas();
    }

    private void guardarTarea() {

        String titulo =
                etTitulo.getText().toString().trim();

        String descripcion =
                etDescripcion.getText().toString().trim();

        if (titulo.isEmpty() || descripcion.isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Map<String, Object> tarea = new HashMap<>();

        tarea.put("titulo", titulo);
        tarea.put("descripcion", descripcion);
        tarea.put("estado", "Pendiente");

        db.collection("tareas")
                .add(tarea)
                .addOnSuccessListener(documentReference -> {

                    Toast.makeText(
                            MainActivity.this,
                            "Tarea guardada",
                            Toast.LENGTH_SHORT
                    ).show();

                    etTitulo.setText("");
                    etDescripcion.setText("");

                    cargarTareas();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            MainActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();

                });
    }

    private void cargarTareas() {

        db.collection("tareas")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    listaTareas.clear();

                    for (DocumentSnapshot doc :
                            queryDocumentSnapshots) {

                        Tarea tarea = new Tarea();

                        tarea.setId(doc.getId());

                        tarea.setTitulo(
                                doc.getString("titulo"));

                        tarea.setDescripcion(
                                doc.getString("descripcion"));

                        tarea.setEstado(
                                doc.getString("estado"));

                        listaTareas.add(tarea);
                    }

                    adapter.notifyDataSetChanged();
                });
    }
}