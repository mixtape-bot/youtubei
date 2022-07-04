@file:Suppress("MemberVisibilityCanBePrivate")

package mixtape.oss.youtubei.proto.builder

import mixtape.oss.youtubei.proto.YoutubeMusicSearchFilters
import mixtape.oss.youtubei.proto.YoutubeMusicSearchParams
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class YoutubeMusicSearchParamsBuilder {
    public var options: YoutubeMusicSearchOptionsBuilder = YoutubeMusicSearchOptionsBuilder()
    public var f13: YoutubeMusicSearchParams.F13? = null

    public fun options(block: YoutubeMusicSearchOptionsBuilder.() -> Unit) {
        options = YoutubeMusicSearchOptionsBuilder().apply(block)
    }

    public fun build(): YoutubeMusicSearchParams {
        return YoutubeMusicSearchParams(
            options = options.build(),
            f13 = f13,
        )
    }
}

public class YoutubeMusicSearchOptionsBuilder {
    public var filters: YoutubeMusicSearchFiltersBuilder = YoutubeMusicSearchFiltersBuilder()

    public fun filters(block: YoutubeMusicSearchFiltersBuilder.() -> Unit) {
        filters = YoutubeMusicSearchFiltersBuilder().apply(block)
    }

    public fun build(): YoutubeMusicSearchParams.Options {
        return YoutubeMusicSearchParams.Options(filters.build())
    }
}

public class YoutubeMusicSearchFiltersBuilder(
    public var songs: Boolean = false,
    public var videos: Boolean = false,
    public var albums: Boolean = false,
    public var artists: Boolean = false,
    public var f5: Boolean = false,
    public var featuredPlaylists: Boolean = false,
    public var communityPlaylists: Boolean = false,
) {
    public fun build(): YoutubeMusicSearchFilters {
        return YoutubeMusicSearchFilters(
            songs,
            videos,
            albums,
            artists,
            f5,
            featuredPlaylists,
            communityPlaylists,
        )
    }
}

public inline fun musicSearchParams(block: YoutubeMusicSearchParamsBuilder.() -> Unit): YoutubeMusicSearchParams {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return YoutubeMusicSearchParamsBuilder()
        .apply(block)
        .build()
}
