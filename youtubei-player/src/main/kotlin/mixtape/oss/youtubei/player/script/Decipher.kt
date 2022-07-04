package mixtape.oss.youtubei.player.script

import mixtape.koyo.extensions.truncate
import mixtape.koyo.lang.js.createJsContext
import mixtape.koyo.lang.js.evaluateJs
import org.graalvm.polyglot.Value
import java.io.Closeable

/**
 * Represents a YouTube n-token/signature de-cipher.
 *
 * @param source The de-cipher source code.
 */
public class Decipher(public val source: String) : Closeable {
    private lateinit var cached: Value

    private val context = createJsContext()

    /**
     * Applies this cipher to the supplied [text].
     *
     * @param text the text to be deciphered
     * @return the deciphered text
     */
    public fun apply(text: String): String? = synchronized(this) {
        if (!::cached.isInitialized) {
            cached = evaluateJs(source, context)
                ?: error("Unable to initialize decipher")
        }

        cached.execute(text).asString()
    }

    /**
     * Closes the javascript context used to execute this de-cipher.
     */
    override fun close() {
        context.close()
    }

    override fun toString(): String = "Decipher(source=${source.truncate(16, includeDots = true)})"
}
