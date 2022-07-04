plugins {
    library
    `library-published`
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(projects.youtubeiCore)
    implementation(projects.youtubeiJson)

    implementation(libs.bundles.common)
    implementation(libs.bundles.koyo)

    implementation(libs.graal.js)
    implementation(libs.ktor.client.core)

    testImplementation(libs.logback)
    testImplementation(libs.ktor.client.cio)
}

