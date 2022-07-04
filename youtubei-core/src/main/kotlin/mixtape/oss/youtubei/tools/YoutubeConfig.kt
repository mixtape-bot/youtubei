package mixtape.oss.youtubei.tools

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mixtape.oss.youtubei.json.client.EndpointContext

@Serializable
public data class YoutubeConfig(
    @SerialName("INNERTUBE_API_KEY")
    val innertubeApiKey: String,
    @SerialName("INNERTUBE_API_VERSION")
    val innertubeApiVersion: String,
    @SerialName("INNERTUBE_CLIENT_NAME")
    val innertubeClientName: String,
    @SerialName("INNERTUBE_CLIENT_VERSION")
    val innertubeClientVersion: String,
    @SerialName("INNERTUBE_CONTEXT")
    val innertubeContext: EndpointContext,
    @SerialName("INNERTUBE_CONTEXT_CLIENT_NAME")
    val innertubeContextClientName: Int,
    @SerialName("INNERTUBE_CONTEXT_CLIENT_VERSION")
    val innertubeContextClientVersion: String,
    @SerialName("PAGE_BUILD_LABEL")
    val pageBuildLabel: String,
    @SerialName("PAGE_CL")
    val pageCl: Int,
)
