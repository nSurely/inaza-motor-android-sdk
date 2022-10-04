package models.policy

import helpers.KOffsetDateTimeSerializer
import java.time.OffsetDateTime


abstract class PolicyIssuerBase {
    abstract val id: String?
    abstract val policyAgreedAt: OffsetDateTime?
}

@kotlinx.serialization.Serializable
class PolicyIssuer(
    override val id: String? = null,
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    override val policyAgreedAt: OffsetDateTime? = null
) : PolicyIssuerBase()


