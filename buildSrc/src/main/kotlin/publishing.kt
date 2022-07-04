import lol.dimensional.gradle.dsl.by
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.maven

fun Project.configurePublishing() {
    val project = this
    extensions.configure<PublishingExtension> {
        repositories {
            dimensionalFun(releases, authenticate = true)
        }

        for (publication in publications) {
            val pub = publication as? MavenPublication ?: continue

            pub.pom {
                name by project.name
                description by "Kotlin Innertube Client"
                url by "https://gitlab.com/mixtape/os/youtubei"

                organization {
                    name by "Mixtape Bot"
                    url by "https://mixtape.systems"
                }

                developers {
                    developer {
                        name by "Mixtape Bot Team"
                        email by "mixtape.devs@gmail.com"
                        url by "https://mixtape.systems"
                    }

                    developer {
                        name by "melike2d"
                        email by "gino@dimensional.fun"
                        url by "https://dimensional.fun"
                    }
                }

                licenses {
                    license {
                        name by "AGPL-3.0"
                        url by "https://opensource.org/licenses/AGPL-3.0"
                    }
                }

                issueManagement {
                    system by "GitLab"
                    url by "https://gitlab.com/mixtape/os/youtubei/-/issues"
                }

                scm {
                    connection by "scm:git:ssh://gitlab.com/mixtape/os/youtubei.git"
                    developerConnection by "scm:git:ssh://git@gitlab.com:mixtape/os/yoitubei.git"
                    url by "https://gitlab.com/mixtape/os/youtubei"
                }
            }
        }
    }
}
