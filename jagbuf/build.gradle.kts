val nettyVersion: String by extra("4.1.42.Final")
val jagexByteBufVersion: String by extra("0.1.3")
val kotlinVersion: String by rootProject.extra

dependencies {
    implementation(rootProject)
    implementation(group = "io.netty", name = "netty-all", version = nettyVersion)
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-scripting-common", version = kotlinVersion)
    implementation(group = "com.squareup", name = "kotlinpoet", version = "1.7.2")
    implementation(group = "io.github.classgraph", name = "classgraph", version = "4.8.98")
}