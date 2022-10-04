package models.policy

data class Theft(
    val cost: Int,
    val cover: Boolean,
    val coverLimit: Int
)