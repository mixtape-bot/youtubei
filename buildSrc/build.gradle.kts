plugins {
    groovy
    `kotlin-dsl`
}

repositories {
    maven("https://maven.dimensional.fun/releases")
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("gradle-plugin", version = "1.7.0"))
    implementation(kotlin("serialization", version = "1.6.21"))

    implementation("fun.dimensional.gradle:gradle-tools:1.1.2")

    implementation(gradleApi())
    implementation(localGroovy())
}

