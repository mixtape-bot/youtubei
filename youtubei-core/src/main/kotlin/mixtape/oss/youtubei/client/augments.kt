package mixtape.oss.youtubei.client

import mixtape.oss.youtubei.json.client.EndpointContext

@JvmInline
public value class Embedded(public val url: String) : EndpointContextAugment {
    override fun augment(ctx: EndpointContext): EndpointContext {
        val thirdParty = EndpointContext.ThirdParty.Embed(embedUrl = url)
        return ctx.copy(client = ctx.client.copy(clientScreen = "EMBED"), thirdParty = thirdParty)
    }
}
