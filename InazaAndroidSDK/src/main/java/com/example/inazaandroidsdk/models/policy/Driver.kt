package models.policy

import helpers.KOffsetDateTimeSerializer
import kotlinx.serialization.json.JsonObject
import java.time.OffsetDateTime

abstract class PolicyDriverBase {
    abstract val esignature: String?
    abstract val esignatureFingerprint: JsonObject?
    abstract val agreedAt: OffsetDateTime?
}

@kotlinx.serialization.Serializable
class PolicyDriver(
    override val esignature: String? = null,
    override val esignatureFingerprint: JsonObject? = null,
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    override val agreedAt: OffsetDateTime? = null
) : PolicyDriverBase() {
    suspend fun isAgreed(): Boolean {
        if (this.agreedAt != null) {
            return true
        }
        return false
    }
}

