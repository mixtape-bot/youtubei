@file:UseSerializers(
    EndpointContext.ThirdParty.JsonSerializer::class
)

package mixtape.oss.youtubei.json.client

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import mixtape.koyo.encoding.json.NonDiscriminatoryPolymorphicSerializer

@Serializable
public data class
EndpointContext(
    val client: ClientContext,
    val user: User = User(),
    val request: Request = Request(),
    val thirdParty: ThirdParty = ThirdParty.None,
) {
    @Serializable
    public data class User(val lockedSafetyMode: Boolean = false)

    @Serializable
    public data class Request(val useSsl: Boolean = true)


    @Serializable
    public sealed class ThirdParty {
        @Serializable
        public data class Embed(val embedUrl: String) : ThirdParty()

        @Serializable
        public object None : ThirdParty() {
            override fun toString(): String = "ThirdPartyContext.None"
        }

        public object JsonSerializer : NonDiscriminatoryPolymorphicSerializer<ThirdParty>(serializer())
    }
}

