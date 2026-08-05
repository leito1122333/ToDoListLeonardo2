# ToDoListLeonardo

## Proyecto Integrador Final - MOVILES II

Aplicación móvil de gestión de tareas desarrollada en Android Studio utilizando Java y servicios Cloud de Firebase.

El objetivo del proyecto es implementar una aplicación To-Do List con autenticación de usuarios, almacenamiento en la nube, gestión de tareas personalizada por usuario y control de acceso mediante roles.

---

# Información del proyecto

**Nombre del proyecto:** ToDoListLeonardo
**Asignatura:** MOVILES II
**Institución:** Instituto CESDE
**Tecnología principal:** Android Java
**Base de datos:** Firebase Cloud Firestore
**Autenticación:** Firebase Authentication

**Autor:**

Nilson Leonardo González Sandoval

---

# Descripción

ToDoListLeonardo es una aplicación móvil que permite a los usuarios administrar sus tareas personales desde cualquier dispositivo Android.

La aplicación permite:

* Crear tareas.
* Visualizar tareas propias.
* Eliminar tareas.
* Gestionar usuarios mediante un panel administrativo.
* Manejar permisos mediante roles.
* Actualizar información en tiempo real utilizando Firebase Firestore.

Cada usuario posee sus propias tareas, garantizando separación de información mediante identificación UID.

---

# Características principales

## Autenticación de usuarios

Implementación de Firebase Authentication para:

* Registro de usuarios.
* Inicio de sesión.
* Cierre de sesión.
* Control de sesión activa.

---

## Gestión de tareas

Los usuarios pueden:

* Crear nuevas tareas.
* Visualizar únicamente sus propias tareas.
* Eliminar tareas.
* Consultar cambios en tiempo real.

Cada tarea almacena:

```
uid
titulo
descripcion
estado
```

El campo `uid` permite relacionar cada tarea con el usuario propietario.

---

# Sistema de Roles (RBAC)

La aplicación implementa control de acceso basado en roles.

Roles disponibles:

## ADMIN

Permite:

* Acceder al panel administrativo.
* Visualizar usuarios registrados.
* Cambiar roles.
* Eliminar usuarios.

## USUARIO

Permite:

* Crear tareas personales.
* Consultar sus propias tareas.
* Gestionar sus registros.

---

# Arquitectura del proyecto

Estructura principal:

```
ToDoListLeonardo
│
├── java/com/Cesde/todolistleonardo
│
│   ├── LoginActivity.java
│   ├── RegistroActivity.java
│   ├── MainActivity.java
│   ├── AdministracionUsuariosActivity.java
│   │
│   ├── Tarea.java
│   ├── Usuario.java
│   │
│   ├── TareaAdapter.java
│   ├── UsuarioAdapter.java
│   │
│   ├── SesionManager.java
│   └── Constantes.java
│
└── res
    │
    ├── layout
    │   ├── activity_login.xml
    │   ├── activity_registro.xml
    │   ├── activity_main.xml
    │   ├── activity_administracion_usuarios.xml
    │   ├── item_tarea.xml
    │   └── item_usuario.xml
```

---

# Tecnologías utilizadas

## Desarrollo móvil

* Android Studio
* Java
* XML
* Material Components
* RecyclerView
* CardView

## Backend Cloud

* Firebase Authentication
* Firebase Cloud Firestore

## Herramientas

* Git
* GitHub
* Gradle

---

# Base de datos Firestore

## Colección usuarios

Estructura:

```
usuarios
 |
 └── UID
      ├── uid
      ├── correo
      ├── nombre
      └── rol
```

Ejemplo:

```json
{
 "uid":"usuario123",
 "correo":"usuario@gmail.com",
 "nombre":"Leonardo",
 "rol":"USUARIO"
}
```

---

## Colección tareas

Estructura:

```
tareas
 |
 └── ID_DOCUMENTO
      ├── uid
      ├── titulo
      ├── descripcion
      └── estado
```

Ejemplo:

```json
{
 "uid":"usuario123",
 "titulo":"Estudiar Firebase",
 "descripcion":"Configurar Firestore",
 "estado":"Pendiente"
}
```

---

# Seguridad

La aplicación implementa separación de datos mediante UID.

Un usuario solamente puede:

* Consultar sus propias tareas.
* Crear tareas asociadas a su cuenta.
* Eliminar sus propios registros.

El administrador cuenta con permisos adicionales para gestionar usuarios.

---

# Diseño Responsive

La aplicación fue diseñada para adaptarse a diferentes tamaños de pantalla.

Incluye:

* Compatibilidad con orientación vertical.
* Compatibilidad con orientación horizontal.
* Uso de RecyclerView para listas dinámicas.
* Ajuste automático con scroll cuando existe gran cantidad de información.
* Adaptación mediante layouts flexibles.

---

# Flujo de navegación

```
Login
 |
 |
 ├── Registro
 |
 |
 └── MainActivity
        |
        |
        ├── Crear tareas
        |
        ├── Consultar tareas
        |
        ├── Cerrar sesión
        |
        |
        └── Panel Administración
                |
                ├── Usuarios
                ├── Cambiar roles
                └── Eliminar usuarios
```

---

# Instalación del proyecto

## Requisitos

* Android Studio actualizado.
* JDK compatible con Android Studio.
* Cuenta Firebase configurada.

---

## Configuración Firebase

1. Crear un proyecto en Firebase Console.
2. Registrar la aplicación Android.
3. Descargar:

```
google-services.json
```

4. Ubicarlo en:

```
app/
 └── google-services.json
```

5. Sincronizar Gradle.

---

# Ejecución

1. Clonar el repositorio:

```bash
git clone URL_DEL_REPOSITORIO
```

2. Abrir el proyecto en Android Studio.

3. Ejecutar:

```
Run ▶
```

en un dispositivo físico o emulador Android.

---

# Estado actual del proyecto

✅ Registro de usuarios
✅ Inicio de sesión
✅ Firebase Authentication
✅ Firestore conectado
✅ CRUD de tareas
✅ Separación de tareas por usuario
✅ RecyclerView dinámico
✅ Panel administrativo
✅ Gestión de roles
✅ Diseño adaptable
✅ Control de sesión

---

# Mejoras futuras

* Recuperación de contraseña.
* Edición de tareas.
* Notificaciones push.
* Fechas límite para tareas.
* Filtros por estado.
* Implementación completa de Firebase Security Rules.
* Publicación en Google Play Store.

---

# Conclusión

ToDoListLeonardo integra conceptos de desarrollo móvil moderno utilizando servicios Cloud, autenticación segura, almacenamiento NoSQL y arquitectura basada en componentes reutilizables.

El proyecto demuestra la transición desde aplicaciones locales hacia soluciones conectadas a la nube con control de usuarios y escalabilidad.
