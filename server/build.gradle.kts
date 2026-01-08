plugins {
    application
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":api"))
    implementation(libs.netty)
    implementation(libs.cosine)
    implementation(libs.bundles.log4j)
    implementation(libs.bundles.okaeri.configs)
}

application {
    mainClass.set("xyz.endelith.server.MinecraftServerImpl")
}
