package mixtape.oss.youtubei.requests

import io.ktor.client.*
import io.ktor.client.statement.*
import mixtape.koyo.encoding.json.deserialize
import mixtape.oss.youtubei.Innertube
import mixtape.oss.youtubei.client.ClientType
import mixtape.oss.youtubei.client.EndpointContextAugment
import mixtape.oss.youtubei.json.player.ContentPlaybackContext
import mixtape.oss.youtubei.json.player.PlayerRequest
import mixtape.oss.youtubei.json.player.PlayerResponse
import mixtape.oss.youtubei.tools.extensions.execute

/**
 * Fetches the player for the given [videoId] and [playbackContext].
 *
 * @param videoId The video ID to fetch the player for.
 * @param playbackContext The playback context to use.
 * @return The player response.
 */
public suspend fun Innertube.player(
    httpClient: HttpClient,
    videoId: String,
    playbackContext: ContentPlaybackContext,
    racyCheckOk: Boolean = true,
    contentCheckOk: Boolean = true,
    augments: Set<EndpointContextAugment> = emptySet(),
    clientType: ClientType = ClientType.Web
): PlayerResponse {
    val request = PlayerRequest(
        racyCheckOk = racyCheckOk,
        contentCheckOk = contentCheckOk,
        videoId = videoId,
        playbackContext = playbackContext
    )

    val response = execute(
        httpClient,
        "player",
        request,
        clientType,
        augments
    )

    return response
        .bodyAsText()
        .deserialize()
}

