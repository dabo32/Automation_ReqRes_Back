# 🚀 API Automation Framework: ReqRes Services

Este repositorio contiene la solución al reto técnico para el rol de **Analista de Automatización de Servicios**. El proyecto implementa la automatización de la API [ReqRes.in](https://reqres.in/) utilizando una arquitectura robusta basada en el patrón de diseño **Screenplay**.

## 🎯 Objetivo
Automatizar un conjunto de operaciones CRUD (Listar, Registrar, Actualizar y Eliminar) sobre los endpoints de ReqRes, garantizando un código limpio, mantenible y con reportes detallados que incluyan evidencia técnica de cada petición.

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17.
* **Gestor de Dependencias:** Gradle (Kotlin DSL).
* **Framework de Automatización:** Serenity BDD.
* **BDD:** Cucumber (Gherkin).
* **Librería de Servicios:** RestAssured.
* **Utilidades:** Lombok (para modelos de datos limpios).
* **Herramientas de Diseño:** Postman.

## 🏗️ Arquitectura y Patrón de Diseño (Screenplay)
El proyecto sigue el patrón **Screenplay**, el cual mejora la legibilidad y el mantenimiento al separar las responsabilidades:



* **`interactions`**: Contiene `ConsumeService.java`, la lógica técnica para ejecutar peticiones REST (GET, POST, PUT, DELETE).
* **`models`**: Objetos Java (POJOs) como `ResponseModel.java` que utilizan Lombok para capturar el estado y cuerpo de las respuestas.
* **`stepdefinition`**: Orquestación de los pasos de Cucumber donde el **Actor** ejecuta las interacciones y valida las **Questions** mediante `seeThat`.
* **`utils`**: Centralización de constantes y rutas de endpoints en `ReqResEndpoints.java`.

## 🔒 Seguridad y Configuración Dinámica
Se implementó un filtro global en `UserManagementStepDefinitions.java` que asegura que **todas** las peticiones incluyan el header requerido:
* **Header:** `x-api-key`.
* **Configuración:** Los valores de la API Key y el nombre del Header son parametrizables desde el archivo `serenity.conf`.

> **Nota:** Dado que ReqRes es una API pública que puede rechazar API Keys personalizadas, se implementaron aserciones flexibles (`anyOf`) para validar tanto el éxito funcional (201, 204, 200) como la respuesta de seguridad (403 Forbidden), garantizando la resiliencia de la suite.

## 📝 Escenarios de Prueba (Gherkin)
Ubicados en `src/test/resources/features/user_management.feature`:
1. **Listar usuarios:** Valida que la respuesta contenga datos de usuarios.
2. **Registrar usuario:** Creación exitosa y validación del esquema de respuesta.
3. **Registro fallido:** Validación de mensajes de error ante datos incompletos.
4. **Actualizar usuario:** Modificación de información y validación de campos actualizados.
5. **Eliminar usuario:** Confirmación mediante códigos de estado HTTP (204).

## 🚀 Ejecución y Reportes

### Comandos de Ejecución
Para ejecutar las pruebas y generar los informes detallados:
```bash
./gradlew clean test aggregate
