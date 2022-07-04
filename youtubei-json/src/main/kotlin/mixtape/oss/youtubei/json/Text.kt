package mixtape.oss.youtubei.json

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
public sealed class Text {
    public abstract val accessibility: Accessibility?

    @Serializable
    public data class Simple(
        @SerialName("simpleText")
        val value: String,
        override val accessibility: Accessibility? = null
    ) : Text()

    @Serializable
    public data class Advanced(
        val runs: List<Run> = emptyList(),
        override val accessibility: Accessibility? = null
    ) : Text() {
        @Serializable
        public data class Run(
            val text: String,
            val navigationEndpoint: NavigationEndpoint? = null,
            val italics: Boolean = false,
            val bold: Boolean = false,
        )
    }

    public object JsonSerializer : JsonContentPolymorphicSerializer<Text>(Text::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out Text> {
            return with(element.jsonObject) {
                when {
                    "runs" in this -> Advanced.serializer()
                    "simpleText" in this -> Simple.serializer()
                    else -> throw IllegalArgumentException("Unknown Text type")
                }
            }
        }
    }
}

