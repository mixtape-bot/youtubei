package mixtape.oss.youtubei.client.config

import arrow.core.Option
import io.ktor.client.*

public fun interface ClientConfigProvider {
    /**
     * Provides an instance of [ClientConfig] for the given [originUrl].
     *
     * @param originUrl The origin URL of the client, e.g. https://www.youtube.com
     * @param httpClient The ktor HTTP client to use for the client.
     * @return The client configuration.
     */
    public suspend fun provide(originUrl: String, httpClient: HttpClient): Option<ClientConfig>
}

