package com.inaza.androidsdk.models.policy

import com.inaza.androidsdk.helpers.KOffsetDateTimeSerializer
import java.time.OffsetDateTime

abstract class PolicyCancellationBase {
    abstract var cancelledAt: OffsetDateTime?
    abstract var message: String?
}

@kotlinx.serialization.Serializable
class PolicyCancellation(
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    override var cancelledAt: OffsetDateTime? = null,
    override var message: String? = null
) : PolicyCancellationBase() {
    suspend fun isCancelled(): Boolean {
        if (this.cancelledAt != null) {
            return true
        }
        return false
    }
}