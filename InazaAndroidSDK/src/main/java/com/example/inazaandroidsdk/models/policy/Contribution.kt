package models.policy

abstract class PolicyContributionBase {
    abstract val pc: Float

    // Change map to JSON object or similar
    abstract val details: MutableMap<String, String>?
}

@kotlinx.serialization.Serializable
class PolicyContribution(
    override val pc: Float = 100.0f,
    override val details: MutableMap<String, String>? = null
) : PolicyContributionBase()