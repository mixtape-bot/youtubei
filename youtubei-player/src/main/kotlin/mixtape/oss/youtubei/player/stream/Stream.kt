package mixtape.oss.youtubei.player.stream

import arrow.core.Option
import arrow.core.orElse
import arrow.core.toOption
import io.ktor.http.*
import mixtape.oss.youtubei.json.StreamingFormat
import mixtape.oss.youtubei.player.script.PlayerScript

/**
 * Represents an innertube player stream.
 */
public data class Stream(
    /**
     * The ID of the player script that is used to render the player, or null if it is unknown.
     */
    val playerScript: PlayerScript?,
    /**
     * The base url for this stream.
     */
    val url: String,
    /**
     * The signature cipher for this stream, or null if the [stream url][url] is not signed.
     */
    val signature: String?,
    /**
     * The parameter that contains the signature cipher for this stream.
     */
    val signatureParam: String,
    /**
     * The n-parameter for this stream.
     */
    val nParameter: String?,
    /**
     * The format of this stream.
     */
    val format: StreamFormat,
) {
    public companion object {
        /**
         * The default signature url parameter.
         *
         * @see Stream.signatureParam
         */
        public const val DEFAULT_SIGNATURE_PARAM: String = "sig"

        /**
         *
         */
        public fun fromInnertubeFormat(
            format: StreamingFormat,
            playerScript: PlayerScript? = null
        ): Stream {
            val cipherInfo = (format.cipher ?: format.signatureCipher)
                ?.let { parseQueryString(it) }
                ?: Parameters.Empty

            val urlParameters = run {
                val url = format.url
                    ?: cipherInfo["url"]
                    ?: missingStreamUrl(format)

                parseQueryString(url)
            }

            return Stream(
                playerScript = playerScript,
                url = cipherInfo["url"] ?: format.url ?: missingStreamUrl(format),
                signature = cipherInfo["s"],
                signatureParam = cipherInfo["sp"] ?: DEFAULT_SIGNATURE_PARAM,
                nParameter = urlParameters["n"],
                format = StreamFormat.fromInnertubeFormat(format),
            )
        }

        private fun missingStreamUrl(format: StreamingFormat): Nothing =
            error("Unable to find stream url in $format")
    }

    /**
     * The URI of this stream.
     *
     * @see Stream.url
     */
    val uri: Url get() = Url(url)

    /**
     * Returns this stream with the supplied [playerScript].
     *
     * @param playerScript The player script to use.
     * @return This stream with the supplied [playerScript].
     */
    public fun withPlayerScript(playerScript: PlayerScript): Stream =
        copy(playerScript = playerScript)

    /**
     * Returns the signed url for this stream, or null if it could not be signed.
     *
     * @see Stream.signature
     */
    public fun getSignedUrl(): Option<Url> {
        require(playerScript != null) {
            "Cannot get signed url for stream without player script"
        }

        return playerScript
            .getSignedStreamUrl(this)
            .orElse { uri.takeIf { signatureParam in it.encodedQuery }.toOption() }
    }
}
