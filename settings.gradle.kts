rootProject.name = "youtubei-root"

include("youtubei-bom", "youtubei-core", "youtubei-player", "youtubei-json", "youtubei-proto")
include("examples")

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            net()
            common()
        }
    }
}

fun VersionCatalogBuilder.net() {
    val ktor = version("ktor", "2.0.3")

    library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef(ktor)

    // client
    library("ktor-client-core",                "io.ktor", "ktor-client-core").versionRef(ktor)
    library("ktor-client-cio",                 "io.ktor", "ktor-client-cio").versionRef(ktor)
    library("ktor-client-content-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef(ktor)

    bundle("ktor-client", listOf("ktor-client-core", "ktor-client-cio", "ktor-client-content-negotiation", "ktor-serialization-json"))
}

fun VersionCatalogBuilder.common() {
    /* misc */
    library("jsoup",          "org.jsoup",              "jsoup").version("1.15.1")
    library("ua-parser",      "com.github.ua-parser",   "uap-java").version("1.5.2")
    library("useragentutils", "eu.bitwalker",           "UserAgentUtils").version("1.21")

    /* kotlin */
    library("kotlinx-coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version("1.6.3")
    library("kotlin-stdlib",      "org.jetbrains.kotlin",  "kotlin-stdlib").version("1.7.0")
    library("kotlin-logging",     "io.github.microutils",  "kotlin-logging").version("2.1.23")

    /* logging */
    library("logback", "ch.qos.logback:logback-classic:1.2.11")

    /* graal */
    library("graal-js", "org.graalvm.js:js:22.1.0.1")

    /* koyo */
    val koyo = version("koyo", "0.2.2")
    library("koyo-core", "mixtape.oss.koyo", "koyo-core").versionRef(koyo)
    library("koyo-js",   "mixtape.oss.koyo", "koyo-js").versionRef(koyo)
    bundle("koyo", listOf("koyo-core", "koyo-js"))

    /* arrow */
    val arrow = version("arrow", "1.1.2")
    library("arrow-core", "io.arrow-kt", "arrow-core").versionRef(arrow)

    /* serialization */
    val kxser = version("kotlinx-serialization", "1.3.3")
    library("kx-ser-json",     "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef(kxser)
    library("kx-ser-protobuf", "org.jetbrains.kotlinx", "kotlinx-serialization-protobuf").versionRef(kxser)

    /* bundles */
    bundle("common", listOf("kx-ser-json", "kx-ser-protobuf", "arrow-core", "kotlinx-coroutines", "kotlin-stdlib", "kotlin-logging"))
}
