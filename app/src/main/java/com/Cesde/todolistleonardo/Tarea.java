package com.Cesde.todolistleonardo;

/**
 * ==========================================================
 * Modelo Tarea
 * ==========================================================
 * Representa una tarea almacenada en Cloud Firestore.
 *
 * Cada tarea pertenece a un único usuario mediante el campo
 * uid, permitiendo que cada usuario únicamente visualice
 * sus propias tareas.
 * ==========================================================
 */
public class Tarea {

    /**
     * Identificador del documento en Firestore.
     */
    private String id;

    /**
     * UID del propietario de la tarea.
     */
    private String uid;

    /**
     * Título de la tarea.
     */
    private String titulo;

    /**
     * Descripción de la tarea.
     */
    private String descripcion;

    /**
     * Estado actual de la tarea.
     */
    private String estado;

    /**
     * Constructor vacío requerido por Firestore.
     */
    public Tarea() {
    }

    /**
     * Constructor completo.
     *
     * @param id Identificador del documento.
     * @param uid UID del propietario.
     * @param titulo Título.
     * @param descripcion Descripción.
     * @param estado Estado.
     */
    public Tarea(String id,
                 String uid,
                 String titulo,
                 String descripcion,
                 String estado) {

        this.id = id;
        this.uid = uid;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;

    }

    /**
     * Obtiene el ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Asigna el ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el UID del propietario.
     */
    public String getUid() {
        return uid;
    }

    /**
     * Asigna el UID del propietario.
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Obtiene el título.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Asigna el título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna la descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el estado.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}