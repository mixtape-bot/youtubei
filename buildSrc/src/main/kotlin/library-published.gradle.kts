plugins {
    `maven-publish`
}

publishing {
    publications {
        val projectNameNormalized = project.name.capitalize()
            .split('-')
            .joinToString("") { it.capitalize() }

        create<MavenPublication>(projectNameNormalized) {
            artifactId = project.name
            group = "mixtape.oss.youtubei"
            version = "$VERSION"
        }
    }
}

configurePublishing()
