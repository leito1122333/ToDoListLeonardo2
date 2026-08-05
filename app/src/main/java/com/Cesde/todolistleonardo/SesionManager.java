package com.Cesde.todolistleonardo;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SesionManager(Context context) {

        preferences = context.getSharedPreferences(
                Constantes.PREF_NAME,
                Context.MODE_PRIVATE
        );

        editor = preferences.edit();
    }

    /**
     * Guarda la sesión del usuario.
     */
    public void guardarSesion(String uid,
                              String correo,
                              String nombre,
                              String rol) {

        editor.putString(Constantes.KEY_UID, uid);
        editor.putString(Constantes.KEY_CORREO, correo);
        editor.putString(Constantes.KEY_NOMBRE, nombre);
        editor.putString(Constantes.KEY_ROL, rol);

        editor.putBoolean(Constantes.KEY_LOGUEADO, true);

        editor.apply();
    }

    /**
     * Indica si existe una sesión activa.
     */
    public boolean haySesion() {

        return preferences.getBoolean(
                Constantes.KEY_LOGUEADO,
                false
        );
    }

    /**
     * Obtiene el UID.
     */
    public String getUid() {

        return preferences.getString(
                Constantes.KEY_UID,
                ""
        );
    }

    /**
     * Obtiene el correo.
     */
    public String getCorreo() {

        return preferences.getString(
                Constantes.KEY_CORREO,
                ""
        );
    }

    /**
     * Obtiene el nombre.
     */
    public String getNombre() {

        return preferences.getString(
                Constantes.KEY_NOMBRE,
                ""
        );
    }

    /**
     * Obtiene el rol.
     */
    public String getRol() {

        return preferences.getString(
                Constantes.KEY_ROL,
                ""
        );
    }

    /**
     * Elimina completamente la sesión.
     */
    public void cerrarSesion() {

        editor.clear();

        editor.apply();
    }

}