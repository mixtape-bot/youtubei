plugins {
    library
    `library-published`
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(libs.bundles.common)
    implementation(libs.bundles.koyo)

    testImplementation(libs.logback)
}
