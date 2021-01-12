plugins {
    kotlin("multiplatform") version "1.4.10"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

group = "com.github.shwaka.kococo"
version = "0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}

val artifactName = project.name
val artifactGroup = project.group.toString()
val artifactVersion = project.version.toString()

// val mySourcesJar by tasks.creating(Jar::class) {
//     archiveClassifier.set("sources")
//     from(sourceSets.getByName("main").allSource)
// }

// publishing {
//     publications {
//         create<MavenPublication>("bintray") {
//             // from(components["kotlin"])
//             artifact(tasks["sourcesJar"])
//             from(components["kotlin"])
//             // artifact(mySourcesJar)
//             groupId = artifactGroup
//             artifactId = artifactName
//             version = artifactVersion
//         }
//     }
// }

// afterEvaluate {
//     project.publishing.publications.all {
//         this as MavenPublication
//         artifactId = artifactName + "-$name".takeUnless { "metadata" in name }.orEmpty()
//     }
// }

bintray {
    // user = System.getenv("BINTRAY_USER")
    // key = System.getenv("BINTRAY_KEY")
    user = project.property("bintrayUser") as String
    key = project.property("bintrayApiKey") as String
    publish = false
    // setPublications("bintray")
    setPublications("js", "jvm")
    pkg.apply {
        repo = "maven"
        name = artifactName
        version.apply {
            name = artifactVersion
            desc = "my description"
            // released = java.util.Date().toString()
            vcsTag = artifactVersion
        }
    }
    // pkg(
    //     closureOf<com.jfrog.bintray.gradle.BintrayExtension.PackageConfig> {
    //         repo = "maven"
    //         name = artifactName
    //         version.apply {
    //             name = artifactVersion
    //             desc = "my description"
    //             // released = java.util.Date().toString()
    //             vcsTag = artifactVersion
    //         }
    //     }
    // )
}

// tasks.withType<com.jfrog.bintray.gradle.BintrayUploadTask> {
//     doFirst {
//         publishing.publications.filterIsInstance<MavenPublication>()
//             .forEach { publication ->
//                 val moduleFile = buildDir.resolve("publications/${publication.name}/module.json")
//                 if (moduleFile.exists()) {
//                     publication.artifact(object : org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact(moduleFile) {
//                         override fun getDefaultExtension() = "module"
//                     })
//                 }
//             }
//     }
// }

// tasks.withType<com.jfrog.bintray.gradle.BintrayUploadTask> {
//     setPublications(publishing.publications.map { it.name }.filter { it != "kotlinMultiplatform" })
// }

tasks.named("bintrayUpload") {
    dependsOn("assemble")
}
