plugins {
    library
    `library-published`
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(projects.youtubeiProto)
    implementation(projects.youtubeiJson)

    implementation(libs.bundles.common)
    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.koyo)

    implementation(libs.ua.parser)
    implementation(libs.useragentutils)
    implementation(libs.jsoup)
}
