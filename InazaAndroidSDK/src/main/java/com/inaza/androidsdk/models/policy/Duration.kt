package com.inaza.androidsdk.models.policy

import android.os.Build
import androidx.annotation.RequiresApi
import com.inaza.androidsdk.helpers.KOffsetDateTimeSerializer
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

data class Duration(
    val duration_grace_period_mins: Int,
    val duration_start: String
)

abstract class PolicyDurationBase {
    abstract val start: OffsetDateTime
    abstract val end: OffsetDateTime?
    abstract val gracePeriodMins: Int
}

@kotlinx.serialization.Serializable
class PolicyDuration(
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    override val start: OffsetDateTime,
    @kotlinx.serialization.Serializable(with = KOffsetDateTimeSerializer::class)
    override val end: OffsetDateTime? = null,
    override val gracePeriodMins: Int = 0
) : PolicyDurationBase() {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun isExpired() {
        val Zone = ZoneId.of("")
        if (this.end != null) {
            val endPlusDelta = this.end.plusMinutes(5)

            if (endPlusDelta < OffsetDateTime.now(ZoneOffset.UTC)) {

            }
        }
    }
}