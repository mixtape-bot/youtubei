package mixtape.oss.youtubei.client

import mixtape.oss.youtubei.client.config.ClientConfig

public data class Client(
    /**
     * The [EndpointContext] provider for this innertube client.
     *
     * @see ConfigBasedEndpointContextProvider
     */
    public val contextProvider: EndpointContextProvider,
    /**
     * The configuration for this innertube client.
     *
     * @see ClientConfig
     */
    public val config: ClientConfig
)
