package mixtape.oss.youtubei.client

import io.ktor.client.request.*
import io.ktor.http.*
import mixtape.oss.youtubei.client.config.ClientConfig
import mixtape.oss.youtubei.json.client.EndpointContext
import mixtape.oss.youtubei.tools.InnertubeHeaders

/**
 * Applies the supplied [config] and this [context][EndpointContext] to the given [request].
 *
 * @param config the configuration to apply
 * @param request the request to apply the configuration to
 */
public fun EndpointContext.applyTo(config: ClientConfig, request: HttpRequestBuilder) {
    val acceptLanguage = "${client.hostLanguage?.lowercase() ?: "en"}-${client.countryCode?.uppercase() ?: "US"}"
    request.headers[HttpHeaders.AcceptLanguage] = acceptLanguage

    client.userAgent?.let {
        request.headers[HttpHeaders.UserAgent] = it
    }

    /* YouTube related headers */
    client.originalUrl?.let {
        request.headers[InnertubeHeaders.Origin] = it
    }

    // client version information
    config.headerVersioning?.let { v ->
        request.headers[InnertubeHeaders.YoutubeClientName] = v.name
        request.headers[InnertubeHeaders.YoutubeClientVersion] = v.version
    }

    // uh idk
    config.other.pageCl?.let {
        request.headers[InnertubeHeaders.YoutubePageCl] = it
    }

    config.other.pageBuildLabel?.let {
        request.headers[InnertubeHeaders.YoutubePageLabel] = it
    }
}

