package com.inaza.androidsdk.models.policy

import com.inaza.androidsdk.helpers.KOffsetDateTimeSerializer
import com.inaza.androidsdk.helpers.UUIDSerializer
import java.time.OffsetDateTime
import java.util.*

abstract class PolicyApprovalBase {
    abstract val approvedAt: OffsetDateTime?
    abstract val autoApproved: Boolean
    abstract val approvedBy: UUID?
}

@kotlinx.serialization.Serializable
class PolicyApproval(
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    override val approvedAt: OffsetDateTime? = null,
    override val autoApproved: Boolean = false,
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    override val approvedBy: UUID? = null
) : PolicyApprovalBase() {

    suspend fun isApproved(): Boolean {
        if (this.approvedAt != null || this.autoApproved) {
            return true
        }
        return false
    }
}