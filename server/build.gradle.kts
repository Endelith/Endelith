import com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer

plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(libs.netty)
    implementation(libs.disruptor)
    implementation(libs.jline)
    implementation(libs.bundles.log4j)
    implementation(libs.bundles.jshepherd)
    implementation(libs.terminal.console.appender)
    implementation(libs.velocity.native)
    implementation(libs.javax.inject)
    implementation(libs.cosine)
}

application {
    mainClass.set("xyz.endelith.server.MinecraftServerImpl")
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    transform(Log4j2PluginsCacheFileTransformer::class.java)
    mergeServiceFiles()
}
