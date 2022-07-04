import kotlinx.serialization.protobuf.ProtoBuf
import mixtape.oss.youtubei.proto.YoutubeMusicSearchFilters
import mixtape.oss.youtubei.proto.YoutubeMusicSearchParams
import mixtape.oss.youtubei.proto.tools.fromB64UrlToBytes
import kotlin.test.Test
import kotlin.test.assertEquals

class YoutubeMusicSearchParamsTests {
    private val f13: YoutubeMusicSearchParams.F13 = YoutubeMusicSearchParams.F13(listOf(3, 10, 9, 5, 4))
    private val params: Map<String, YoutubeMusicSearchParams> = mapOf(
        "EgWKAQIIAWoKEAMQChAJEAUQBA%3D%3D" to createYmsp(YoutubeMusicSearchFilters(songs = true)),
        "EgeKAQQoAEABagoQAxAKEAkQBRAE" to createYmsp(YoutubeMusicSearchFilters(f5 = false, communityPlaylists = true)),
        "EgWKAQIYAWoKEAMQChAJEAUQBA%3D%3D" to createYmsp(YoutubeMusicSearchFilters(albums = true)),
        "EgWKAQIgAWoKEAMQChAJEAUQBA%3D%3D" to createYmsp(YoutubeMusicSearchFilters(artists = true)),
        "EgWKAQIQAWoKEAMQChAJEAUQBA%3D%3D" to createYmsp(YoutubeMusicSearchFilters(videos = true)),
    )

    @Test
    fun `YoutubeMusicSearchParams correctly represents parameters used by youtube`() {
        for ((b64, expected) in params) {
            val b64bytes = b64.fromB64UrlToBytes()
            val actual = ProtoBuf.decodeFromByteArray(YoutubeMusicSearchParams.serializer(), b64bytes)
            assertEquals(expected, actual)
        }
    }

    private fun createYmsp(filters: YoutubeMusicSearchFilters, f13: YoutubeMusicSearchParams.F13 = this.f13): YoutubeMusicSearchParams {
        return YoutubeMusicSearchParams(YoutubeMusicSearchParams.Options(filters), f13)
    }
}

