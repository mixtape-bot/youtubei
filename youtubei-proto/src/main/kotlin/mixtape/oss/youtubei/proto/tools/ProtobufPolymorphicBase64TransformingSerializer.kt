package mixtape.oss.youtubei.proto.tools

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer

public open class ProtobufPolymorphicBase64TransformingSerializer<T : Any>(
    public val serializer: KSerializer<T>
) : KSerializer<T> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(serializer.descriptor.serialName + "[String+B64]", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) {
        val protobuf = ProtoBuf.encodeToByteArray(value::class.serializer() as KSerializer<T>, value)
        encoder.encodeString(protobuf.toB64UrlString())
    }

    override fun deserialize(decoder: Decoder): T = error("well fuck")
}

