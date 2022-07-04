package mixtape.oss.youtubei.player.script

import mixtape.koyo.extensions.substringAround

public object PlayerScriptExtractor {
    /**
     * Extracts a [PlayerScript] from the given [javascript code][code].
     *
     * @param info The information about the player.
     * @param code The javascript code to extract the [PlayerScript] from.
     */
    public fun extract(info: PlayerScriptInfo, code: String): PlayerScript? {
        val sts = extractSigTimestamp(code)
            ?: return null

        val sigDecipher = extractSigDecipher(code)
            ?: return null

        val ntokenDecipher = extractNTokenDecipher(code)
            ?: return null

        return PlayerScript(
            info,
            sts,
            Decipher(sigDecipher),
            Decipher(ntokenDecipher)
        )
    }

    /**
     * Extracts the signature timestamp from the supplied [player script][script].
     *
     * @param script The player script.
     * @return The signature timestamp.
     */
    public fun extractSigTimestamp(script: String): Long? {
        // TODO: extract using regex first, then fallback to substring
        val timestamp = script.substringAround("signatureTimestamp:", ",")
        return timestamp?.toLongOrNull()
    }

    /**
     * Extracts the signature decipher script from the supplied [player script][script].
     *
     * @param script the player script to extract the signature decipher script from
     * @return the signature decipher script
     */
    public fun extractSigDecipher(script: String): String? {
        val context = script.substringAround("this.audioTracks};var", "};")
            ?: return null
        val cipher = script.substringAround("""function(a){a=a.split("");""", """};""")
            ?: return null
        return """(function(a){a=a.split("");var$context};$cipher})""".trimIndent()
    }

    /**
     * Extracts the N-token decipher script from the supplied [player script][script].
     *
     * @param script The player script.
     * @return The N-token decipher script.
     */
    public fun extractNTokenDecipher(script: String): String? {
        val cipher = script.substringAround("""b=a.split("")""", """}return b.join("")}""")
            ?: return null
        return """(function(a){var b=a.split("")$cipher}return b.join("");})"""
    }
}
