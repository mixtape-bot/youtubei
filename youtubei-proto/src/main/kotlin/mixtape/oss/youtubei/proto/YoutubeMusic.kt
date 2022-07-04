package mixtape.oss.youtubei.proto

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

/**
 * Represents parameters used for YouTube Music searching.
 * @see SearchParams.JsonSerializer
 */
@Serializable
public data class YoutubeMusicSearchParams(
    @ProtoNumber(2)
    val options: Options? = null,
    @ProtoNumber(13)
    val f13: F13? = null,
) : SearchParams() {
    public companion object {
        public val DEFAULT_F13: F13 = F13(listOf(3, 4, 9, 10, 5))

        public fun withFilters(filters: YoutubeMusicSearchFilters, f13: F13 = this.DEFAULT_F13): YoutubeMusicSearchParams {
            return YoutubeMusicSearchParams(Options(filters), f13)
        }
    }

    @Serializable
    public data class Options(
        @ProtoNumber(17) val filters: YoutubeMusicSearchFilters? = null,
    )

    @Serializable
    public data class F13(
        @ProtoNumber(2) val f2: List<Int>? = null,
    )
}

/**
 * Represents filters used for searching YouTube Music.
 * @see YoutubeMusicSearchParams
 */
@Serializable
public data class YoutubeMusicSearchFilters(
    @ProtoNumber(1) val songs: Boolean = false,
    @ProtoNumber(2) val videos: Boolean = false,
    @ProtoNumber(3) val albums: Boolean = false,
    @ProtoNumber(4) val artists: Boolean = false,
    @ProtoNumber(5) val f5: Boolean = false,
    @ProtoNumber(7) val featuredPlaylists: Boolean = false,
    @ProtoNumber(8) val communityPlaylists: Boolean = false,
) : SearchFilters
