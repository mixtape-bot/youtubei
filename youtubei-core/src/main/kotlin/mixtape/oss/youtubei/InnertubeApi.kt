package mixtape.oss.youtubei

import io.ktor.http.*
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Represents the YouTube Innertube API.
 */
public data class InnertubeApi(
    /**
     * The base url of this innertube api, must not include the version.
     */
    val url: String,
    /**
     * The api key to use.
     */
    val key: String = DEFAULT_KEY,
    /**
     * The api version to use, must not be prefixed with `v`.
     */
    val version: String = DEFAULT_VERSION,
) {
    public companion object {
        /**
         * The default api key to use, it is not recommended using this as it can be invalidated at any time.
         */
        public const val DEFAULT_KEY: String = "AIzaSyA8eiZmM1FaDVjRy-df2KTyQ_vz_yYM39w"

        /**
         * The default version of the innertube api.
         */
        public const val DEFAULT_VERSION: String = "1"
    }

    /**
     * Form a working url for the given [endpoint].
     *
     * @param endpoint The endpoint to use.
     * @param block Used to add upon the url, see [URIBuilder].
     */
    public inline fun formUrl(endpoint: String, block: URLBuilder.() -> Unit = {}): Url {
        contract {
            callsInPlace(block, InvocationKind.EXACTLY_ONCE)
        }

        val url = URLBuilder("$url/v$version/$endpoint")
        url.parameters["key"] = key
        url.apply(block)

        return url.build()
    }
}
