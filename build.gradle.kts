import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.6.20-RC"
}

group = "Elaman20M"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
    implementation("org.litote.kmongo:kmongo-serialization:4.5.0")
    implementation("org.litote.kmongo:kmongo-id-serialization:4.5.0")
    implementation("org.json:json:20220320")
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
//    implementation("org.litote.kmongo:kmongo-async:4.2.4")
//    implementation("org.litote.kmongo:kmongo-coroutine:4.2.4")
//    implementation("org.litote.kmongo:kmongo-reactor:4.2.4")
    implementation("org.jbibtex:jbibtex:1.0.20")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-log4j12:1.7.36")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}