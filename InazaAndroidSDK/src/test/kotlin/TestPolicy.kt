import models.policy.Policy
import org.junit.jupiter.api.Test

@Test
suspend fun testPolicyLocalUpdate() {
    val pol = Policy()
    val fields: MutableMap<String, Any?> = mutableMapOf("maxPassengers" to 15, "sumInsured" to 25.02)
    val unchanged = pol.canRenew
    pol.update(false, fields)


    assert(pol.maxPassengers == fields["maxPassengers"])
    assert(pol.sumInsured == fields["sumInsured"])

    assert(pol.canRenew == unchanged)
}