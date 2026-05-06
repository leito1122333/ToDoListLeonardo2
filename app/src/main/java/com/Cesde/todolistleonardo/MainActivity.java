package com.Cesde.todolistleonardo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etId, etTitulo, etDescripcion, etEstado;
    private Button btnRegistrar, btnBuscar, btnEditar, btnBorrar, btnVerTodos;

    private RecyclerView rvTareas;
    private TareaAdapter adaptador;
    private List<Tarea> listaTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etEstado = findViewById(R.id.etEstado);

        rvTareas = findViewById(R.id.rvTareas);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnVerTodos = findViewById(R.id.btnVerTodos);

        rvTareas.setLayoutManager(new LinearLayoutManager(this));

        btnRegistrar.setOnClickListener(v -> registrarTarea());
        btnBuscar.setOnClickListener(v -> buscarTarea());
        btnEditar.setOnClickListener(v -> modificarTarea());
        btnBorrar.setOnClickListener(v -> borrarTarea());
        btnVerTodos.setOnClickListener(v -> cargarListaTareas());
    }

    private void registrarTarea(){
        String titulo = etTitulo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String estado = etEstado.getText().toString();

        if (!titulo.isEmpty() && !descripcion.isEmpty() && !estado.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("titulo", titulo);
            registro.put("descripcion", descripcion);
            registro.put("estado", estado);

            db.insert("tareas", null, registro);
            db.close();

            limpiarCampos();
            Toast.makeText(this, "Tarea registrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarTarea(){
        String id = etId.getText().toString();

        if (!id.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase db = admin.getReadableDatabase();

            Cursor fila = db.rawQuery("SELECT titulo, descripcion, estado FROM tareas WHERE id=" + id, null);

            if (fila.moveToFirst()){
                etTitulo.setText(fila.getString(0));
                etDescripcion.setText(fila.getString(1));
                etEstado.setText(fila.getString(2));
            } else {
                Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
            }

            fila.close();
            db.close();
        }
    }

    private void modificarTarea(){
        String id = etId.getText().toString();
        String titulo = etTitulo.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String estado = etEstado.getText().toString();

        if (!id.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("titulo", titulo);
            valores.put("descripcion", descripcion);
            valores.put("estado", estado);

            db.update("tareas", valores, "id=" + id, null);
            db.close();

            limpiarCampos();
        }
    }

    private void borrarTarea(){
        String id = etId.getText().toString();

        if (!id.isEmpty()){
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase db = admin.getWritableDatabase();

            db.delete("tareas", "id=" + id, null);
            db.close();

            limpiarCampos();
        }
    }

    private void cargarListaTareas(){
        listaTareas = new ArrayList<>();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("SELECT id, titulo, descripcion, estado FROM tareas", null);

        while (fila.moveToNext()){
            listaTareas.add(new Tarea(
                    fila.getInt(0),
                    fila.getString(1),
                    fila.getString(2),
                    fila.getString(3)
            ));
        }

        fila.close();
        db.close();

        adaptador = new TareaAdapter(listaTareas);
        rvTareas.setAdapter(adaptador);
    }

    private void limpiarCampos(){
        etId.setText("");
        etTitulo.setText("");
        etDescripcion.setText("");
        etEstado.setText("");
    }
}