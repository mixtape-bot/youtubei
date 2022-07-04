plugins {
    `java-platform`
    `library-published`
}


val me = project
rootProject.subprojects {
    if (name != me.name) {
        me.evaluationDependsOn(path)
    }
}

dependencies {
    constraints {
        rootProject.subprojects.forEach { subproject ->
            if (subproject.name in listOf(name, "examples")) {
                return@forEach
            }

            subproject.the<PublishingExtension>().publications.forEach pubs@{ publication ->
                if (publication !is MavenPublication) return@pubs

                val artifactId = publication.artifactId
                if (artifactId.endsWith("-metadata") || artifactId.endsWith("-kotlinMultiplatform")) {
                    return@pubs
                }

                api("${publication.groupId}:${publication.artifactId}:${publication.version}")
            }
        }
    }
}

publishing {
    publications.withType<MavenPublication> {
        from(project.components["javaPlatform"])
    }
}
