plugins {
    id("java")
    id("net.serenity-bdd.serenity-gradle-plugin") version "4.0.15"
}

group = "org.bohorquez.automation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val serenityVersion = "4.0.15"
val cucumberVersion = "7.15.0"
val junitVersion = "5.10.2"

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")

    // Serenity Core & Screenplay
    testImplementation("net.serenity-bdd:serenity-core:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-cucumber:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay:$serenityVersion")
    testImplementation("net.serenity-bdd:serenity-screenplay-webdriver:$serenityVersion")

    // 3. Agregar librería específica para Screenplay + REST (Si vas a usar ReqRes)
    testImplementation("net.serenity-bdd:serenity-screenplay-rest:$serenityVersion")

    // Rest Assured
    testImplementation("io.rest-assured:rest-assured:5.3.2")
}

tasks.test {
    useJUnitPlatform()
    systemProperties(System.getProperties().mapKeys { it.key.toString() })
}

serenity {
    setTestRoot("src/test/resources/features")
    reports = listOf("html")
}