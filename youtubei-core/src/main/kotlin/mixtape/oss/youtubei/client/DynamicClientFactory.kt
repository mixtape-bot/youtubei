package mixtape.oss.youtubei.client

import arrow.core.orElse
import arrow.core.toOption
import io.ktor.client.*
import mixtape.koyo.extensions.unwrap
import mixtape.oss.youtubei.client.config.ClientConfig
import mixtape.oss.youtubei.client.config.MainPageClientConfigProvider

/**
 * Creates clients whose configuration is fetched from the provided [originUrl].
 * If that fails, it falls back to the supplied [defaultConfig].
 *
 * @param originUrl     The URL to fetch the configuration from.
 * @param defaultConfig The default configuration to fall back to.
 */
public class DynamicClientFactory(
    public val originUrl: String,
    public val defaultConfig: ClientConfig? = null
) : ClientFactory {
    override suspend fun create(httpClient: HttpClient): Client {
        val config = MainPageClientConfigProvider.provide(originUrl, httpClient)
            .orElse { defaultConfig.toOption() }
            .unwrap()

        return Client(ConfigBasedEndpointContextProvider(config), config)
    }
}
