import com.inaza.androidsdk.models.vehicles.DriverVehicle
import com.inaza.androidsdk.models.vehicles.Vehicle
import com.inaza.androidsdk.models.vehicles.VehicleType
import org.junit.jupiter.api.Test

@Test
suspend fun testDriverVehicleLocalUpdate() {
    val drv = DriverVehicle()
    val fields: MutableMap<String, Any?> =
        mutableMapOf("isOwner" to true, "displayName" to "Unit Test")
    val unchanged = drv.expiresAt
    drv.update(false, fields)


    assert(drv.isOwner == fields["isOwner"])
    assert(drv.displayName == fields["displayName"])

    assert(drv.expiresAt == unchanged)
}

@Test
suspend fun testRegisteredVehicleLocalUpdate() {
    val rv = Vehicle()
    val fields: MutableMap<String, Any?> = mutableMapOf("isApproved" to true, "engineLitres" to 3)
    val unchanged = rv.vin
    rv.update(false, fields)


    assert(rv.isApproved == fields["isApproved"])
    assert(rv.engineLitres == fields["engineLitres"])

    assert(rv.vin == unchanged)
}

@Test
suspend fun testVehicleLocalUpdate() {
    val v = VehicleType()
    val fields: MutableMap<String, Any?> = mutableMapOf("isActive" to true, "brand" to "Toyota")
    val unchanged = v.fuelTankCapacityML
    v.update(false, fields)


    assert(v.isActive == fields["isActive"])
    assert(v.brand == fields["brand"])

    assert(v.fuelTankCapacityML == unchanged)
}