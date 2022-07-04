package mixtape.oss.youtubei.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class ThumbnailContainer(val thumbnails: List<Image> = emptyList())

@Serializable
public data class Image(val url: String, val width: Int, val height: Int)

@Serializable
public data class Accessibility(@SerialName("accessibilityData") val data: Data) {
    @Serializable
    public data class Data(val label: String)
}
