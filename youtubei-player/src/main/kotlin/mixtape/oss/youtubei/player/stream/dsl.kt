package mixtape.oss.youtubei.player.stream

import mixtape.koyo.log.logging
import mixtape.oss.youtubei.json.player.PlayerResponse
import mixtape.oss.youtubei.player.script.PlayerScript

private val log by logging("mixtape.oss.youtubei.player.stream.dsl")

/**
 * Returns all streams within this player response, or an empty list if none are found.
 *
 * @param playerScript optional [PlayerScript] to assign to each stream.
 * @return list of [Stream]s.
 */
public fun PlayerResponse.getStreams(playerScript: PlayerScript? = null): List<Stream> {
    val streams = mutableListOf<Stream>()
    for (format in (streamingData.formats + streamingData.adaptiveFormats)) {
        if (format.contentLength == null && !videoDetails.isLive) {
            log.debug { "Video is not a live stream, but no contentLength found in format. Skipping..." }
            continue
        }

        streams.add(Stream.fromInnertubeFormat(format, playerScript))
    }

    return streams
}
