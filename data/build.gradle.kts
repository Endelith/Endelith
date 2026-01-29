import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform

plugins {
    alias(libs.plugins.vanilla.gradle) 
}

dependencies {
    compileOnly(libs.jspecify)
    implementation(libs.javapoet)
}

minecraft {
    version(libs.versions.minecraft.get())
    platform(MinecraftPlatform.SERVER)
}
