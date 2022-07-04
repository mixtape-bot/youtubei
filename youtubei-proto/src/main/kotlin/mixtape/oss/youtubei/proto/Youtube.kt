package mixtape.oss.youtubei.proto

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

/**
 * The type of uploaded content to sort by.
 * @see YoutubeSearchFilters
 */
public enum class SearchResultType {
    Any,        /* 1 */
    Video,      /* 2 */
    Channel,    /* 3 */
    Playlist,   /* 4 */
    Movie,      /* 5 */
}

/**
 * What to sort the search results by.
 * @see YoutubeSearchFilters
 */
public enum class SortType {
    Relevance,
    Rating,
    UploadDate,
    ViewCount
}

/**
 * Search for videos uploaded in a certain time period.
 * @see YoutubeSearchFilters
 */
public enum class UploadDate {
    AllTime,
    Hour,
    Day,
    Week,
    Month,
    Year
}

/**
 * The duration of videos to search for.
 * @see YoutubeSearchFilters
 */
public enum class VideoDuration {
    All,
    Short,
    Long,
    Medium
}

/**
 * Represents parameters used for YouTube searching.
 * @see SearchParams
 */
@Serializable
public data class YoutubeSearchParams(
    @ProtoNumber(1) val sort: SortType? = null,
    @ProtoNumber(2) val filters: YoutubeSearchFilters? = null,
    @ProtoNumber(9) val offset: UInt? = null,
    @ProtoNumber(16) val id: List<String> = emptyList(),
    @ProtoNumber(22) val searchPosition: YoutubeSearchPosition? = null,
) : SearchParams()


@Serializable
public data class YoutubeSearchPosition(@ProtoNumber(1) val offset: Offset) {
    @Serializable
    public data class Offset(@ProtoNumber(1) val total: UInt, @ProtoNumber(2) val page: UInt)
}

/**
 * Represents filters used for YouTube searching.
 * @see YoutubeSearchParams
 */
@Serializable
public data class YoutubeSearchFilters(
    @ProtoNumber(1) val time: UploadDate? = null,
    @ProtoNumber(2) val type: SearchResultType? = null,
    @ProtoNumber(3) val duration: VideoDuration? = null,
    @ProtoNumber(4) val isHd: Boolean? = null,
    @ProtoNumber(5) val hasCc: Boolean? = null,
    @ProtoNumber(6) val createCommons: Boolean? = null,
    @ProtoNumber(7) val is3d: Boolean? = null,
    @ProtoNumber(8) val isLive: Boolean? = null,
    @ProtoNumber(9) val isPurchased: Boolean? = null,
    @ProtoNumber(14) val is4k: Boolean? = null,
    @ProtoNumber(15) val is360: Boolean? = null,
    @ProtoNumber(23) val hasLocation: Boolean? = null,
    @ProtoNumber(25) val isHdr: Boolean? = null,
    @ProtoNumber(26) val isVr180: Boolean? = null,
) : SearchFilters
