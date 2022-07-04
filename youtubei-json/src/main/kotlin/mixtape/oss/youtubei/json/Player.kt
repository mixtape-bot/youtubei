package mixtape.oss.youtubei.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.LongAsStringSerializer
import kotlinx.serialization.json.JsonNames
import mixtape.koyo.encoding.json.WrappedSerializer

@Serializable
public data class PlayerRequest(
    val racyCheckOk: Boolean,
    val contentCheckOk: Boolean,
    val videoId: String,
    @Serializable(with = ContentPlaybackContext.JsonSerializer::class)
    val playbackContext: ContentPlaybackContext,
)

@Serializable
public data class ContentPlaybackContext(val signatureTimestamp: Long) {
    public object JsonSerializer : WrappedSerializer<ContentPlaybackContext>(serializer())
}

@Serializable
public data class PlayerResponse(
    val videoDetails: VideoDetails,
    val streamingData: StreamingData = StreamingData(),
    val playabilityStatus: PlayabilityStatusBlock,
)

@Serializable
public data class VideoDetails(
    val videoId: String,
    val title: String,
    @Serializable(with = LongAsStringSerializer::class)
    val lengthSeconds: Long? = null,
    val channelId: String,
    val author: String,
    @JsonNames("isLive")
    val isLive: Boolean = false,
    val thumbnail: ThumbnailContainer = ThumbnailContainer(),
)

@Serializable
public data class PlayabilityStatusBlock(
    val playableInEmbed: Boolean = true,
    val status: PlayabilityStatus? = null,
    val reason: String? = null,
    @Serializable(with = LiveStreamabilityRenderer.JsonSerializer::class)
    val liveStreamability: LiveStreamabilityRenderer? = null,
    @Serializable(with = PlayerErrorMessageRenderer.JsonSerializer::class)
    val errorScreen: PlayerErrorMessageRenderer? = null
)

@Serializable
public object LiveStreamabilityRenderer {
    public object JsonSerializer : WrappedSerializer<LiveStreamabilityRenderer>(serializer())
}

@Serializable
public data class PlayerErrorMessageRenderer(
    @Serializable(with = Text.JsonSerializer::class)
    val reason: Text? = null,
    @Serializable(with = Text.JsonSerializer::class)
    val subreason: Text? = null,
    @Serializable(with = YpcTrailerRenderer.JsonSerializer::class)
    val ypcTrailerRenderer: YpcTrailerRenderer,
) {
    public object JsonSerializer : WrappedSerializer<PlayerErrorMessageRenderer>(serializer())
}

@Serializable
public object YpcTrailerRenderer {
    public object JsonSerializer : WrappedSerializer<YpcTrailerRenderer>(serializer())
}

@Serializable
public data class StreamingData(
    val formats: List<StreamingFormat> = emptyList(),
    val adaptiveFormats: List<StreamingFormat> = emptyList()
)

@Serializable
public data class StreamingFormat(
    val url: String? = null,
    val cipher: String? = null,
    val signatureCipher: String? = null,
    val mimeType: String,
    val bitrate: Long? = null,
    @Serializable(with = LongAsStringSerializer::class)
    val contentLength: Long? = null,
    val audioChannels: Int = 2,
)
