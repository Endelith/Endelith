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

tasks {
    jar {
        manifest.attributes("Automatic-Module-Name" to "xyz.endelith.api")
    }
}
