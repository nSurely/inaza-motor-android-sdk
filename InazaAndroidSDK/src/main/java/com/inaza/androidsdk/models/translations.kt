package com.inaza.androidsdk.models

import kotlinx.serialization.Serializable

@Serializable
data class Translations(
    val display: MutableMap<String, String?>,
    val description: MutableMap<String, String?>,
    val variant: MutableMap<String, String?>,
    val brand: MutableMap<String, String?>,
    val model: MutableMap<String, String?>
)