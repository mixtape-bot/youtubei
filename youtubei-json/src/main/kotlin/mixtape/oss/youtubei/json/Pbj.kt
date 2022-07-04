package mixtape.oss.youtubei.json

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
@JvmInline
public value class Pbj(public val sections: List<PbjSection>)

@Serializable
public sealed interface PbjSection {
    @Serializable
    public data class Player(val playerResponse: PlayerResponse) : PbjSection

    @Serializable
    public object Unknown : PbjSection {
        override fun toString(): String = "Unknown"
    }

    public companion object : JsonContentPolymorphicSerializer<PbjSection>(PbjSection::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out PbjSection> {
            val jsonObj = element.jsonObject
            return when {
                "playerResponse" in jsonObj -> Player.serializer()
                else -> Unknown.serializer()
            }
        }
    }
}
