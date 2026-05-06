#  To-Do List App (Android)

Aplicación móvil desarrollada en **Android Studio (Java + SQLite)** que permite gestionar tareas mediante operaciones CRUD y visualización con RecyclerView.

---

## 👨‍🎓 Información del Proyecto

* **Estudiante:** Nilson Leonardo Gonzalez Sandoval
* **Asignatura:** Móviles II
* **Docente:** Edgar Camilo Guerrero
* **Proyecto:** To-Do List

---

## 🎯 Objetivo

Desarrollar una aplicación móvil funcional que permita la administración de tareas, aplicando conceptos de:

* Persistencia de datos con SQLite
* Interfaces gráficas en Android
* Uso de RecyclerView
* Implementación de operaciones CRUD

---

## ⚙️ Funcionalidades

La aplicación permite:

* ✅ Crear tareas
* 📋 Listar todas las tareas
* 🔍 Buscar tareas por ID
* ✏️ Editar tareas
* ❌ Eliminar tareas

---

## Base de Datos

Se utiliza SQLite con la siguiente estructura:

**Tabla: ****`tareas`**

| Campo       | Tipo    | Descripción                    |
| ----------- | ------- | ------------------------------ |
| id          | INTEGER | Clave primaria autoincremental |
| titulo      | TEXT    | Título de la tarea             |
| descripcion | TEXT    | Descripción de la tarea        |
| estado      | TEXT    | Estado (pendiente/completada)  |

---

##  Arquitectura del Proyecto

El proyecto sigue una estructura básica basada en:

* **Adaptador:** `TareaAdapter.java`
* **Controlador:** `MainActivity.java`
* **Base de datos:** `AdminSQLiteOpenHelper.java`
* **Interfaz:** XML + RecyclerView

---

## 🖥️ Tecnologías Utilizadas

* Java
* Android Studio
* SQLite
* RecyclerView

---

## 🚀 Ejecución del Proyecto

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/tuusuario/todorepo.git
   ```

2. Abrir en Android Studio

3. Ejecutar en emulador o dispositivo físico

---

## 📌 Notas

* El campo **estado** debe ingresarse como:

  * `pendiente`
  * `completada`

* La base de datos se crea automáticamente al ejecutar la aplicación.

---



## 📄 Licencia

Proyecto académico - uso educativo.
