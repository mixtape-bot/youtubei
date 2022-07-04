package mixtape.oss.youtubei.tools

import kotlinx.serialization.json.Json

public object JsonTools {
    public val DEFAULT_FORMAT: Json = Json {
        isLenient = true
        encodeDefaults = false
        ignoreUnknownKeys = true
    }
}
