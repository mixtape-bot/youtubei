package mixtape.oss.youtubei.client

public open class ClientType(public val factory: ClientFactory) {
    public object Web : ClientType(createWebClientFactory())

    public object WebRemix : ClientType(createWebRemixClientFactory())

    public object Android : ClientType(createAndroidClientFactory())

    public object TvHtml5 : ClientType(createTvHtml5ClientFactory())
}

