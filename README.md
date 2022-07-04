# mixtape &bull; lava/youtubei

> Innertube Client written in Kotlin.

## 📦 Modules

- [**core**](/youtubei-core) — innertube client implementations
- [**player**](/youtubei-player) — classes for requesting innertube players
  - **PlayerScriptManager** to fetch player scripts used to sign stream urls
  - **Innertube.player** for executing the /player innertube endpoint
- [**json**](/youtubei-json) — common data structures found in innertube api results
- [**protobuf**](/youtubei-proto) — reverse-engineered protobuf structures

## 📁 Documentation

**Soon™️**

## 🚀 Installation

##### 🐘 Gradle

Usage With Bom:

```kotlin
repositories {
    maven("https://maven.dimensional.fun/releases")
}

dependencies {
    implementation(platform("mixtape.oss.youtubei:youtube-bom:{VERSION}"))
    implementation("mixtape.oss.youtubei:youtubei-{MODULE}")
}
```

##### 🪶 Maven

```xml
<repositories>
    <repository>
        <id>dimensional-maven</id>
        <name>Dimensional Maven</name>
        <url>https://maven.dimensional.fun/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>mixtape.oss.youtubei</groupId>
        <artifactId>youtubei-{MODULE}</artifactId>
        <version>{VERSION}</version>
    </dependency>
</dependencies>
```

## Usage

```kotlin

```

###### *note:* do not expect support for this library

## Contributors

- [**@melike2d**](https://www.dimensional.fun)

---

Licensed under [**AGPL 3.0**](./LICENSE)
