package models.policy

import kotlinx.datetime.LocalDate
import models.policy.enums.PaymentFrequency

// TODO constrain Ints

// add enum default to frequency
abstract class PolicyBasePremiumBase {
    abstract val value: Int
    abstract val payableImmediate: Boolean
    abstract val frequency: PaymentFrequency?
    abstract val useFrequency: Boolean
    abstract val nextPaymentDate: LocalDate?
    abstract val variable: Boolean

    // TODO missing in motorpy
    abstract val enabled: Boolean
}

@kotlinx.serialization.Serializable
class PolicyBasePremium(
    override val value: Int = 0,
    override val payableImmediate: Boolean = true,
    // TODO should be PaymentFrequency.LAST_BUSINESS_DAY_OF_YEAR?
    override val frequency: PaymentFrequency? = null,
    override val useFrequency: Boolean = false,
    override val nextPaymentDate: LocalDate? = null, override val variable: Boolean = false,
    override val enabled: Boolean = false
) :
    PolicyBasePremiumBase()



