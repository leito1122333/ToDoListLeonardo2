package com.Cesde.todolistleonardo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ==========================================================
 * Adapter encargado de mostrar los usuarios registrados.
 *
 * Este Adapter NO modifica Firestore directamente.
 * Toda la lógica de negocio será manejada desde
 * AdministracionUsuariosActivity mediante la interfaz
 * OnUsuarioListener.
 * ==========================================================
 */
public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private final List<Usuario> listaUsuarios;
    private final OnUsuarioListener listener;

    /**
     * Constructor.
     *
     * @param listaUsuarios Lista de usuarios.
     * @param listener Eventos del Adapter.
     */
    public UsuarioAdapter(List<Usuario> listaUsuarios,
                          OnUsuarioListener listener) {

        this.listaUsuarios = listaUsuarios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_usuario,
                        parent,
                        false
                );

        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull UsuarioViewHolder holder,
            int position) {

        Usuario usuario = listaUsuarios.get(position);

        holder.tvCorreo.setText(usuario.getCorreo());

        holder.tvRol.setText(
                "Rol: " + usuario.getRol()
        );

        /*
         * No permitir modificar
         * al administrador principal.
         */
        if (usuario.getCorreo().equalsIgnoreCase(Constantes.ADMIN_EMAIL)) {

            holder.btnEliminarUsuario.setEnabled(false);

            holder.btnCambiarRol.setEnabled(false);

        } else {

            holder.btnEliminarUsuario.setEnabled(true);

            holder.btnCambiarRol.setEnabled(true);

        }

        holder.btnCambiarRol.setOnClickListener(v -> {

            if (listener != null) {

                listener.onCambiarRol(usuario);

            }

        });

        holder.btnEliminarUsuario.setOnClickListener(v -> {

            if (listener != null) {

                listener.onEliminarUsuario(usuario);

            }

        });

    }

    @Override
    public int getItemCount() {

        return listaUsuarios.size();

    }

    /**
     * ==========================================================
     * ViewHolder
     * ==========================================================
     */
    static class UsuarioViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvCorreo;
        TextView tvRol;

        Button btnCambiarRol;
        Button btnEliminarUsuario;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCorreo =
                    itemView.findViewById(R.id.tvCorreo);

            tvRol =
                    itemView.findViewById(R.id.tvRol);

            btnCambiarRol =
                    itemView.findViewById(R.id.btnCambiarRol);

            btnEliminarUsuario =
                    itemView.findViewById(R.id.btnEliminarUsuario);

        }

    }

    /**
     * ==========================================================
     * Interface utilizada para comunicar el Adapter con la
     * Activity sin acoplar el código.
     * ==========================================================
     */
    public interface OnUsuarioListener {

        /**
         * Cambiar rol.
         */
        void onCambiarRol(Usuario usuario);

        /**
         * Eliminar usuario.
         */
        void onEliminarUsuario(Usuario usuario);

    }

}