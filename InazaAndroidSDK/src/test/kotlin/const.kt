package models.policy.tests

import com.inaza.androidsdk.models.policy.PolicyOrgConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class OrgPolicyConfigTest {

    @Test
    fun policyOrgConfigCreate() {
        val json = """{
    "group": "d",
    "settings": "custom",
    "cover": [
    "comprehensive"
    ],
    "assignPolicyOnCreate": false,
    "requiresApproval": false,
    "validMins": null,
    "gracePeriodMins": 0,
    "config": {
        "display": null,
        "description": null,
        "currency": "EUR",
        "generation": {
            "maxPassengersInheritVehicle": false,
            "autoIssued": false
        },
        "terms": {
            "url": null,
            "html": null,
            "attachments": null,
            "requiresDriverEsignature": true
        }
    },
    "contribution": {
        "pc": 100.0,
        "details": null
    },
    "excess": {
        "voluntary": 0,
        "compulsory": 0
    },
    "extras": {
        "repairs": {
            "enforceApprovedSuppliers": false,
            "courtesyVehicle": false
        },
        "alarm": {
            "enforce": false
        },
        "breakdown": {
            "cover": false,
            "coverLimit": 0,
            "cost": 0
        },
        "rescue": {
            "cover": false,
            "coverLimit": 0,
            "cost": 0
        },
        "theft": {
            "cover": false,
            "coverLimit": 0,
            "cost": 0
        },
        "keyReplacement": {
            "cover": false,
            "coverLimit": 0,
            "cost": 0
        },
        "windscreen": {
            "cover": false,
            "cost": 0
        }
    },
    "fees": {
        "cancellation": 0,
        "renewal": 0,
        "newBusiness": 0
    },
    "premium": {
        "value": 0,
        "payableImmediate": true,
        "frequency": "d",
        "useFrequency": false,
        "nextPaymentDate": null,
        "variable": false
    },
    "rates": {
        "enabled": false,
        "value": 0.0,
        "max": 0.0,
        "min": 0.0,
        "chargeableDistanceKm": 500,
        "frequency": "bml",
        "variable": false
    },
    "rewards": {
        "enabled": false,
        "maxMonthly": 0,
        "rates": {
            "enabled": false,
            "maxDiscountPc": 20.0
        },
        "premium": {
            "enabled": false,
            "maxDiscountPc": 20.0
        }
    },
    "telematics": {
        "process": "indefinite",
        "days": null,
        "minKm": null
    }
}"""

        val policy = Json.decodeFromString<PolicyOrgConfig>(json)

    }
}

// payment frequency from api returned null but is enum