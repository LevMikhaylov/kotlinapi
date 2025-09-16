plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("io.ktor.plugin") version "2.3.3"
    application
}

group = "com.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.3")
    implementation("io.ktor:ktor-server-core-jvm:2.3.3")
    implementation("io.ktor:ktor-server-status-pages:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.3")
    implementation("io.ktor:ktor-server-netty:2.3.3") // ИЗМЕНИТЕ: убрали -jvm
    implementation("io.ktor:ktor-server-host-common-jvm:2.3.3") // ДОБАВЬТЕ
    implementation("ch.qos.logback:logback-classic:1.4.8")
    implementation("io.ktor:ktor-server-config-yaml:2.3.3")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.0")
}

ktor {
    fatJar {
        archiveFileName.set("application.jar")
    }
}