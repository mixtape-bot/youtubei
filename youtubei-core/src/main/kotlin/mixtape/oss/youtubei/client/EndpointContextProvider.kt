package mixtape.oss.youtubei.client

import mixtape.oss.youtubei.json.client.EndpointContext

public fun interface EndpointContextProvider {
    /**
     * Provides an instance of [EndpointContext]
     *
     * @return an instance of [EndpointContext]
     */
    public fun provide(): EndpointContext
}

