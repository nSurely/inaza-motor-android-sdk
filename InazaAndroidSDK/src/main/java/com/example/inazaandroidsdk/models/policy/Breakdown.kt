package models.policy

data class Breakdown(
    val cost: Int,
    val cover: Boolean,
    val coverLimit: Int
)