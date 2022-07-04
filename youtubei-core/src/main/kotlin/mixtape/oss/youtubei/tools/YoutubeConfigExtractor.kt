package mixtape.oss.youtubei.tools

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import mixtape.koyo.encoding.json.deserialize
import mixtape.koyo.extensions.use
import mixtape.koyo.lang.js.createJsContext
import mixtape.koyo.lang.js.evaluateJs
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

public object YoutubeConfigExtractor {
    /**
     * Fetches the supplied [url] and returns a [YoutubeConfig] using the response.
     *
     * @param httpClient The ktor HTTP client to use for fetching the page.
     * @param url        The URL to fetch.
     */
    public suspend fun fromUrl(httpClient: HttpClient, url: String): YoutubeConfig {
        val html = httpClient
            .get(url)
            .bodyAsText()

        return fromHtml(html)
    }

    /**
     * Scrapes the supplied [html] and returns the [YoutubeConfig].
     *
     * @param html   The HTML to scrape.
     */
    public fun fromHtml(html: String): YoutubeConfig = use(createJsContext()) { ctx ->
        val bin = ctx.getBindings("js")
        ctx.eval("js", "const window = this;")

        /* parse html */
        val document = Jsoup.parse(html)

        /*  */
        val scripts = document.getElementsByTag("script")
            .toList()
            .filter { !it.hasAttr("src") }
            .map(Element::data)

        for (script in scripts) {
            script.runCatching { evaluateJs(script, ctx) }
        }

        evaluateJs("var output = JSON.stringify(ytcfg.data_);", ctx)
        bin.getMember("output")
            .asString()
            .deserialize()
    }
}
