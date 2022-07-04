package mixtape.oss.youtubei.proto

import kotlinx.serialization.Serializable
import mixtape.oss.youtubei.proto.tools.ProtobufPolymorphicBase64TransformingSerializer

public interface SearchFilters

@Serializable
public sealed class SearchParams {
    public object JsonSerializer : ProtobufPolymorphicBase64TransformingSerializer<SearchParams>(serializer())
}

