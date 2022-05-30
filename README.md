# mixtape &bull; lava/youtubei

> Innertube Client written in Kotlin.

## Usage

```kotlin
import mixtape.apache.httptools.HttpClientTools
import mixtape.youtubei.Innertube
import mixtape.youtubei.client.InnertubeClientType
import mixtape.youtubei.player
import mixtape.youtubei.player.findBestStream
import mixtape.youtubei.player.getStreams
import mixtape.youtubei.playerScripts
import mixtape.youtubei.proto.YoutubeMusicSearchFilters
import mixtape.youtubei.request.ContentPlaybackContext
import mixtape.youtubei.response.getYoutubeMusicTracks
import mixtape.youtubei.search

val innertube = Innertube(InnertubeClientType.Youtube)

fun main() {
    /* fetch the current player script */
    val playerScript = innertube.playerScripts.fetch()
        ?: error("Failed to fetch player script")

    /* search for something on YouTube music */
    val tracks = innertube.search("b2nny - collar", YoutubeMusicSearchFilters(songs = true))
        .contents
        .getYoutubeMusicTracks()
        .first()

    /* fetch the player and get the signed url of the best stream */
    val player = innertube.player(
        videoId = tracks.flexColumns[0].text.runs[0].navigationEndpoint!!.watchEndpoint!!.videoId,
        playbackContext = ContentPlaybackContext(signatureTimestamp = playerScript.signatureTimestamp)
    )

    val stream = player
        .getStreams(playerScript.info.id)
        .findBestStream()

    println(playerScript.getSignedStreamUrl(stream))
}
```

###### *note:* do not expect support for this library

## Contributors

- [**@melike2d**](https://www.dimensional.fun)

---

Licensed under [**AGPL 3.0**](./LICENSE)
