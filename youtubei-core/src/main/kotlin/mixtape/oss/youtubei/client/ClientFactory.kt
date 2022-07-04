package mixtape.oss.youtubei.client

import io.ktor.client.*

public interface ClientFactory {
    /**
     * Creates an innertube client.
     *
     * @param httpClient The ktor HTTP client to use for creating the client.
     */
    public suspend fun create(httpClient: HttpClient): Client
}
