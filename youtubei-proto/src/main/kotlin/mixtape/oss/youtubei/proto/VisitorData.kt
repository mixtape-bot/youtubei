package mixtape.oss.youtubei.proto

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber
import mixtape.koyo.generateUniqueId
import mixtape.oss.youtubei.proto.tools.ProtobufBase64TransformingSerializer
import kotlin.time.Duration.Companion.milliseconds

@Serializable
public data class VisitorData constructor(
    @ProtoNumber(1) val id: String,
    @ProtoNumber(5) val timestamp: Int,
) {
    public companion object {
        /**
         * Generate a new VisitorData object with a unique id and the current time.
         * @return The generated [VisitorData]
         */
        public fun generateRandom(): VisitorData = VisitorData(
            id = generateUniqueId().take(11),
            timestamp = System.currentTimeMillis().milliseconds.inWholeSeconds.toInt()
        )
    }

    public object JsonSerializer : ProtobufBase64TransformingSerializer<VisitorData>(serializer())
}
