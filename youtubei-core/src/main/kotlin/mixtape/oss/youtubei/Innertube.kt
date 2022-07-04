@file:Suppress("MemberVisibilityCanBePrivate")

package mixtape.oss.youtubei

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.serialization.json.JsonObject
import mixtape.koyo.encoding.json.toJsonObject
import mixtape.koyo.log.logging
import mixtape.oss.youtubei.client.Client
import mixtape.oss.youtubei.client.ClientType
import mixtape.oss.youtubei.client.EndpointContextAugment
import mixtape.oss.youtubei.client.applyTo
import mixtape.oss.youtubei.json.client.EndpointContext
import mixtape.oss.youtubei.tools.JsonTools
import kotlin.time.measureTimedValue

public class Innertube {
    public companion object {
        private val log by logging { }
    }

    /**
     * The current clients used with this Innertube instance.
     */
    public val clients: MutableMap<ClientType, Client> = mutableMapOf()

    /**
     * Executes an innertube endpoint using the supplied [body], returning its response.
     *
     * @param endpoint   The innertube endpoint to execute.
     * @param body       The data to send.
     * @param clientType The client type to use for this endpoint.
     * @return The response.
     */
    public suspend fun execute(
        httpClient: HttpClient,
        endpoint: String,
        body: JsonObject,
        clientType: ClientType,
        augments: Set<EndpointContextAugment>
    ): HttpResponse {
        val client = get(clientType, httpClient)

        /* augment the context and modify the request data */
        val context = augments.fold(client.contextProvider.provide()) { context, augment ->
            augment.augment(context)
        }

        /* create new body with the newly provided context */
        val obj = body.toMutableMap()
        obj["context"] = JsonTools.DEFAULT_FORMAT.encodeToJsonElement(EndpointContext.serializer(), context)

        val content = JsonTools.DEFAULT_FORMAT.encodeToString(JsonObject.serializer(), obj.toJsonObject())

        /* create and execute the request. */
        val url = client.config.api.formUrl(endpoint)
        val (response, took) = measureTimedValue {
            val request = HttpRequestBuilder()
            request.url(url)
            request.setBody(TextContent(content, ContentType.Application.Json))
            request.contentType(ContentType.Application.Json)
            context.applyTo(client.config, request)

            httpClient.post(request)
        }

        log.debug { "<<< POST /$endpoint - $took $content" }
        return response
    }

    private suspend fun get(type: ClientType, httpClient: HttpClient): Client {
        if (type !in clients) {
            val client = type.factory.create(httpClient)
            clients[type] = client
        }

        return clients[type]!!
    }
}
