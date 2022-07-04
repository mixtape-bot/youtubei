package mixtape.oss.youtubei.client.config

import mixtape.koyo.io.net.useragent.UserAgent
import mixtape.oss.youtubei.InnertubeApi
import mixtape.oss.youtubei.client.Versioning
import mixtape.oss.youtubei.json.client.ClientPlatform
import mixtape.oss.youtubei.tools.YoutubeConfig

/**
 * Represents configuration for an innertube client.
 */
public data class ClientConfig(
    /**
     * The origin url of the innertube client.
     */
    val originUrl: String,
    /**
     * The platform of the innertube client.
     */
    val platform: ClientPlatform? = null,
    /**
     * Versioning used in instances of [com.youtube.i.client.EndpointContext]
     */
    val contextVersioning: Versioning,
    /**
     * The innertube api to use.
     */
    val api: InnertubeApi,
    /**
     * Versioning used in request headers.
     */
    val headerVersioning: Versioning? = null,
    /**
     * Other useful information about this innertube client.
     */
    val other: Other = Other(),
    /**
     * Requirements for the user agent.
     */
    val userAgentRequirements: UserAgentRequirements = UserAgentRequirements(category = platform?.name?.lowercase())
) {
    public companion object {
        /**
         * The default configuration for the "WEB" innertube client.
         */
        public val DEFAULT_WEB: ClientConfig = ClientConfig(
            originUrl = "https://www.youtube.com",
            platform = ClientPlatform.DESKTOP,
            api = InnertubeApi("https://www.youtube.com/youtubei"),
            headerVersioning = Versioning(name = "WEB", version = "2.20220519.09.00"),
            contextVersioning = Versioning(name = "1", version = "2.20220519.09.00"),
            other = Other(pageCl = "449852892", pageBuildLabel = "youtube.desktop.web_20220519_09_RC00")
        )

        /**
         * The default configuration for the "WEB_REMIX" innertube client.
         */
        public val DEFAULT_WEB_REMIX: ClientConfig = ClientConfig(
            originUrl = "https://music.youtube.com",
            platform = ClientPlatform.DESKTOP,
            api = InnertubeApi("https://music.youtube.com/youtubei"),
            headerVersioning = Versioning(name = "67", version = "1.20220518.01.00"),
            contextVersioning = Versioning(name = "WEB_REMIX", version = "1.20220518.01.00"),
            other = Other(pageCl = "449431927", pageBuildLabel = "youtube.music.web.client_20220518_01_RC00")
        )

        /**
         * The default configuration for the "ANDROID" innertube client.
         */
        public val DEFAULT_ANDROID: ClientConfig = ClientConfig(
            originUrl = "https://www.youtube.com",
            platform = ClientPlatform.MOBILE,
            api = InnertubeApi("https://www.youtube.com/youtubei"),
            contextVersioning = Versioning(name = "ANDROID", version = "17.03"),
            userAgentRequirements = UserAgentRequirements(regex = "android".toRegex(RegexOption.IGNORE_CASE), category = "mobile")
        )

        /**
         * The default configuration for the "ANDROID" innertube client.
         */
        public val DEFAULT_TVHTML5: ClientConfig = ClientConfig(
            originUrl = "https://www.youtube.com",
            api = InnertubeApi("https://www.youtube.com/youtubei"),
            contextVersioning = Versioning(name = "TVHTML5_SIMPLY_EMBEDDED_PLAYER", version = "2.0"),
            userAgentRequirements = UserAgentRequirements(regex = "android".toRegex(RegexOption.IGNORE_CASE))
        )

        /**
         * Creates an instance of [ClientConfig] from the supplied [YouTube config][YoutubeConfig].
         *
         * @param config the YouTube config to use.
         * @return the created client config.
         */
        public fun fromYoutubeConfig(config: YoutubeConfig) : ClientConfig {
            val originUrl = config.innertubeContext.client.originalUrl
                ?.removeSuffix("/")
                ?: error("No 'originalUrl' field found in innertube context")

            return ClientConfig(
                originUrl = originUrl,
                platform = config.innertubeContext.client.platform ?: ClientPlatform.DESKTOP,
                api = InnertubeApi(
                    url = "$originUrl/youtubei",
                    key = config.innertubeApiKey,
                    version = config.innertubeApiVersion.removePrefix("v")
                ),
                contextVersioning = Versioning(
                    name = config.innertubeClientName,
                    version = config.innertubeClientVersion,
                ),
                headerVersioning = Versioning(
                    name = config.innertubeContextClientName.toString(),
                    version = config.innertubeContextClientVersion,
                ),
                other = Other(
                    pageCl = config.pageCl.toString(),
                    pageBuildLabel = config.pageBuildLabel
                )
            )
        }
    }

    public data class Other(
        val pageCl: String? = null,
        val pageBuildLabel: String? = null,
    )

    public data class UserAgentRequirements(
        val regex: Regex? = null,
        val category: String? = null,
    ) {
        /**
         * Checks if the supplied user agent matches the requirements.
         *
         * @param userAgent the user agent to check.
         */
        public fun matches(userAgent: UserAgent): Boolean = matchesCategory(userAgent) && matchesRegex(userAgent)

        /**
         * Checks if the supplied user agent matches the required regex.
         *
         * @param userAgent the user agent to check.
         */
        public fun matchesRegex(userAgent: UserAgent) : Boolean = regex?.containsMatchIn(userAgent.toString()) ?: true

        /**
         * Checks if the supplied user agent matches the required category.
         *
         * @param ua the user agent to check.
         */
        public fun matchesCategory(ua: UserAgent): Boolean = category?.let { ua.deviceCategory == it } ?: true
    }
}
