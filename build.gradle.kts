import io.guthix.buffer.registerPublication
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    idea
    `maven-publish`
    signing
    id("org.jetbrains.dokka")
    kotlin("jvm")
}

description = "A Netty ByteBuf extension library for RuneTek obfuscated buffers"

val logbackVersion: String by extra("1.2.3")
val nettyVersion: String by extra("4.1.56.Final")
val kotestVersion: String by extra("4.3.2")
val kotlinVersion: String by extra(project.getKotlinPluginVersion()!!)

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "io.guthix"
    version = "0.1.3"

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        testImplementation(group = "ch.qos.logback", name = "logback-classic", version = logbackVersion)
        testImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = kotestVersion)
        testImplementation(group = "io.kotest", name = "kotest-assertions-core-jvm", version = kotestVersion)
        dokkaHtmlPlugin(group = "org.jetbrains.dokka", name = "kotlin-as-java-plugin", version = kotlinVersion)
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        compileKotlin {
            kotlinOptions.jvmTarget = "11"
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = "11"
        }
    }
}

kotlin { explicitApi() }

dependencies {
    api(group = "io.netty", name = "netty-buffer", version = nettyVersion)
}

registerPublication(
    publicationName = "jagexByteBuf",
    pomName = "jagex-bytebuf"
)