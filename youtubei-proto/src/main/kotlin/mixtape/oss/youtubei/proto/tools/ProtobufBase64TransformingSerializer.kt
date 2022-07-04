package mixtape.oss.youtubei.proto.tools

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf

public open class ProtobufBase64TransformingSerializer<T>(public val serializer: KSerializer<T>) : KSerializer<T> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(serializer.descriptor.serialName + "[String+B64]", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) {
        val protobuf = ProtoBuf.encodeToByteArray(serializer, value)
        encoder.encodeString(protobuf.toB64UrlString())
    }

    override fun deserialize(decoder: Decoder): T {
        val encoded = decoder.decodeString()
        return ProtoBuf.decodeFromByteArray(serializer, encoded.fromB64UrlToBytes())
    }
}

