package com.Cesde.todolistleonardo;

/**
 * ==========================================================
 * Clase: Constantes
 * ==========================================================
 * Contiene todas las constantes utilizadas en la aplicación.
 */
public final class Constantes {

    private Constantes() {
        // Evita instanciar esta clase
    }

    //==========================================================
    // SharedPreferences
    //==========================================================

    public static final String PREF_NAME = "SesionUsuario";

    public static final String KEY_UID = "uid";

    public static final String KEY_CORREO = "correo";

    public static final String KEY_NOMBRE = "nombre";

    public static final String KEY_ROL = "rol";

    public static final String KEY_LOGUEADO = "logueado";

    //==========================================================
    // Colecciones Firestore
    //==========================================================

    public static final String COLLECTION_TAREAS = "tareas";

    public static final String COLLECTION_USUARIOS = "usuarios";

    //==========================================================
    // Campos Firestore
    //==========================================================

    public static final String CAMPO_UID = "uid";

    public static final String CAMPO_CORREO = "correo";

    public static final String CAMPO_NOMBRE = "nombre";

    public static final String CAMPO_ROL = "rol";

    public static final String CAMPO_TITULO = "titulo";

    public static final String CAMPO_DESCRIPCION = "descripcion";

    public static final String CAMPO_ESTADO = "estado";

    //==========================================================
    // Roles
    //==========================================================

    public static final String ROL_ADMIN = "Administrador";

    public static final String ROL_EMPLEADO = "Empleado";

    //==========================================================
    // Administrador Principal
    //==========================================================

    public static final String ADMIN_EMAIL =
            "1246652347leonardo@gmail.com";

    //==========================================================
    // Estados de las tareas
    //==========================================================

    public static final String ESTADO_PENDIENTE = "Pendiente";

    public static final String ESTADO_EN_PROCESO = "En Proceso";

    public static final String ESTADO_COMPLETADA = "Completada";

}