import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}

group = "me.shun"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    // mavenLocal()
    maven {
        setUrl("https://dl.bintray.com/shwaka/maven/")
    }
}

dependencies {
    testImplementation(kotlin("test-junit"))
    if (System.getProperty("debug") == null) {
        implementation("com.github.shwaka.kococo:kococo-release-jvm:0.1")
    } else {
        implementation("com.github.shwaka.kococo:kococo-debug-jvm:0.1")
    }
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.github.shwaka.kococo.example.AppKt"
}
