# 🚀 API Automation Framework: ReqRes Services (BDG Technical Challenge)

Este repositorio contiene la solución al reto técnico para el rol de Analista de Automatización de Servicios. El proyecto implementa la automatización de la API [ReqRes.in](https://reqres.in/) utilizando una arquitectura robusta basada en el patrón de diseño **Screenplay**.

## 🎯 Objetivo
Automatizar un conjunto de operaciones CRUD (Listar, Registrar, Actualizar y Eliminar) sobre los endpoints de ReqRes, garantizando un código limpio, mantenible y con reportes detallados.

## 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17.
* **Gestor de Dependencias:** Gradle (Kotlin DSL).
* **Framework de Automatización:** Serenity BDD.
* **BDD:** Cucumber (Gherkin).
* **Librería de Servicios:** RestAssured.
* **Pruebas Unitarias:** JUnit 5.
* **Herramientas de Diseño:** Postman.

## 🏗️ Arquitectura y Patrón de Diseño (Screenplay)
El proyecto sigue el patrón **Screenplay**, organizando la lógica en las siguientes capas funcionales:

* **`tasks`**: Acciones de alto nivel que el Actor realiza para interactuar con la API (ej. `PostUser`).
* **`interactions`**: Implementaciones de `RestInteraction` para gestionar la comunicación técnica con RestAssured.
* **`models`**: Objetos Java (POJOs) para el mapeo de los JSON de entrada y salida.
* **`questions`**: Verificaciones y aserciones utilizando el método `seeThat` y `JsonPath` para validar el contenido de las respuestas.
* **`userinterfaces`**: Definición de los recursos y endpoints de la API mapeados previamente en Postman.

## 📝 Escenarios de Prueba (Gherkin)
Se han implementado los siguientes escenarios definidos en el reto:
1. **Listar usuarios:** Valida que la respuesta contenga al menos un usuario.
2. **Registrar usuario:** Creación exitosa y validación del esquema esperado.
3. **Registro fallido:** Validación de error al omitir datos obligatorios.
4. **Actualizar usuario:** Modificación de información y validación de integridad.
5. **Eliminar usuario:** Confirmación de operación exitosa mediante código de estado HTTP.

## 🚀 Ejecución y Reportes

### Requisitos Previos
* Java JDK 17 o superior.
* Gradle (se incluye el wrapper `gradlew`).

### Comandos de Ejecución
Para ejecutar las pruebas y validar los criterios de aceptación:
```bash
./gradlew clean test
