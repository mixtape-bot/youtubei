package mixtape.oss.youtubei.proto.tools

import mixtape.koyo.encoding.base64.decodeBase64Bytes
import mixtape.koyo.encoding.base64.encodeBase64String
import java.net.URLDecoder

public fun ByteArray.toB64UrlString(): String {
    return encodeBase64String()
        .replace('+', '-')
        .replace('/', '_')
        .replace("=", "%3D")
}

public fun String.fromB64UrlToBytes(): ByteArray {
    return URLDecoder.decode(this, Charsets.UTF_8)
        .replace('-', '+')
        .replace('_', '/')
        .decodeBase64Bytes()
}
