package mixtape.oss.youtubei.player.script

import arrow.core.None
import arrow.core.Option
import arrow.core.toOption
import io.ktor.http.*
import mixtape.koyo.extensions.unwrap
import mixtape.koyo.log.logging
import mixtape.oss.youtubei.player.stream.Stream

public data class PlayerScript(
    val info: PlayerScriptInfo,
    val signatureTimestamp: Long,
    val signatureDecipher: Decipher,
    val nTokenDecipher: Decipher,
) : AutoCloseable {
    public companion object {
        private val log by logging {  }
    }

    /**
     * Returns the signed url for the supplied [Stream1], or null if the signature could not be deciphered.
     *
     * @param stream The stream to get the signed url for.
     * @return The signed url for the supplied [Stream1].
     */
    public fun getSignedStreamUrl(stream: Stream): Option<Url> {
        val uri = URLBuilder(stream.uri)

        /* decipher the stream signature. */
        if (stream.signature != null) {
            val deciphered = decipherSignature(stream.signature)
            if (deciphered.isEmpty()) {
                log.debug { "Could not decipher signature for stream ${stream.uri}" }
                return None
            }

            uri.parameters[stream.signatureParam] = deciphered.unwrap()
        }

        /* attempt to decipher ntoken. */
        stream.nParameter
            ?.let { decipherNToken(it) }
            ?.tap { nToken -> uri.parameters["n"] = nToken }

        /* return the signed uri */
        return uri.build().toOption()
    }

    /**
     * Deciphers the supplied [signature].
     *
     * @param signature The signature to decipher.
     * @return The deciphered text.
     */
    public fun decipherSignature(signature: String): Option<String> {
        log.debug { "deciphering signature: $signature w/ player script ${info.url}" }
        return try {
            signatureDecipher.apply(signature).toOption()
        } catch (ex: Exception) {
            log.debug(ex) { "exception occurred while deciphering signature: $signature w/ player script ${info.url}" }
            None
        }
    }

    /**
     * Deciphers the supplied [nToken].
     *
     * @param nToken The N-token to decipher.
     * @return The deciphered text.
     */
    public fun decipherNToken(nToken: String): Option<String> {
        log.debug { "deciphering n-token: $nToken w/ player script ${info.url}" }
        return try {
            nTokenDecipher.apply(nToken).toOption()
        } catch (ex: Exception) {
            log.debug(ex) { "exception occurred while deciphering n-token: $nToken w/ player script ${info.url}" }
            None
        }
    }

    override fun close() {
        signatureDecipher.close()
        nTokenDecipher.close()
    }
}

