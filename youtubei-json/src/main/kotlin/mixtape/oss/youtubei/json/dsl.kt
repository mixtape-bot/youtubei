package mixtape.oss.youtubei.json

public fun Text.Advanced.Run.format(): String {
    var t = text
    if (bold) t = "**$t**"
    if (italics) t = "__${t}__"
    return t
}

public fun Text.Advanced.format(markdown: Boolean = false): String {
    return runs.joinToString(separator = "") { if (markdown) it.format() else it.text }
}

public fun Text.format(markdown: Boolean = false): String = when (this) {
    is Text.Advanced -> format(markdown)
    is Text.Simple -> value
}
