# 🚀 API Automation Framework: ReqRes Services

Este repositorio contiene la solución al reto técnico para el rol de **Analista de Automatización de Servicios**. El
proyecto implementa la automatización de la API [ReqRes.in](https://reqres.in/) utilizando una arquitectura robusta
basada en el patrón de diseño **Screenplay**.

## 🎯 Objetivo

Automatizar un conjunto de operaciones CRUD (Listar, Registrar, Actualizar y Eliminar) sobre los endpoints de ReqRes,
garantizando un código limpio, mantenible y con reportes detallados que incluyan evidencia técnica de cada petición.

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 17.
* **Gestor de Dependencias:** Gradle (Kotlin DSL).
* **Framework de Automatización:** Serenity BDD.
* **BDD:** Cucumber (Gherkin).
* **Librería de Servicios:** RestAssured.
* **Utilidades:** Lombok para la creación de modelos de datos limpios.
* **Herramientas de Diseño:** Postman.

## 🏗️ Estructura del Proyecto (Screenplay)

La arquitectura sigue el patrón Screenplay para separar las responsabilidades de manera modular y escalable:

```plaintext
src/test/java/com/reqres
 ├── interactions    # Lógica técnica para ejecutar peticiones REST (GET, POST, PUT, DELETE)
 ├── models          # POJOs con Lombok para mapear el estado y cuerpo de las respuestas
 ├── questions       # Verificaciones y aserciones mediante el método seeThat
 ├── runners         # Clases encargadas de ejecutar los escenarios de Cucumber
 ├── stepdefinition  # Orquestación entre los pasos de Gherkin y las acciones del Actor
 ├── tasks           # Acciones de alto nivel realizadas por el actor (ej: Registrar usuario)
 └── userinterfaces  # Centralización de constantes y rutas de endpoints (Resources)

src/test/resources
 ├── features        # Escenarios de prueba escritos en lenguaje Gherkin
 ├── postman         # Colección de Postman utilizada para el análisis previo de los servicios
 └── serenity.conf   # Configuración de ambientes, URLs y headers de seguridad

```
---
## 🔒 Seguridad y Configuración Dinámica

Se implementó un **filtro global** que asegura que todas las peticiones incluyan el header de seguridad requerido de forma automática.

* **Header Requerido:** `x-api-key`.
* **Configuración:** El nombre y valor del header son completamente parametrizables desde el archivo `serenity.conf`.
* **Resiliencia:** Se implementaron aserciones flexibles (`anyOf`) para garantizar la estabilidad de la suite frente a las políticas de la API pública:
    * **Éxito Funcional:** Validaciones de códigos `201`, `204`, `200`.
    * **Seguridad:** Validación de respuesta `403 Forbidden`.
---

## 📝 Escenarios de Prueba (Gherkin)

Ubicados en: `src/test/resources/features/user_management.feature`

| Escenario              | Descripción                              | Validación Clave                     |
|:-----------------------|:-----------------------------------------|:-------------------------------------|
| **Listar usuarios**    | Obtener lista de usuarios registrados.   | Presencia de datos en la respuesta.  |
| **Registrar usuario**  | Creación exitosa de un nuevo registro.   | Validación del esquema de respuesta. |
| **Registro fallido**   | Manejo de errores por datos incompletos. | Mensajes de error esperados.         |
| **Actualizar usuario** | Modificación de información existente.   | Validación de campos actualizados.   |
| **Eliminar usuario**   | Confirmación de borrado de recurso.      | Código de estado HTTP `204`.         |

---

## 🚀 Ejecución y Reportes

### Comandos de Ejecución

Para limpiar el proyecto, ejecutar las pruebas y generar los informes detallados, utiliza el siguiente comando en la
terminal:

```bash
./gradlew clean test aggregate
