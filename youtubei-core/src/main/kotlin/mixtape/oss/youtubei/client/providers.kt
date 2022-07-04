package mixtape.oss.youtubei.client

import mixtape.koyo.io.net.useragent.UserAgent
import mixtape.oss.youtubei.client.config.ClientConfig
import mixtape.oss.youtubei.json.client.ClientContext
import mixtape.oss.youtubei.json.client.ClientPlatform
import mixtape.oss.youtubei.json.client.EndpointContext
import mixtape.oss.youtubei.proto.VisitorData
import mixtape.oss.youtubei.tools.UserAgentInformation

public class ConfigBasedEndpointContextProvider(public val config: ClientConfig) : EndpointContextProvider {
    override fun provide(): EndpointContext {
        var client = ClientContext(
            visitorData = VisitorData.generateRandom(),
            clientName = config.contextVersioning.name,
            clientVersion = config.contextVersioning.version,
            screenDensityFloat = 1.0F,
            screenPixelDensity = 1,
            originalUrl = config.originUrl,
        )

        /* augment the client context using the retrieved user agent. */
        val platform = config.platform
        if (platform != null) {
            UserAgent.retrieveRandom()

            val ua = UserAgent.ALL.find { config.userAgentRequirements.matches(it) }
                ?: UserAgent.retrieveRandom()

            val uai = UserAgentInformation.fromUserAgent(ua.asString)

            client = client.copy(
                userAgent = ua.asString,
                screenWidthPoints = ua.viewportWidth,
                screenHeightPoints = ua.viewportHeight,
                browserName = uai.browser.name,
                browserVersion = uai.browser.version,
                osName = uai.device.model.ifBlank { uai.device.os.name },
                platform = ClientPlatform.valueOf(platform.name),
                osVersion = uai.device.os.version.replace('.', '_'),
            )

            if (uai.device.os.isMobile) {
                client = client.copy(
                    deviceMake = uai.device.manufacturer,
                    deviceModel = uai.device.model,
                )
            }
        }

        return EndpointContext(client = client)
    }
}
