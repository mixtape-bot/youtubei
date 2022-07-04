import mixtape.oss.youtubei.player.script.PlayerScriptInfo
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerScriptInfoTests {
    val urls = listOf(
        "https://www.youtube.com/s/player/64dddad9/player_ias.vflset/en_US/base.js" to "64dddad9",
        "https://www.youtube.com/s/player/64dddad9/player_ias.vflset/fr_FR/base.js" to "64dddad9",
        "https://www.youtube.com/s/player/64dddad9/player-plasma-ias-phone-en_US.vflset/base.js" to "64dddad9",
        "https://www.youtube.com/s/player/64dddad9/player-plasma-ias-phone-de_DE.vflset/base.js" to "64dddad9",
        "https://www.youtube.com/s/player/64dddad9/player-plasma-ias-tablet-en_US.vflset/base.js" to "64dddad9",
        // obsolete
        "https://www.youtube.com/yts/jsbin/player_ias-vfle4-e03/en_US/base.js" to "vfle4-e03",
        "https://www.youtube.com/yts/jsbin/player_ias-vfl49f_g4/en_US/base.js" to "vfl49f_g4",
        "https://www.youtube.com/yts/jsbin/player_ias-vflCPQUIL/en_US/base.js" to "vflCPQUIL",
        "https://www.youtube.com/yts/jsbin/player-vflzQZbt7/en_US/base.js" to "vflzQZbt7",
        "https://www.youtube.com/yts/jsbin/player-en_US-vflaxXRn1/base.js" to "vflaxXRn1",
        "https://s.ytimg.com/yts/jsbin/html5player-en_US-vflXGBaUN.js" to "vflXGBaUN",
        "https://s.ytimg.com/yts/jsbin/html5player-en_US-vflKjOTVq/html5player.js" to "vflKjOTVq",
    )

    @Test
    fun `PlayerInfo#extractFromUrl returns the correct player ids`() {
        for ((url, expectedId) in urls) {
            val extracted = PlayerScriptInfo.extractFromUrl(url)
            assertEquals(expectedId, extracted?.id, url)
        }
    }
}

