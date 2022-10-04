package models.policy

@kotlinx.serialization.Serializable
class PolicyExtrasNested(
    val alarm: PolicyExtrasAlarm = PolicyExtrasAlarm(),
    val breakdown: PolicyExtrasBreakdown = PolicyExtrasBreakdown(),
    val keyReplacement: PolicyExtrasKeyRecovery = PolicyExtrasKeyRecovery(),
    val repairs: PolicyExtrasRepairs = PolicyExtrasRepairs(),
    val rescue: PolicyExtrasRescue = PolicyExtrasRescue(),
    val theft: PolicyExtrasTheft = PolicyExtrasTheft(),
    val windscreen: PolicyExtrasWindscreen = PolicyExtrasWindscreen()
)


abstract class PolicyExtrasRepairsBase {
    abstract val enforceApprovedSuppliers: Boolean
    abstract val courtesyVehicle: Boolean
}

@kotlinx.serialization.Serializable
class PolicyExtrasRepairs(
    override val enforceApprovedSuppliers: Boolean = false,
    override val courtesyVehicle: Boolean = false
) : PolicyExtrasRepairsBase()

abstract class PolicyExtrasAlarmBase {
    abstract val enforce: Boolean
}

@kotlinx.serialization.Serializable
class PolicyExtrasAlarm(override val enforce: Boolean = false) : PolicyExtrasAlarmBase()

// constrain int
abstract class PolicyExtrasBreakdownBase {
    abstract val cover: Boolean
    abstract val coverLimit: Int
    abstract val cost: Int
}

@kotlinx.serialization.Serializable
class PolicyExtrasBreakdown(
    override val cover: Boolean = false,
    override val coverLimit: Int = 0,
    override val cost: Int = 0
) : PolicyExtrasBreakdownBase()

abstract class PolicyExtrasRescueBase {
    abstract val cover: Boolean
    abstract val coverLimit: Int
    abstract val cost: Int
}

@kotlinx.serialization.Serializable
class PolicyExtrasRescue(
    override val cover: Boolean = false,
    override val coverLimit: Int = 0,
    override val cost: Int = 0
) : PolicyExtrasRescueBase()


abstract class PolicyExtrasTheftBase {
    abstract val cover: Boolean
    abstract val coverLimit: Int
    abstract val cost: Int
}

@kotlinx.serialization.Serializable
class PolicyExtrasTheft(
    override val cover: Boolean = false,
    override val coverLimit: Int = 0,
    override val cost: Int = 0
) : PolicyExtrasTheftBase()


abstract class PolicyExtrasKeyRecoveryBase {
    abstract val cover: Boolean
    abstract val coverLimit: Int
    abstract val cost: Int
}

@kotlinx.serialization.Serializable
class PolicyExtrasKeyRecovery(
    override val cover: Boolean = false,
    override val coverLimit: Int = 0,
    override val cost: Int = 0
) : PolicyExtrasKeyRecoveryBase()


// Why no cover limit for windscreen policy?
abstract class PolicyExtrasWindscreenBase {
    abstract val cover: Boolean
    abstract val cost: Int
}

@kotlinx.serialization.Serializable
class PolicyExtrasWindscreen(override val cover: Boolean = false, override val cost: Int = 0) :
    PolicyExtrasWindscreenBase()