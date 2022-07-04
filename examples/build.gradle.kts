plugins {
    library
}

dependencies {
    implementation(projects.youtubeiCore)
    implementation(projects.youtubeiPlayer)
    implementation(projects.youtubeiJson)
    implementation(projects.youtubeiProto)

    implementation(libs.bundles.common)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.koyo)

    implementation(libs.logback)
}
