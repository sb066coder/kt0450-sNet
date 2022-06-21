import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("jacoco")
    kotlin("jvm") version "1.7.0"
}

group = "ru.netology"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("junit:junit:4.13.2")
    testImplementation(kotlin("test"))
}



tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}