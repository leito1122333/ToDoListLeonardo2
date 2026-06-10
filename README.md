# 📋 To-Do List App (Android + Firebase)

Aplicación móvil desarrollada en **Android Studio (Java)** que permite gestionar tareas mediante operaciones CRUD utilizando **Firebase Firestore** como base de datos en la nube y visualización de información mediante **RecyclerView**.

---

# 👨‍🎓 Información del Proyecto

* **Estudiante:** Nilson Leonardo Gonzalez Sandoval
* **Asignatura:** Móviles II
* **Docente:** Edgar Camilo Guerrero
* **Proyecto:** To-Do List

---

# 🎯 Objetivo

Desarrollar una aplicación móvil funcional para la gestión de tareas, aplicando conceptos de:

* Desarrollo Android con Java
* Persistencia de datos en la nube mediante Firebase Firestore
* Interfaces gráficas con Material Design
* Implementación de RecyclerView
* Operaciones CRUD (Crear, Leer, Actualizar y Eliminar)

---

# ⚙️ Funcionalidades

La aplicación permite:

* ✅ Crear tareas
* 📋 Listar todas las tareas
* 🔍 Buscar tareas por ID
* ✏️ Editar tareas
* ❌ Eliminar tareas
* ☁️ Almacenar información en Firebase Firestore

---

# 🗄️ Base de Datos

Se utiliza **Firebase Firestore** con una colección llamada:

```text
tareas
```

Cada documento contiene la siguiente estructura:

| Campo       | Tipo   |
| ----------- | ------ |
| titulo      | String |
| descripcion | String |
| estado      | String |

Ejemplo:

```json
{
  "titulo": "Realizar proyecto",
  "descripcion": "Implementar CRUD con Firestore",
  "estado": "Pendiente"
}
```

Firestore genera automáticamente el ID de cada documento.

---

# 🏗️ Arquitectura del Proyecto

El proyecto está compuesto por:

* **Modelo:** `Tarea.java`
* **Adaptador:** `TareaAdapter.java`
* **Controlador:** `MainActivity.java`
* **Base de datos:** Firebase Firestore
* **Interfaz gráfica:** XML + RecyclerView + Material Components

---

# 🖥️ Tecnologías Utilizadas

* Java
* Android Studio
* Firebase Firestore
* RecyclerView
* Material Design Components
* Gradle Kotlin DSL

---

# 🚀 Ejecución del Proyecto

## 1. Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/ToDoListLeonardo.git
```

## 2. Abrir el proyecto

Abrir la carpeta del proyecto desde Android Studio.

## 3. Configurar Firebase

* Crear un proyecto en Firebase.
* Registrar la aplicación Android.
* Descargar el archivo `google-services.json`.
* Copiar el archivo dentro de:

```text
app/google-services.json
```

## 4. Ejecutar la aplicación

Ejecutar en un emulador o dispositivo físico con conexión a Internet.

---

# 📱 Interfaz

La aplicación cuenta con:

* Formulario para crear y editar tareas.
* Selección del estado mediante Chips.
* Botones para las operaciones CRUD.
* Listado dinámico mediante RecyclerView.

---

# 📌 Notas

Los valores recomendados para el campo estado son:

* Pendiente
* Completada

La colección de Firestore se crea automáticamente cuando se guarda la primera tarea.

---

# 📄 Licencia

Proyecto académico desarrollado para la asignatura **Móviles II**.

Uso exclusivamente educativo.
