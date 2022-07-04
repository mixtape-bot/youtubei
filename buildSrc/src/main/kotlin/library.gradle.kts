import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`

    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "mixtape.oss.youtubei"
version = "$VERSION"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(JDK_VERSION)) // "8"
    }
}

repositories {
    mavenCentral()
    maven("https://maven.dimensional.fun/releases")
}

dependencies {
    api("org.slf4j:slf4j-api:1.7.32")

    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "$JDK_VERSION"
            freeCompilerArgs = listOf(
                "-Xjdk-release=$JDK_VERSION",
                "-opt-in=kotlin.time.ExperimentalTime",
                "-opt-in=kotlinx.serialization.InternalSerializationApi",
                "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
                "-opt-in=kotlin.contracts.ExperimentalContracts",
            )
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = "$JDK_VERSION"
        targetCompatibility = "$JDK_VERSION"
    }

    val sourcesJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    publishing {
        publications.withType<MavenPublication> {
            from(project.components["java"])
            artifact(sourcesJar)
        }
    }
}
