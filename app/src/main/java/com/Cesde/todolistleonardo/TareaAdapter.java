package com.Cesde.todolistleonardo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * ==========================================================
 * TareaAdapter
 * ==========================================================
 * Adapter encargado de mostrar las tareas del usuario.
 * Permite:
 * - Visualizar tareas.
 * - Cambiar estado Pendiente/Completado.
 * - Eliminar tareas.
 * ==========================================================
 */
public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private final Context context;
    private final ArrayList<Tarea> listaTareas;
    private final FirebaseFirestore db;

    public TareaAdapter(Context context,
                        ArrayList<Tarea> listaTareas) {

        this.context = context;
        this.listaTareas = listaTareas;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_tarea,
                        parent,
                        false
                );

        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TareaViewHolder holder,
            int position) {

        Tarea tarea = listaTareas.get(position);

        holder.tvTitulo.setText(tarea.getTitulo());

        holder.tvDescripcion.setText(tarea.getDescripcion());

        holder.tvEstado.setText(
                "Estado: " + tarea.getEstado()
        );

        // Evita disparar el listener al reciclar la vista
        holder.checkEstado.setOnCheckedChangeListener(null);

        boolean completada =
                Constantes.ESTADO_COMPLETADO.equals(
                        tarea.getEstado()
                );

        holder.checkEstado.setChecked(completada);

        if (completada) {

            holder.tvEstado.setTextColor(
                    Color.parseColor("#2E7D32")
            );

            holder.tvTitulo.setPaintFlags(
                    holder.tvTitulo.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG
            );

        } else {

            holder.tvEstado.setTextColor(
                    Color.parseColor("#D84315")
            );

            holder.tvTitulo.setPaintFlags(
                    holder.tvTitulo.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );

        }

        holder.checkEstado.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    cambiarEstado(
                            tarea,
                            isChecked
                    );

                });

        holder.btnEliminar.setOnClickListener(v ->

                eliminarTarea(tarea)

        );

    }
    @Override
    public int getItemCount() {

        return listaTareas.size();

    }


    /**
     * ==========================================================
     * Cambia el estado de la tarea en Firestore.
     * ==========================================================
     */
    private void cambiarEstado(
            Tarea tarea,
            boolean completada
    ) {

        if (tarea.getId() == null || tarea.getId().isEmpty()) {

            Toast.makeText(
                    context,
                    "No se pudo actualizar la tarea",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        String nuevoEstado;

        if (completada) {

            nuevoEstado = Constantes.ESTADO_COMPLETADO;

        } else {

            nuevoEstado = Constantes.ESTADO_PENDIENTE;

        }

        db.collection(Constantes.COLLECTION_TAREAS)
                .document(tarea.getId())
                .update(
                        Constantes.CAMPO_ESTADO,
                        nuevoEstado
                )
                .addOnSuccessListener(unused -> {

                    // No actualizamos la lista manualmente.
                    // El SnapshotListener de MainActivity
                    // refresca automáticamente el RecyclerView.

                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            context,
                            "Error actualizando estado",
                            Toast.LENGTH_SHORT
                    ).show();

                });

    }


    /**
     * ==========================================================
     * Elimina una tarea.
     * ==========================================================
     */
    private void eliminarTarea(
            Tarea tarea
    ) {

        if (tarea.getId() == null || tarea.getId().isEmpty()) {

            Toast.makeText(
                    context,
                    "No se pudo eliminar la tarea",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        db.collection(Constantes.COLLECTION_TAREAS)
                .document(tarea.getId())
                .delete()
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            context,
                            "Tarea eliminada",
                            Toast.LENGTH_SHORT
                    ).show();

                })
                .addOnFailureListener(e ->

                        Toast.makeText(
                                context,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show()

                );

    }
    /**
     * ==========================================================
     * ViewHolder
     * ==========================================================
     */
    static class TareaViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkEstado;

        TextView tvTitulo;
        TextView tvDescripcion;
        TextView tvEstado;

        Button btnEliminar;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);

            checkEstado =
                    itemView.findViewById(R.id.checkEstado);

            tvTitulo =
                    itemView.findViewById(R.id.tvTitulo);

            tvDescripcion =
                    itemView.findViewById(R.id.tvDescripcion);

            tvEstado =
                    itemView.findViewById(R.id.tvEstado);

            btnEliminar =
                    itemView.findViewById(R.id.btnEliminar);
        }

    }

}