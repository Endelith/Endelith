subprojects {
    apply<JavaLibraryPlugin>()
    apply<CheckstylePlugin>()

    group = "xyz.endelith"
    version = "1.0"
    
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven("https://repo.papermc.io/repository/maven-public")
    }
 
    extensions.configure<CheckstyleExtension> {
        toolVersion = "13.0.0"
        configFile = rootProject.file(".checkstyle/config.xml")
    }

    extensions.getByType<JavaPluginExtension>().apply { 
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }
}
