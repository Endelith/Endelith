subprojects {
    apply<JavaLibraryPlugin>()

    group = "xyz.endelith"
    version = "1.21.10"
    
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
    }

    extensions.getByType<JavaPluginExtension>().apply { 
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }
}
