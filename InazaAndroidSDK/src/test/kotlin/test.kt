import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.example.inazaandroidsdk.models.drivers.Driver
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test


class TestTest {
    @Test
    fun testDriverModelInitWithMap(){
        // generate uuid
        val driverMap = mapOf(
            "id" to "234325325fsesfsegsg32r32g3g23",
            "firstName" to "John",
            "lastName" to "Doe",
            "email" to "john.doe@gmail.com",
            "dob" to LocalDate.parse("1990-01-01"),
            "lang" to "en",
            "drivingStartDate" to LocalDate.parse("2022-01-01"
            )
        )

        val driver =
            driverMap["lastName"]?.let {
                driverMap["firstName"]?.let { it1 ->
                    driverMap["email"]?.let { it2 ->
                        driverMap["id"]?.let { it3 ->
                            Driver(_id = it3 as String, _firstName = it1 as String, _lastName = it as String,
                                _email = it2 as String,
                                _dob = driverMap["dob"] as LocalDate?,
                                _lang = driverMap["lang"] as String?,
                                _drivingStartDate = driverMap["drivingStartDate"] as LocalDate?
                            )
                        }
                    }
                }
            }
        if (driver == null) {
            println("hi")

            assert(false)
        } else{
            assert(driver.id == driverMap["id"])
            assert(driver.firstName == driverMap["firstName"])
            assert(driver.lastName == driverMap["lastName"])
            assert(driver.email == driverMap["email"])
            assert(driver.lang == driverMap["lang"])
            assert(driver.drivingStartDate == driverMap["drivingStartDate"])
            assert(driver.dob == driverMap["dob"])
        }

    }
    @Test
    fun testDriverModelFromJson(){
        val json = """{"id": 4392-6863486346-346346,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@email.com",
        "dob": "1990-01-01",
        "lang": "en",
        "drivingStartDate": "2020-01-01"}"""


        val driver = Json.decodeFromString<Driver>(
            """{"id": "739303-950494-30393-38940","firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@email.com",
            "dateOfBirth": "1990-01-01",
            "lang": "en",
            "drivingStartDate": "2020-01-01","dateOfBirth": "2022-01-01"}"""
        )
        val str = Json.encodeToString(driver)
        println(str)
    }

    @Test
    fun testTest(){
        assert(true)
    }

    @Test
    fun wrongInput() {
        val exception: Throwable = assertThrows(
            Exception::class.java
        ) {
            throw Exception("wfwa")
        }
    }
}
