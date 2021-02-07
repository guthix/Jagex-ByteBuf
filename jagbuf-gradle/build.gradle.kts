import io.guthix.buffer.registerPublication

plugins {
    id("com.gradle.plugin-publish") version "0.12.0"
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "io.guthix"

dependencies {
    api(project(":jagbuf"))
    runtimeOnly(project(":jagbuf"))
    implementation(gradleApi())
    implementation(group = "io.github.classgraph", name = "classgraph", version = "4.8.53")
}

pluginBundle {
    website = "http://www.guthix.io/"
    vcsUrl = "https://github.com/guthix/Jagex-ByteBuf"
    description = "JagProt Gradle plugin"
}

gradlePlugin {
    plugins {
        create("jagprot") {
            id = "io.guthix.jagprot"
            displayName = "jagprot"
            description = "Jagex protocol buffers"
            implementationClass = "io.guthix.proto.JagProtPlugin"
        }
    }
}

registerPublication(
    publicationName = "jagprot",
    pomName = "jagprot"
)

