package mixtape.oss.youtubei.client

import io.ktor.client.*
import mixtape.oss.youtubei.client.config.ClientConfig

/**
 * Creates clients whose configuration is static and will not be updated during runtime.
 * Note: the configuration is static which it can break at any time, requiring new values to be set.
 *
 * @param config The client configuration.
 */
public open class StaticClientFactory(public val config: ClientConfig) : ClientFactory {
    override suspend fun create(httpClient: HttpClient): Client =
        Client(ConfigBasedEndpointContextProvider(config), config)
}
