subprojects {
    apply<JavaLibraryPlugin>()
    apply<CheckstylePlugin>()

    group = "xyz.endelith"
    version = "1.21.10"
    
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://storehouse.okaeri.eu/repository/maven-public/")
    }
 
    extensions.configure<CheckstyleExtension> {
        toolVersion = "13.0.0"
    }

    extensions.getByType<JavaPluginExtension>().apply { 
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }

    tasks.withType<Checkstyle>().configureEach {
        exclude( "**/generated/**")
    }
}
