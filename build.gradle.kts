subprojects {
    apply<JavaLibraryPlugin>()

    group = "xyz.endelith"
    version = "1.21.10"
    
    repositories {
        mavenLocal()
        mavenCentral()
    }

    extensions.getByType<JavaPluginExtension>().apply { 
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }
}
