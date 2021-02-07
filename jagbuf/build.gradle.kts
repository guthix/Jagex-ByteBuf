import io.guthix.buffer.registerPublication

plugins {
    kotlin("jvm")
}

val nettyVersion: String by extra("4.1.42.Final")
val jagexByteBufVersion: String by extra("0.1.3")
val logbackVersion: String by rootProject.extra
val kotestVersion: String by rootProject.extra
val kotlinVersion: String by rootProject.extra

description = "JagBuf protocol class generator"

dependencies {
    api(rootProject)
    api(group = "com.squareup", name = "kotlinpoet", version = "1.7.2")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-scripting-common", version = kotlinVersion)
    testImplementation(group = "ch.qos.logback", name = "logback-classic", version = logbackVersion)
    testImplementation(group = "io.kotest", name = "kotest-runner-junit5-jvm", version = kotestVersion)
    testImplementation(group = "io.kotest", name = "kotest-assertions-core-jvm", version = kotestVersion)
    dokkaHtmlPlugin(group = "org.jetbrains.dokka", name = "kotlin-as-java-plugin", version = kotlinVersion)
}

registerPublication(
    publicationName = "jagBuf",
    pomName = "jagbuf"
)

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