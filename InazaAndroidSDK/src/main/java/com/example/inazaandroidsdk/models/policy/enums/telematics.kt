package models.policy.enums

enum class PolicyTelematicsProcess(process: String) {
    NONE("none"),
    INDEFINITE("indefinite"),
    MIN_KM("min_km"),
    DAYS("days")
}