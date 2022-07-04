package mixtape.oss.youtubei.player.script

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import mixtape.koyo.extensions.substringAround
import mixtape.koyo.io.net.useragent.UserAgent

public class PlayerScriptManager(public val httpClient: HttpClient) {
    public companion object {
        public const val IFRAME_API_URL: String = "https://www.youtube.com/iframe_api"
        public const val PLAYER_SCRIPT_URL: String = "https://www.youtube.com/s/player/%s/player_ias.vflset/en_US/base.js"
    }

    /**
     * Fetches the player script from YouTube.
     */
    public suspend fun fetch(): PlayerScript? {
        val iframeApi = httpClient
            .get(IFRAME_API_URL) { userAgent(UserAgent.retrieveRandom().asString) }
            .bodyAsText()

        val scriptUrl = iframeApi.substringAround("var scriptUrl = '", "';")
            ?.replace("\\/", "/")
            ?: return null

        val info = PlayerScriptInfo.extractFromUrl(scriptUrl)
            ?: return null

        return fetch(info.id)
    }

    /**
     * Fetches the provided [player script][url] and returns useful information about it.
     *
     * @param url The URL of the player script.
     */
    public suspend fun fetch(urlOrId: String): PlayerScript? {
        if (urlOrId matches "^https?://.+$".toRegex()) {
            val info = PlayerScriptInfo.extractFromUrl(urlOrId)
                ?: return null

            return fetch(info)
        }

        return fetch(PLAYER_SCRIPT_URL.format(urlOrId))
    }

    /**
     * Fetches the provided [player script][info] and returns an instance of [PlayerScript].
     *
     * @param url The URL of the player script.
     */
    public suspend fun fetch(info: PlayerScriptInfo): PlayerScript? {
        val script = httpClient
            .get(info.url) { userAgent(UserAgent.retrieveRandom().asString) }
            .bodyAsText()

        return PlayerScriptExtractor.extract(info, script)
    }
}

