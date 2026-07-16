# 📋 To-Do List App

Aplicación móvil desarrollada en **Android Studio** utilizando **Java** y **Firebase Firestore**, diseñada para gestionar tareas de manera sencilla e intuitiva mediante operaciones CRUD y sincronización en tiempo real.

---

# 👨‍🎓 Información del Proyecto

* **Estudiante:** Nilson Leonardo Gonzalez Sandoval
* **Asignatura:** Móviles II
* **Docente:** Edgar Camilo Guerrero
* **Institución:** CESDE

---

# 📖 Descripción

To-Do List App es una aplicación Android que permite a los usuarios administrar sus tareas diarias mediante una interfaz moderna y fácil de usar.

La aplicación implementa autenticación básica mediante registro e inicio de sesión, almacenamiento de información en Firebase Firestore y actualización automática de los datos gracias al uso de SnapshotListener.

---

# 🎯 Objetivo

Desarrollar una aplicación móvil que permita gestionar tareas utilizando tecnologías actuales para el desarrollo Android, implementando buenas prácticas de programación, persistencia de datos en la nube y una interfaz amigable para el usuario.

---

# ✨ Características

* Registro de usuarios.
* Inicio de sesión.
* Crear tareas.
* Editar tareas.
* Eliminar tareas.
* Visualizar tareas en tiempo real.
* Actualización automática mediante SnapshotListener.
* Interfaz moderna con Material Design.
* Uso de RecyclerView para mostrar la información.
* Integración con Firebase Firestore.

---

# 🛠️ Tecnologías utilizadas

* Java
* Android Studio
* Firebase Firestore
* RecyclerView
* CardView
* Material Design Components
* XML
* Gradle

---

# 📂 Estructura del proyecto

```
app/
│
├── java/
│   └── com.Cesde.todolistleonardo/
│       ├── LoginActivity.java
│       ├── RegistroActivity.java
│       ├── MainActivity.java
│       ├── Tarea.java
│       ├── TareaAdapter.java
│       └── FirebaseConfig.java (si aplica)
│
├── res/
│   ├── drawable/
│   ├── layout/
│   ├── mipmap/
│   └── values/
│
└── AndroidManifest.xml
```

---

# 🔥 Base de datos

La aplicación utiliza **Firebase Firestore** como base de datos NoSQL.

Cada documento almacena información como:

* Título
* Descripción
* Estado de la tarea
* Fecha de creación (si aplica)

Las tareas se sincronizan automáticamente con Firestore, permitiendo visualizar los cambios en tiempo real.

---

# 📱 Funcionalidades principales

### Registro

Permite crear una cuenta para acceder a la aplicación.

### Inicio de sesión

Valida las credenciales registradas y permite ingresar al sistema.

### Gestión de tareas

El usuario puede:

* Agregar nuevas tareas.
* Modificar tareas existentes.
* Eliminar tareas.
* Visualizar todas las tareas almacenadas.

### Sincronización en tiempo real

Gracias a SnapshotListener, cualquier cambio realizado en Firestore se refleja inmediatamente en la aplicación sin necesidad de actualizar manualmente.

---

# 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio.

```bash
git clone https://github.com/leito1122333/ToDoListLeonardo2.git
```

2. Abrir el proyecto en Android Studio.

3. Sincronizar Gradle.

4. Crear un proyecto en Firebase.

5. Agregar el archivo **google-services.json** dentro de la carpeta:

```
app/
```

6. Habilitar Firebase Firestore.

7. Ejecutar la aplicación en un dispositivo físico o emulador.

---

# 📸 Capturas


Ejemplo:

```
/screenshots/login.png
<img width="397" height="855" alt="image" src="https://github.com/user-attachments/assets/8a1c712f-2c2f-49a7-9ffb-404f2ed3c2fa" />

/screenshots/register.png
<img width="399" height="775" alt="image" src="https://github.com/user-attachments/assets/a5e142d9-6041-4ca6-835f-d7b718a06a16" />

/screenshots/home.png
<img width="405" height="760" alt="image" src="https://github.com/user-attachments/assets/aa688fa4-ace8-478b-85c4-9a28ce937a9a" />


```

---

# 📚 Aprendizajes

Durante el desarrollo del proyecto se fortalecieron conocimientos en:

* Programación orientada a objetos.
* Desarrollo de aplicaciones Android.
* Firebase Firestore.
* Persistencia de datos en la nube.
* RecyclerView.
* Adaptadores personalizados.
* Material Design.
* Arquitectura básica de aplicaciones móviles.
* Manejo de eventos y listeners.

---

# 👨‍💻 Autor

**Nilson Leonardo Gonzalez Sandoval**

Proyecto académico desarrollado para la asignatura **Móviles II**.

---

# 📄 Licencia

Este proyecto fue desarrollado con fines exclusivamente académicos.
