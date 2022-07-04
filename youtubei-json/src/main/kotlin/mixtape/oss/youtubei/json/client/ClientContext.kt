package mixtape.oss.youtubei.json.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mixtape.oss.youtubei.proto.VisitorData

@Serializable
public data class ClientContext(
    val clientName: String,
    val clientVersion: String,
    val clientScreen: String? = null,
    val clientFormFactor: String? = null,
    val screenDensityFloat: Float? = null,
    val screenPixelDensity: Int? = null,
    val screenHeightPoints: Int? = null,
    val screenWidthPoints: Int? = null,
    val deviceMake: String? = null,
    val deviceModel: String? = null,
    val browserName: String? = null,
    val browserVersion: String? = null,
    val osName: String? = null,
    val osVersion: String? = null,
    val platform: ClientPlatform? = null,
    val originalUrl: String? = null,
    @SerialName("hl")
    val hostLanguage: String? = null,
    @SerialName("gl")
    val countryCode: String? = null,
    val userAgent: String? = null,
    @Serializable(with = VisitorData.JsonSerializer::class)
    val visitorData: VisitorData? = null,
)
