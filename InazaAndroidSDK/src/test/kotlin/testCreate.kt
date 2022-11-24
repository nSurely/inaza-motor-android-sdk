package models.policy.tests

import com.inaza.androidsdk.models.policy.Policy
import com.inaza.androidsdk.models.policy.enums.PolicyCoverType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class TestCreate {
    @Test
    fun testCreateBasic() {
        val basic = mapOf<String, Any>(
            "id" to "DRV-123",
            "createdAt" to "2020-01-01T00:00:00.000Z",
            "isActivePolicy" to true,
            "sumInsured" to 100.00,
            "canRenew" to true,
            "cover" to listOf<PolicyCoverType>(PolicyCoverType.comprehensive),
            "maxPassengers" to 1
        )
        val p = Policy(
            _id = basic["id"] as String?,
//            createdAt = LocalDateTime.parse(basic["createdAt"] as String),
            createdAt = OffsetDateTime.parse(basic["createdAt"] as String),
            _isActivePolicy = basic["isActivePolicy"] as Boolean,
            _sumInsured = basic["sumInsured"] as Double,
            _canRenew = basic["canRenew"] as Boolean,
            _cover = basic["cover"] as List<PolicyCoverType>,
            _maxPassengers = basic["maxPassengers"] as Int
        )


        assert(p.isActivePolicy == true)
        assert(p.sumInsured == 100.00)
        assert(p.canRenew == true)
        assert(p.cover[0].toString() == "comprehensive")
        assert(p.maxPassengers == 1)


    }

//    @Test
//    fun testIsLive() {
//        val json = """{
//	"id": "DRV-123",
//	"createdAt": "2020-01-01T00:00:00.000Z",
//	"isActivePolicy": true,
//	"sumInsured": 100.00,
//	"canRenew": true,
//	"maxPassengers": 1,
//	"cover": [
//		"comprehensive"
//	],
//	"approval": {
//		"approvedAt": "2022-02-02T00:00:00.000Z",
//		"autoApproved": true,
//		"approvedBy": "fd8a039f-2b7b-4537-b5f6-fe90017f4cd4"
//	},
//	"cancellation": {
//		"cancelledAt": "2022-02-04T02:23:24Z",
//		"message": "test"
//	},
//	"config": {
//		"display": "test",
//		"description": "test",
//		"currency": "EUR",
//		"generation": {
//			"maxPassengersInheritVehicle": true,
//			"autoIssued": true
//		},
//		"terms": {
//			"url": "https://www.google.com",
//			"html": "<h1>test</h1>",
//
//			"requiresDriverEsignature": true
//		},
//		"contribution": {
//			"pc": 100.00,
//			"details": {
//				"test": "test"
//			}
//		},
//		"driver": {
//			"esignature": "path",
//			"esignatureFingerprint": {
//				"test": "test"
//			},
//			"agreedAt": "2022 - 02 - 04 T02: 23: 24 Z"
//		},
//		"duration": {
//			"start": "2022 - 02 - 04 T02: 23: 24 Z",
//			"end": "2022 - 02 - 04 T02: 23: 24 Z",
//			"gracePeriodMins": 1
//		},
//		"excess": {
//			"voluntary": 100,
//			"compulsory": 100
//		},
//		"extras": {
//			"repairs": {
//				"enforceApprovedSuppliers": true,
//				"courtesyVehicle": true
//			},
//			"alarm": {
//				"enforce": true
//			},
//			"breakdown": {
//				"cover": true,
//				"coverLimit": 1,
//				"cost": 100
//			},
//			"rescue": {
//				"cover": true,
//				"coverLimit": 1,
//				"cost": 100
//			},
//			"theft": {
//				"cover": true,
//				"coverLimit": 1,
//				"cost": 100
//			},
//			"keyReplacement": {
//				"cover": true,
//				"coverLimit": 1,
//				"cost": 100
//			},
//			"windscreen": {
//				"cover": true,
//				"cost": 100
//			}
//		},
//		"fees": {
//			"cancellation": 0,
//			"renewal": 0,
//			"newBusiness": 0
//		},
//		"final": {
//			"rates": {
//				"value": 1,
//				"max": 3,
//				"min": 0,
//				"appliedRiskMultiplier": 1.5
//			},
//			"premium": {
//				"value": 100,
//				"appliedRiskMultiplier": 1.5
//			}
//		},
//		"issuer": {
//			"id": "egwgwegewgwegwe",
//			"policyAgreedAt": "2022 - 02 - 04 T02: 23: 24 Z"
//		},
//		"noClaims": {
//			"forgiveness": false,
//			"discountPc": 10.0
//		},
//		"premium": {
//			"enabled": true,
//			"value": 100,
//			"payableImmediate": true,
//			"frequency": "bmf",
//			"useFrequency": false,
//			"nextPaymentDate": null,
//			"variable": false
//		},
//		"rates": {
//			"enabled": false,
//			"active": true,
//			"value": 0.5,
//			"max": 1.0,
//			"min": 0.0,
//			"chargeableDistanceKm": 100,
//			"frequency": "bmf",
//			"variable": false
//		},
//		"rewards": {
//			"enabled": true,
//			"maxMonthly": 100,
//			"rates": {
//				"enabled": true,
//				"maxDiscountPc": 20.0
//			},
//			"premium": {
//				"enabled": true,
//				"maxDiscountPc": 20.0
//			}
//		},
//		"telematics": {
//			"process": "indefinite",
//			"days": null,
//			"minKm": null
//		}
//	}
//}"""
////        "attachments": [
////        "https://www.google.com"
////        ],
//
//        val pol = Json.decodeFromString<Policy>(json)
//
//    }

    @Test
    fun testApiResponse() {
        //2022-08-03T08:30:17.197767+00:00 doesn't work
        val json = """{
    "isActivePolicy": true,
    "sumInsured": 1,
    "canRenew": true,
    "cover": [
        "comprehensive"
    ],
    "maxPassengers": 2,
    "id": "D-HJKVM71659515417",
    "createdAt": "2022-08-03T08:30:17.197767+00:00",
    "policyCertificateLoc": null,
    "approval": {
        "approvedAt": "2022-08-03T08:30:17.188612+00:00",
        "autoApproved": true,
        "approvedBy": null
    },
    "cancellation": {
        "cancelledAt": null,
        "message": null
    },
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
            "requiresDriverEsignature": false
        }
    },
    "contribution": {
        "pc": 100.0,
        "details": null
    },
    "driver": {
        "esignature": null,
        "esignatureFingerprint": null,
        "agreedAt": null
    },
    "duration": {
        "start": "2022-08-03T08:30:17.188593+00:00",
        "end": null,
        "gracePeriodMins": 0
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
    "final": {
        "requiresReprice": false,
        "rates": {
            "value": 0.0,
            "max": 0.0,
            "min": 0.0,
            "appliedRiskMultiplier": null
        },
        "premium": {
            "value": 0,
            "appliedRiskMultiplier": null
        }
    },
    "issuer": {
        "id": null,
        "policyAgreedAt": null
    },
    "noClaims": {
        "forgiveness": false,
        "discountPc": 0.0
    },
    "premium": {
        "value": 0,
        "payableImmediate": true,
        "frequency": null,
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
        // frequency should be 'bml
        val pol = Json.decodeFromString<Policy>(json)

    }

    @Test
    fun testDriverPolicy() {
        val json = """{
    "isActivePolicy": true,
    "sumInsured": 1,
    "canRenew": true,
    "cover": [
        "comprehensive"
    ],
    "maxPassengers": 2,
    "id": "D-HJKVM71659515417",
    "createdAt": "2022-08-03T08:30:17.197767Z",
    "policyCertificateLoc": null,
    "approval": {
        "approvedAt": "2022-08-03T08:30:17.188612Z",
        "autoApproved": true,
        "approvedBy": null
    },
    "cancellation": {
        "cancelledAt": null,
        "message": null
    },
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
            "requiresDriverEsignature": false
        }
    },
    "contribution": {
        "pc": 100.0,
        "details": null
    },
    "driver": {
        "esignature": null,
        "esignatureFingerprint": null,
        "agreedAt": null
    },
    "duration": {
        "start": "2022-08-03T08:30:17.188593Z",
        "end": null,
        "gracePeriodMins": 0
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
    "final": {
        "requiresReprice": false,
        "rates": {
            "value": 0.0,
            "max": 0.0,
            "min": 0.0,
            "appliedRiskMultiplier": null
        },
        "premium": {
            "value": 0,
            "appliedRiskMultiplier": null
        }
    },
    "issuer": {
        "id": null,
        "policyAgreedAt": null
    },
    "noClaims": {
        "forgiveness": false,
        "discountPc": 0.0
    },
    "premium": {
        "value": 0,
        "payableImmediate": true,
        "frequency": null,
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
}
"""

        val pol = Json.decodeFromString<Policy>(json)

    }

    @Test
    fun testFullPolicy() {
        val json = """{
    "id": "DRV-123",
    "createdAt": "2022-08-03T08:30:17.197767+00:00",
    "isActivePolicy": True,
    "sumInsured": 100.00,
    "canRenew": True,
    "maxPassengers": 1,
    "cover": [
        "comprehensive"
    ],
    "approval": {
        "approvedAt":"2022-08-03T08:30:17.188593+00:00",
        "autoApproved": True,
        "approvedBy": "fd8a039f-2b7b-4537-b5f6-fe90017f4cd4"
    },
    "cancellation": {
        "cancelledAt": "2022-08-03T08:30:17.188593+00:00",
        "message": "test"
    },
    "config": {
        "display": "test",
        "description": "test",
        "currency": "EUR",
        "generation": {
            "maxPassengersInheritVehicle": True,
            "autoIssued": True
        },
        "terms": {
            "url": "https://www.google.com",
            "html": "<h1>test</h1>",
                    "attachments": [
                        "https://www.google.com"
                    ],
            "requiresDriverEsignature": True
        },
        "geofence": {
            "enabled": True,
            "polygons": [
                {
                    "type": "Polygon",
                    "coordinates": [
                            [
                                -122.4,
                                37.8
                            ],
                        [
                                -122.4,
                                37.9
                            ],
                        [
                                -122.3,
                                37.9
                            ],
                        [
                                -122.4,
                                37.8
                            ]
                    ]
                }
            ]
        }
    },
    "contribution": {
        "pc": 100.00,
        "details": {
            "test": "test"
        }
    },
    "driver": {
        "esignature": "path",
        "esignatureFingerprint": {
            "test": "test"
        },
        "agreedAt": "2022-08-03T08:30:17.188593+00:00"
    },
    "duration": {
        "start": "2022-08-03T08:30:17.188593+00:00",
        "end": null,
        "gracePeriodMins": 1
    },
    "excess": {
        "voluntary": 100,
        "compulsory": 100
    },
    "extras": {
        "repairs": {
            "enforceApprovedSuppliers": True,
            "courtesyVehicle": True
        },
        "alarm": {
            "enforce": True
        },
        "breakdown": {
            "cover": True,
            "coverLimit": 1,
            "cost": 100
        },
        "rescue": {
            "cover": True,
            "coverLimit": 1,
            "cost": 100
        },
        "theft": {
            "cover": True,
            "coverLimit": 1,
            "cost": 100
        },
        "keyReplacement": {
            "cover": True,
            "coverLimit": 1,
            "cost": 100
        },
        "windscreen": {
            "cover": True,
            "cost": 100
        }
    },
    "fees": {
        "cancellation": 0,
        "renewal": 0,
        "newBusiness": 0
    },
    "final": {
        "rates": {
            "value": 1,
            "max": 3,
            "min": 0,
            "appliedRiskMultiplier": 1.5
        },
        "premium": {
            "value": 100,
            "appliedRiskMultiplier": 1.5
        }
    },
    "issuer": {
        "id": "fd8a039f-2b7b-4537-b5f6-fe90017f4cd4",
        "policyAgreedAt": "2022-08-03T08:30:17.188593+00:00"
    },
    "noClaims": {
        "forgiveness": False,
        "discountPc": 10.0
    },
    "premium": {
        "enabled": True,
        "value": 100,
        "payableImmediate": True,
        "frequency": "bmf",
        "useFrequency": False,
        "nextPaymentDate": null,
        "variable": False
    },
    "rates": {
        "enabled": True,
        "value": 0.5,
        "max": 1.0,
        "min": 0.0,
        "chargeableDistanceKm": 100,
        "frequency": "bmf",
        "variable": False
    },
    "rewards": {
        "enabled": True,
        "maxMonthly": 100,
        "rates": {
            "enabled": True,
            "maxDiscountPc": 20.0
        },
        "premium": {
            "enabled": True,
            "maxDiscountPc": 20.0
        }
    },
    "telematics": {
        "process": "indefinite",
        "days": null,
        "minKm": null
    }
}"""
        val p = Json.decodeFromString<Policy>(json)
        assert(p.isActivePolicy == true)
        assert(p.sumInsured == 100.00)
        assert(p.canRenew == true)
        assert(p.cover[0].toString() == "comprehensive")
        assert(p.maxPassengers == 1)

        assert(p.rates.enabled == true)
        assert(p.telematics.days == null)
    }
}

