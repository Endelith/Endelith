plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(libs.netty)
    implementation(libs.jline)
    implementation(libs.cosine)
    implementation(libs.bundles.log4j)
    implementation(libs.bundles.okaeri.configs)
    implementation(libs.terminal.console.appender)
}

application {
    mainClass.set("xyz.endelith.server.MinecraftServerImpl")
}
