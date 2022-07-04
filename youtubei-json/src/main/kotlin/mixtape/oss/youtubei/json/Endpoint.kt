package mixtape.oss.youtubei.json

import mixtape.koyo.encoding.json.WrappedSerializer
import kotlinx.serialization.Serializable

@Serializable
public data class UrlEndpoint(val url: String) {
    public object Wrapped : WrappedSerializer<UrlEndpoint>(serializer())
}

@Serializable
public data class NavigationEndpoint(
    val clickTrackingParams: String,
    val browseEndpoint: BrowseEndpoint? = null,
    val watchEndpoint: WatchEndpoint? = null,
    val urlEndpoint: UrlEndpoint? = null,
)

@Serializable
public data class BrowseEndpoint(val browseId: String)

@Serializable
public data class WatchEndpoint(val videoId: String)

