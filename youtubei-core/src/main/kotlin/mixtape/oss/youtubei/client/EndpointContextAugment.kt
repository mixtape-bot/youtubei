package mixtape.oss.youtubei.client

import mixtape.oss.youtubei.json.client.EndpointContext

public fun interface EndpointContextAugment {
    /**
     * Augments the given [context][ctx] with additional information.
     *
     * @param ctx the context to augment
     * @return the augmented context
     */
    public fun augment(ctx: EndpointContext): EndpointContext
}

