import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking
import mixtape.oss.youtubei.player.script.PlayerScriptManager
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class PlayerScriptTests {
    private val signatureTests = listOf(
        CipherTest(
            "https://www.youtube.com/s/player/c403842a/player_ias.vflset/en_US/base.js",
            ciphered = "e2===w0TOGpve9LwYkJ=OvzT91B_lLX9LoEVyQlE-oSqb-RNDQICsW29Ru3bvCM4xqBS3oeCD04p8khBcBn3PH9kdma-UiCgIQRwAJQ0qO88",
            deciphered = "AOq0QJ8wRQIgCiU-amdk9HP3nBcBhk8p40DCeo3SBqx4MCvb3uR9eWsCIQDNR-bqSo-ElQyVEoL9XLl_B19TzvO2JkYwL9evpGOT0w=="
        )
    )

    private val nTokenTests = listOf(
        CipherTest(
            "https://www.youtube.com/s/player/c403842a/player_ias.vflset/en_US/base.js",
            ciphered = "Katx9kUE3HLnNJHouSca",
            deciphered = "So0zDkCWnS8pLg"
        )
    )

    private val http = HttpClient(CIO)
    private val playerScripts = PlayerScriptManager(http)

    @Test
    fun `PlayerScript#decipherSignature successfully deciphers signatures`() = runBlocking {
        for (signature in signatureTests) {
            val playerScript = playerScripts.fetch(signature.playerScript)
                ?: fail("Unable to fetch player script: ${signature.playerScript}")

            assertEquals(signature.deciphered, playerScript.decipherSignature(signature.ciphered).orNull())
        }
    }

    @Test
    fun `PlayerScript#decipherNToken successfully deciphers n-tokens`() = runBlocking {
        for (nToken in nTokenTests) {
            val playerScript = playerScripts.fetch(nToken.playerScript)
                ?: fail("Unable to fetch player script: ${nToken.playerScript}")

            assertEquals(nToken.deciphered, playerScript.decipherNToken(nToken.ciphered).orNull())
        }
    }

    data class CipherTest(val playerScript: String, val ciphered: String, val deciphered: String)
}
