package models.policy

data class Rescue(
    val cost: Int,
    val cover: Boolean,
    val coverLimit: Int
)