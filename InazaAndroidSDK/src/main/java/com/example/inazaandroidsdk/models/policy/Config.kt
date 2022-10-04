package models.policy

import models.policy.enums.ISOCurrencyCode


abstract class PolicyConfigStandardBase {
    abstract val display: String?
    abstract val description: String?
    abstract val currency: ISOCurrencyCode
}


abstract class PolicyConfigStandard : PolicyConfigStandardBase()

@kotlinx.serialization.Serializable
class PolicyConfig(
    override val display: String? = null,
    override val description: String? = null,
    override val currency: ISOCurrencyCode = ISOCurrencyCode.EUR
) : PolicyConfigStandard() {
    val generation: PolicyGeneration = PolicyGeneration()
    val geofence: PolicyGeofenceRead = PolicyGeofenceRead()
    val terms: PolicyTerms = PolicyTerms()

}

abstract class PolicyGenerationBase {
    abstract val maxPassengersInheritVehicle: Boolean
    abstract val autoIssued: Boolean
}

@kotlinx.serialization.Serializable
class PolicyGeneration(
    override val maxPassengersInheritVehicle: Boolean = false,
    override val autoIssued: Boolean = false
) : PolicyGenerationBase()


// TODO Complete this
enum class GeoType {
    Polygon
}

@kotlinx.serialization.Serializable
// Doesn't serialize with Pair<Float,Float> instead of Double
class GeofencePolygon(val type: GeoType, val coordinates: List<List<Double>>) {
    init {
        validateCoordinates(coordinates)
    }

    // TODO check should be List<List<Pair<Float,Float>>>
    private fun validateCoordinates(coords: List<List<Double>>) {
        if (coords.size < 3) {
            throw Exception("Polygon coordinates must have at least 3 points, got %d".format(coords.size))
        }

        if (coords[0] != coords[coords.lastIndex]) {
            throw Exception(
                "Polygon coordinates must have the same first and last points, got %d and %d".format(
                    coords[0],
                    coords[coords.lastIndex]
                )
            )
        }
    }
}
// validate coordinates


abstract class PolicyGeofenceBase {
    abstract val enabled: Boolean
    abstract val polygons: List<GeofencePolygon>?
}

class PolicyGeofenceCreate(enabled: Boolean = false, polygons: MutableMap<String, Any>?)

class PolicyGeofenceUpdate(enabled: Boolean = false, polygons: MutableMap<String, Any>?)

@kotlinx.serialization.Serializable
class PolicyGeofenceRead(
    override val enabled: Boolean = false,
    override val polygons: List<GeofencePolygon>? = null
) : PolicyGeofenceBase()

abstract class PolicyTermsBase {
    abstract val url: String?
    abstract val html: String?
    abstract val attachments: List<String>?
    abstract val requiresDriverEsignature: Boolean?
}

@kotlinx.serialization.Serializable
class PolicyTerms(
    override val url: String? = null,
    override val html: String? = null,
    override val attachments: List<String>? = null,
    override val requiresDriverEsignature: Boolean? = null
) : PolicyTermsBase()