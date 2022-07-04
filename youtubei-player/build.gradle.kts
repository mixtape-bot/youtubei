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
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.koyo)

    implementation(libs.graal.js)
}

