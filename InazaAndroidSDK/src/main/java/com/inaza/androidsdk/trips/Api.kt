package com.inaza.androidsdk.trips

import com.inaza.androidsdk.api.ApiHandler
import com.inaza.androidsdk.auth.Auth


//// Where does it get the org_id / region / url from?
class TripApi(auth: Auth) : ApiHandler("Change me", auth, auth.region, url = null) {
    suspend fun sendTripData() {
//        this.telematicsRequest() // basically just this right?
        val a = auth.orgId
    }
}
