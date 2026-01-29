plugins {
    `maven-publish`
}

dependencies {
    api(libs.jspecify)
    api(libs.slf4j)
    api(libs.bundles.adventure)
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        pom.licenses {
            license {
                name = "MIT"
                url = "https://choosealicense.com/licenses/mit/"
            }
        }
    }
}

val generatedDir = "src/generated/java"

sourceSets {
    main {
        java {
            srcDir(generatedDir)
        }
    }
}

val dataProject = project(":data")
val sourceSets = dataProject.extensions.getByName("sourceSets") as SourceSetContainer

tasks.register<JavaExec>("generateSources") {
    workingDir(rootDir.resolve("test-server").apply { mkdirs() })

    mainClass.set("xyz.endelith.data.GeneratorMain")
    classpath = sourceSets["main"].runtimeClasspath

    args(
        "--apiSourceFolder=${
            project(":api").projectDir
                .resolve(generatedDir).absolutePath
        }",
        "--serverSourceFolder=${
            project(":server").projectDir
                .resolve(generatedDir).absolutePath
        }",
        "--serverResourceFolder=${
            project(":server").projectDir
                .resolve("src/main/resources/data/").absolutePath
        }"
    )
}

tasks {
    jar {
        manifest.attributes("Automatic-Module-Name" to "xyz.endelith.api")
    }
}
