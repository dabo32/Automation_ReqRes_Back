plugins {
    id("java")
    id("net.serenity-bdd.serenity-gradle-plugin") version "4.2.8"
}

group = "org.bohorquez.automation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val serenityVersion = "4.2.8"
val cucumberVersion = "7.20.1"
val junitVersion = "5.11.3"
val restAssuredVersion = "5.5.0"
val lombokVersion = "1.18.34"

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite-api")
    testRuntimeOnly("org.junit.platform:junit-platform-suite-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Cucumber & Serenity Integration
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:$cucumberVersion")
    testImplementation("net.serenity-bdd:serenity-cucumber:$serenityVersion")

    // Serenity & REST
    implementation("net.serenity-bdd:serenity-core:$serenityVersion")
    implementation("net.serenity-bdd:serenity-screenplay:$serenityVersion")
    implementation("net.serenity-bdd:serenity-screenplay-rest:$serenityVersion")
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.filter.tags", System.getProperty("cucumber.filter.tags"))
    systemProperty("cucumber.plugin", "io.cucumber.core.plugin.SerenityReporterParallel,pretty")

    filter {
        includeTestsMatching("*Runner")
    }

    testLogging {
        showStandardStreams = true
        events("passed", "skipped", "failed")
    }
}

serenity {
    testRoot = "co.com.screenplay.project"
    requirementsBaseDir = "src/test/resources/features"
    reports = listOf("html")
}