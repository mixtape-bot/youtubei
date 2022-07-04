package mixtape.oss.youtubei.player.stream

import io.ktor.http.*
import mixtape.oss.youtubei.json.StreamingFormat

/**
 * Represents the format of a [Stream].
 */
public data class StreamFormat(
    /**
     * The bitrate of this format.
     */
    val bitrate: Long,
    /**
     * The number of audio channels of this format.
     */
    val channelCount: Int,
    /**
     * The content type of this format.
     */
    val contentType: ContentType,
    /**
     * The byte length of this format.
     */
    val contentLength: Long?,
) {
    public companion object {
        public const val UNKNOWN_BITRATE: Long = -1

        /**
         * Creates a [StreamFormat] from the supplied [innertube format][PlayerResponse.StreamingFormat] object.
         *
         * @param format The innertube format object.
         * @return The created [StreamFormat] object.
         */
        public fun fromInnertubeFormat(format: StreamingFormat): StreamFormat = StreamFormat(
            bitrate = format.bitrate ?: UNKNOWN_BITRATE,
            channelCount = format.audioChannels,
            contentType = ContentType.parse(format.mimeType),
            contentLength = format.contentLength
        )
    }

    /**
     * Whether the bitrate of this format is unknown.
     *
     * @see UNKNOWN_BITRATE
     */
    val isBitrateUnknown: Boolean
        get() = bitrate == UNKNOWN_BITRATE
}

