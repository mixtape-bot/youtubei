import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.registering

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
