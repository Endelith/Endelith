import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(project(":server:log4j2-plugin"))
    implementation(libs.netty)
    implementation(libs.disruptor)
    implementation(libs.jline)
    implementation(libs.bundles.log4j)
    implementation(libs.bundles.okaeri.configs)
    implementation(libs.terminal.console.appender)
    implementation(libs.velocity.native)
    implementation(libs.cosine)
}

application {
    mainClass.set("xyz.endelith.server.MinecraftServerImpl")
}

val generatedDir = "src/generated/java"

sourceSets {
    main {
        java {
            srcDir(generatedDir)
        }
    }
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    transform(Log4j2PluginsCacheFileTransformer::class.java)
    mergeServiceFiles()
}
