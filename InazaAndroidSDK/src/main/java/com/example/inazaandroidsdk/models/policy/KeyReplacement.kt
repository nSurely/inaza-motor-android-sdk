package models.policy

data class KeyReplacement(
    val cost: Int,
    val cover: Boolean,
    val coverLimit: Int
)