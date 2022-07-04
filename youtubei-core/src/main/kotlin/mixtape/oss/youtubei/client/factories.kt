package mixtape.oss.youtubei.client

import mixtape.oss.youtubei.client.config.ClientConfig

/**
 * Creates a new dynamic
 */
public fun createWebClientFactory(): ClientFactory =
    DynamicClientFactory("https://www.youtube.com/", ClientConfig.DEFAULT_WEB)

/**
 * Creates a new dynamic `WEB_REMIX` client for https://music.youtube.com.
 */
public fun createWebRemixClientFactory(): ClientFactory =
    DynamicClientFactory("https://music.youtube.com/", ClientConfig.DEFAULT_WEB_REMIX)

/**
 * Creates a new static `ANDROID` client.
 */
public fun createAndroidClientFactory(): ClientFactory = StaticClientFactory(ClientConfig.DEFAULT_ANDROID)

/**
 * Creates a new static `TVHTML5` client.
 *
 * @see StaticClient
 */
public fun createTvHtml5ClientFactory(): ClientFactory = StaticClientFactory(ClientConfig.DEFAULT_TVHTML5)

