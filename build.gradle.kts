subprojects {
    apply<JavaLibraryPlugin>()

    group = "xyz.endelith"
    version = "1.21.10"
    
    repositories {
        mavenLocal()
        mavenCentral() 
        maven("https://storehouse.okaeri.eu/repository/maven-public/")
    }

    extensions.getByType<JavaPluginExtension>().apply { 
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }
}
