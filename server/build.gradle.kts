plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(libs.netty)
    implementation(libs.logback)
    implementation(libs.cosine)
    implementation(libs.bundles.okaeri.configs)
}

application {
    mainClass.set("xyz.endelith.server.MinecraftServerImpl")
}
