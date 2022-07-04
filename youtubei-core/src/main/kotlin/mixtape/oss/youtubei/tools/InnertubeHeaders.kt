package mixtape.oss.youtubei.tools

/**
 * Common headers used with innertube
 */
public object InnertubeHeaders {
    public const val Origin: String = "X-Origin"

    // Client-metadata
    public const val YoutubeClientName: String = "X-Youtube-Client-Name"
    public const val YoutubeClientVersion: String = "X-Youtube-Client-Version"

    // Build information (?)
    public const val YoutubePageCl: String = "X-Youtube-Page-Cl"
    public const val YoutubePageLabel: String = "X-Youtube-Page-Label"
}
