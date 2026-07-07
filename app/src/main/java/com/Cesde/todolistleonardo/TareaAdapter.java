package com.Cesde.todolistleonardo;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolder> {

    private List<Tarea> lista;
    private Context context;

    public TareaAdapter(Context context, List<Tarea> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Tarea tarea = lista.get(position);

        holder.tvId.setText(tarea.getId());
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvEstado.setText(tarea.getEstado());

        holder.itemView.setOnClickListener(v -> {
            mostrarDialogEditar(tarea);
        });

        holder.itemView.setOnLongClickListener(v -> {

            eliminarTarea(tarea);

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private void mostrarDialogEditar(Tarea tarea) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);

        builder.setTitle("Editar Tarea");

        LinearLayout layout =
                new LinearLayout(context);

        layout.setOrientation(
                LinearLayout.VERTICAL);

        EditText etTitulo =
                new EditText(context);

        EditText etDescripcion =
                new EditText(context);

        etTitulo.setText(tarea.getTitulo());
        etDescripcion.setText(
                tarea.getDescripcion());

        layout.addView(etTitulo);
        layout.addView(etDescripcion);

        builder.setView(layout);

        builder.setPositiveButton("Guardar",
                (dialog, which) -> {

                    Map<String, Object> datos =
                            new HashMap<>();

                    datos.put(
                            "titulo",
                            etTitulo.getText()
                                    .toString());

                    datos.put(
                            "descripcion",
                            etDescripcion.getText()
                                    .toString());

                    FirebaseFirestore.getInstance()
                            .collection("tareas")
                            .document(tarea.getId())
                            .update(datos)
                            .addOnSuccessListener(unused -> {

                                tarea.setTitulo(
                                        etTitulo.getText()
                                                .toString());

                                tarea.setDescripcion(
                                        etDescripcion.getText()
                                                .toString());

                                notifyDataSetChanged();

                                Toast.makeText(
                                        context,
                                        "Tarea actualizada",
                                        Toast.LENGTH_SHORT
                                ).show();
                            });
                });

        builder.setNegativeButton(
                "Cancelar",
                null);

        builder.show();
    }

    private void eliminarTarea(Tarea tarea) {

        new AlertDialog.Builder(context)
                .setTitle("Eliminar")
                .setMessage("¿Desea eliminar esta tarea?")
                .setPositiveButton("Sí",
                        (dialog, which) -> {

                            FirebaseFirestore.getInstance()
                                    .collection("tareas")
                                    .document(tarea.getId())
                                    .delete()
                                    .addOnSuccessListener(
                                            unused -> {

                                                lista.remove(
                                                        tarea);

                                                notifyDataSetChanged();

                                                Toast.makeText(
                                                        context,
                                                        "Tarea eliminada",
                                                        Toast.LENGTH_SHORT
                                                ).show();
                                            });
                        })
                .setNegativeButton(
                        "No",
                        null)
                .show();
    }

    static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvId;
        TextView tvTitulo;
        TextView tvDescripcion;
        TextView tvEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId =
                    itemView.findViewById(
                            R.id.tvItemId);

            tvTitulo =
                    itemView.findViewById(
                            R.id.tvItemTitulo);

            tvDescripcion =
                    itemView.findViewById(
                            R.id.tvItemDescripcion);

            tvEstado =
                    itemView.findViewById(
                            R.id.tvItemEstado);
        }
    }
}