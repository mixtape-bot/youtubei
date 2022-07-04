plugins {
    library
    `library-published`
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(projects.youtubeiProto)

    implementation(libs.bundles.common)
    implementation(libs.bundles.koyo)
}
