package com.inaza.androidsdk.models.risk

import kotlinx.serialization.Serializable

/**
 * Risk model
 *
 * @property lookback
 * @property dynamic
 * @property ihr
 * @constructor Create empty Risk
 */
@Serializable
class Risk(
    val lookback: RiskRetro = RiskRetro(),
    val dynamic: DynamicRisk = DynamicRisk(),
    val ihr: RiskRetro = RiskRetro()
)

/**
 * Common risk
 *
 * @constructor Create empty Common risk
 */
@Serializable
abstract class CommonRisk {
    abstract val risk: Risk?
}


/**
 * Risk application model
 *
 * @property inheritance
 * @property apply
 * @constructor Create empty Risk application
 */
@Serializable
class RiskApplication(val inheritance: Boolean = false, val apply: Boolean = false)

/**
 * Risk retro
 *
 * @property rates
 * @property value
 * @property weighting
 * @property premium
 * @constructor Create empty Risk retro
 */
@Serializable
class RiskRetro(
    val rates: RiskApplication = RiskApplication(),
    val value: Float = 0.0f,
    val weighting: Float = 0.0f,
    val premium: RiskApplication = RiskApplication()
)

/**
 * Dynamic risk model
 *
 * @property apply
 * @property process
 * @property weighting
 * @constructor Create empty Dynamic risk
 */
@Serializable
class DynamicRisk(
    val apply: Boolean = false,
    val process: String = "std",
    val weighting: Float = 0.0f
)