package mixtape.oss.youtubei.tools

import eu.bitwalker.useragentutils.DeviceType
import eu.bitwalker.useragentutils.Manufacturer
import eu.bitwalker.useragentutils.UserAgent.parseUserAgentString
import mixtape.koyo.extensions.capitalize
import ua_parser.Parser

public data class UserAgentInformation(
    val browser: Browser,
    val device: Device,
) {
    public companion object {
        public fun fromUserAgent(userAgent: String): UserAgentInformation {
            val parsed1 = parseUserAgentString(userAgent)
            val parsed2 = Parser().parse(userAgent)

            return UserAgentInformation(
                browser = Browser(
                    name = parsed2?.userAgent?.family ?: parsed1.browser?.group?.name?.lowercase()?.capitalize() ?: "",
                    version = parsed1?.browserVersion?.version ?: "",
                ),
                device = Device(
                    manufacturer = parsed1?.operatingSystem?.manufacturer
                        ?.takeUnless { it == Manufacturer.OTHER }
                        ?.name?.lowercase()?.capitalize() ?: "",
                    model = parsed2?.device?.family?.takeUnless { it == "Other" } ?: "",
                    os = OperatingSystem(
                        name = parsed2?.os?.family ?: "",
                        version = parsed2?.os?.let {
                            "${it.major ?: ""}${it.minor.ifNotBlank { ".$it" }}${it.patch.ifNotBlank { ".$it" }}${it.patchMinor.ifNotBlank { ".$it" }}"
                        } ?: "",
                        isMobile = parsed1?.operatingSystem?.deviceType == DeviceType.MOBILE
                    )
                )
            )
        }

        private fun String?.ifNotBlank(block: (String) -> String): String {
            return if (isNullOrBlank()) "" else block(this)
        }
    }

    public data class Browser(
        val name: String,
        val version: String,
    )

    public data class Device(
        val model: String,
        val manufacturer: String,
        val os: OperatingSystem,
    )

    public data class OperatingSystem(
        val name: String,
        val version: String,
        val isMobile: Boolean,
    )
}

