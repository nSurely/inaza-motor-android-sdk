package models.policy

// Add constrained types here
abstract class PolicyExcessBase {
    abstract val voluntary: Int
    abstract val compulsory: Int
}

@kotlinx.serialization.Serializable
class PolicyExcess(override val voluntary: Int = 0, override val compulsory: Int = 0) :
    PolicyExcessBase()