import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import mixtape.koyo.encoding.DefaultFormats
import mixtape.oss.youtubei.Innertube
import mixtape.oss.youtubei.json.ContentPlaybackContext
import mixtape.oss.youtubei.player.script.PlayerScriptManager
import mixtape.oss.youtubei.player.stream.getStreams
import mixtape.oss.youtubei.requests.player

suspend fun main() {
    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(DefaultFormats.JSON)
        }
    }

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
