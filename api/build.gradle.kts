plugins {
    `maven-publish`
}

dependencies {
    api(libs.slf4j)
    api(libs.guava)
    api(libs.bundles.maven.resolver)
    api(libs.bundles.adventure)
    api(libs.jetbrains.annotations)
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

tasks {
    jar {
        manifest.attributes("Automatic-Module-Name" to "xyz.endelith.api")
    }
}
