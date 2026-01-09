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
    implementation(libs.cosine)
    implementation(libs.bundles.log4j)
    implementation(libs.bundles.okaeri.configs)
    implementation(libs.terminal.console.appender)
}

application {
    mainClass.set("xyz.endelith.server.MinecraftServerImpl")
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    transform(Log4j2PluginsCacheFileTransformer::class.java)
    mergeServiceFiles()
}
