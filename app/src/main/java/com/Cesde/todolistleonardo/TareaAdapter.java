package com.Cesde.todolistleonardo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 *
 * Adapter encargado de mostrar las tareas
 * del usuario autenticado.
 *
 * Firestore maneja la actualización de datos mediante
 * SnapshotListener desde MainActivity.
 *
 * Por eso este Adapter solamente muestra y ejecuta acciones.
 *
 * ==========================================================
 */
public class TareaAdapter
        extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {



    private final Context context;


    private final ArrayList<Tarea> listaTareas;



    private final FirebaseFirestore db;




    public TareaAdapter(
            Context context,
            ArrayList<Tarea> listaTareas
    ) {


        this.context = context;

        this.listaTareas = listaTareas;

        this.db = FirebaseFirestore.getInstance();

    }







    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {



        View vista =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(
                                R.layout.item_tarea,
                                parent,
                                false
                        );



        return new TareaViewHolder(vista);


    }







    @Override
    public void onBindViewHolder(
            @NonNull TareaViewHolder holder,
            int position
    ) {



        Tarea tarea =
                listaTareas.get(position);




        holder.tvTitulo.setText(
                tarea.getTitulo()
        );



        holder.tvDescripcion.setText(
                tarea.getDescripcion()
        );



        holder.tvEstado.setText(
                "Estado: "
                        + tarea.getEstado()
        );





        holder.btnEliminar.setOnClickListener(v -> {


            eliminarTarea(tarea);



        });



    }







    @Override
    public int getItemCount() {


        return listaTareas.size();


    }







    /**
     * Elimina una tarea desde Firestore.
     *
     * IMPORTANTE:
     * No modificamos listaTareas aquí.
     *
     * MainActivity recibe el cambio mediante
     * SnapshotListener y actualiza RecyclerView.
     */
    private void eliminarTarea(
            Tarea tarea
    ) {



        if(tarea.getId() == null
                ||
                tarea.getId().isEmpty()) {



            Toast.makeText(

                    context,

                    "No se pudo identificar la tarea",

                    Toast.LENGTH_SHORT

            ).show();



            return;


        }







        db.collection(
                        Constantes.COLLECTION_TAREAS
                )

                .document(
                        tarea.getId()
                )

                .delete()



                .addOnSuccessListener(unused -> {



                    Toast.makeText(

                            context,

                            "Tarea eliminada",

                            Toast.LENGTH_SHORT

                    ).show();



                })



                .addOnFailureListener(e -> {



                    Toast.makeText(

                            context,

                            "Error eliminando tarea: "
                                    + e.getMessage(),

                            Toast.LENGTH_LONG

                    ).show();



                });



    }









    /**
     * ViewHolder
     */
    static class TareaViewHolder
            extends RecyclerView.ViewHolder {



        TextView tvTitulo;

        TextView tvDescripcion;

        TextView tvEstado;


        Button btnEliminar;





        public TareaViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);



            tvTitulo =
                    itemView.findViewById(
                            R.id.tvTitulo
                    );



            tvDescripcion =
                    itemView.findViewById(
                            R.id.tvDescripcion
                    );



            tvEstado =
                    itemView.findViewById(
                            R.id.tvEstado
                    );



            btnEliminar =
                    itemView.findViewById(
                            R.id.btnEliminar
                    );


        }

    }


}