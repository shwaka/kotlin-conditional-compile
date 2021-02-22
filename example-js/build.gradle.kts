plugins {
    kotlin("js") version "1.4.21"
}

group = "me.shun"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    // mavenLocal()
    maven(url = "https://shwaka.github.io/maven/")
}

dependencies {
    testImplementation(kotlin("test-js"))
    if (System.getProperty("debug") == null) {
        implementation("com.github.shwaka.kococo:kococo-release-js:0.1")
    } else {
        implementation("com.github.shwaka.kococo:kococo-debug-js:0.1")
    }
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
    }
}
