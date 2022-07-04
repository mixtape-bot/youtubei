package mixtape.oss.youtubei.tools.extensions

import io.ktor.client.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import mixtape.oss.youtubei.Innertube
import mixtape.oss.youtubei.client.ClientType
import mixtape.oss.youtubei.client.EndpointContextAugment
import mixtape.oss.youtubei.tools.JsonTools

public suspend inline fun <reified T : Any> Innertube.execute(
    httpClient: HttpClient,
    endpoint: String,
    value: T,
    clientType: ClientType,
    augments: Set<EndpointContextAugment> = emptySet(),
): HttpResponse = execute(
    httpClient = httpClient,
    endpoint = endpoint,
    body = JsonTools.DEFAULT_FORMAT.encodeToJsonElement(T::class.serializer(), value).jsonObject,
    clientType = clientType,
    augments = augments
)
