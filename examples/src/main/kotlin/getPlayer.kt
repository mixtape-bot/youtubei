import io.ktor.client.*
import io.ktor.client.engine.cio.*
import mixtape.oss.youtubei.Innertube
import mixtape.oss.youtubei.json.player.ContentPlaybackContext
import mixtape.oss.youtubei.player.script.PlayerScriptManager
import mixtape.oss.youtubei.player.stream.getStreams
import mixtape.oss.youtubei.requests.player

suspend fun main() {
    val httpClient = HttpClient(CIO)
    val innertube = Innertube()
    val playerScripts = PlayerScriptManager(httpClient)

    /* fetch the latest player script */
    val playerScript = playerScripts.fetch()
        ?: error("Unable to fetch player script")

    /* fetch the player */
    val player = innertube.player(
        httpClient,
        "BuNBLjJzRoo",
        ContentPlaybackContext(signatureTimestamp = playerScript.signatureTimestamp)
    )

    /* find and sign the best stream */
    val bestStream = player
        .getStreams(playerScript)
        .first()

    println(bestStream.getSignedUrl())
    println(bestStream)
}
