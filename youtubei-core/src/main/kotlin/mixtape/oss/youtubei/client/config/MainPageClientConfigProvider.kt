package mixtape.oss.youtubei.client.config

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import mixtape.koyo.encoding.json.deserialize
import mixtape.koyo.extensions.substringAround
import mixtape.koyo.lang.js.JSON
import mixtape.koyo.log.logging
import mixtape.oss.youtubei.tools.YoutubeConfig
import mixtape.oss.youtubei.tools.YoutubeConfigExtractor

public object MainPageClientConfigProvider : ClientConfigProvider {
    private val log by logging {  }

    override suspend fun provide(originUrl: String, httpClient: HttpClient): Option<ClientConfig> {
        return try {
            val youtubeConfig = getYoutubeConfig(originUrl, httpClient)
            Some(ClientConfig.fromYoutubeConfig(youtubeConfig))
        } catch (ex: Throwable) {
            log.error(ex) { "Unable to provide client config for the main page of $originUrl:" }
            None
        }
    }

    private suspend fun getYoutubeConfig(originUrl: String, httpClient: HttpClient): YoutubeConfig {
        val html = httpClient
            .get(originUrl)
            .bodyAsText()

        return try {
            YoutubeConfigExtractor.fromHtml(html)
        } catch (ex: Exception) {
            log.trace(ex) { "Unable to scrape '$originUrl' for config using JS extractor" }
            html.substringAround("ytcfg.set({", "})")
                ?.let { "{$it}" }
                ?.let(JSON::stringify)
                ?.deserialize()
                ?: error("Unable to deserialize embedded config")
        }
    }
}

