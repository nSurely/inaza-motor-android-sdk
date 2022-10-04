package models.policy

data class Terms(
    val url: String? = null,
    val html: String? = null,
    val attachments: List<String>? = listOf(),
    val terms_requires_driver_esignature: Boolean
)