import io.guthix.buffer.registerPublication

val nettyVersion: String by extra("4.1.42.Final")
val jagexByteBufVersion: String by extra("0.1.3")
val kotlinVersion: String by rootProject.extra

description = "JagBuf protocol class generator"

dependencies {
    api(rootProject)
    api(group = "io.netty", name = "netty-all", version = nettyVersion)
    api(group = "com.squareup", name = "kotlinpoet", version = "1.7.2")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-scripting-common", version = kotlinVersion)
}

registerPublication(
    publicationName = "jagBuf",
    pomName = "jagbuf"
)