package mixtape.oss.youtubei.player.script

/**
 * Represents information about a YouTube player script url.
 */
public data class PlayerScriptInfo(
    val url: String,
    val id: String,
) {
    public companion object {
        private val PLAYER_INFO_REGEX: List<Regex> = listOf(
            """/s/player/(?<id>[a-zA-Z0-9_-]{8,})""".toRegex(),
            """/(?<id>[a-zA-Z0-9_-]{8,})/player(?:_ias\.vflset(?:/[a-zA-Z]{2,3}_[a-zA-Z]{2,3})?|-plasma-ias-(?:phone|tablet)-[a-z]{2}_[A-Z]{2}\.vflset)/base\.js$""".toRegex(),
            """\b(?<id>vfl[a-zA-Z0-9_-]+)\b.*?\.js$""".toRegex()
        )

        /**
         * Extracts the player id from the given url.
         * 
         * @param url the url to extract the player id from
         * @return the [PlayerScriptInfo] or null if no information could be extracted.
         */
        public fun extractFromUrl(url: String): PlayerScriptInfo? {
            return PLAYER_INFO_REGEX
                .firstNotNullOfOrNull { re -> re.find(url)?.groups?.get("id")?.value }
                ?.let { id -> PlayerScriptInfo(url, id) }
        }
    }
}
