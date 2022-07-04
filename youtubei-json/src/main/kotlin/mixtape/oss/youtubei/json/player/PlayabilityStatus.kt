package mixtape.oss.youtubei.json.player

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = PlayabilityStatus.Companion::class)
public sealed class PlayabilityStatus(public val name: String) {
    public object Ok : PlayabilityStatus("OK") {
        override fun toString(): String = "PlayabilityStatus::Ok"
    }

    public object Error : PlayabilityStatus("ERROR") {
        override fun toString(): String = "PlayabilityStatus::Error"
    }

    public object LoginRequired : PlayabilityStatus("LOGIN_REQUIRED") {
        override fun toString(): String = "PlayabilityStatus::LoginRequired"
    }

    public object Unplayable : PlayabilityStatus("UNPLAYABLE") {
        override fun toString(): String = "PlayabilityStatus::Unplayable"
    }

    public object LiveStreamOffline : PlayabilityStatus("LIVE_STREAM_OFFLINE") {
        override fun toString(): String = "PlayabilityStatus::LiveStreamOffline"
    }

    public object ContentCheckRequired : PlayabilityStatus("CONTENT_CHECK_REQUIRED") {
        override fun toString(): String = "PlayabilityStatus::ContentCheckRequired"
    }

    public class Unknown(name: String) : PlayabilityStatus(name) {
        override fun toString(): String = "PlayabilityStatus::Unknown(name=$name)"
    }

    public companion object : KSerializer<PlayabilityStatus> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("mixtape.oss.youtubei.json.player.PlayabilityStatus", PrimitiveKind.STRING)

        public fun values(): Array<PlayabilityStatus> {
            return arrayOf(
                Ok,
                Error, LoginRequired, Unplayable, LiveStreamOffline, ContentCheckRequired
            )
        }

        public fun valueOf(value: String): PlayabilityStatus {
            return when (value) {
                "OK" -> Ok
                "ERROR" -> Error
                "LOGIN_REQUIRED" -> LoginRequired
                "UNPLAYABLE" -> Unplayable
                "LIVE_STREAM_OFFLINE" -> LiveStreamOffline
                "CONTENT_CHECK_REQUIRED" -> ContentCheckRequired
                else -> Unknown(value)
            }
        }

        override fun serialize(encoder: Encoder, value: PlayabilityStatus) {
            encoder.encodeString(value.name)
        }

        override fun deserialize(decoder: Decoder): PlayabilityStatus =
            valueOf(decoder.decodeString())
    }
}
