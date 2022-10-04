package search

import helpers.enumContains

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


/**
 * Ops
 *
 * @constructor Create empty Ops
 */
enum class Ops {
    /**
     * Eq
     *
     * @constructor Create empty Eq
     */
    eq,

    /**
     * Ne
     *
     * @constructor Create empty Ne
     */
    ne,

    /**
     * Gt
     *
     * @constructor Create empty Gt
     */
    gt,

    /**
     * Gte
     *
     * @constructor Create empty Gte
     */
    gte,

    /**
     * Lt
     *
     * @constructor Create empty Lt
     */
    lt,

    /**
     * Lte
     *
     * @constructor Create empty Lte
     */
    lte,

    /**
     * Like
     *
     * @constructor Create empty Like
     */
    like,

    /**
     * Ilike
     *
     * @constructor Create empty Ilike
     */
    ilike
}


/**
 * Advanced Search
 *
 * @property value
 * @property operator
 * @constructor Create empty Search
 */
class Search(val operator: String = "eq", val value: Any) {
    init {
        if (!(enumContains<Ops>(operator))) {
            throw Exception(
                "Invalid operator:  \"%s\" - can only be one of %s".format(
                    operator,
                    Ops.values().contentToString()
                )
            )
        }
    }

    /**
     * Checks the type and returns the value as a string. Datetime is converted to ISO format.
     *
     * @return
     */
    private fun getValueStr(): String {
        when (this.value) {
            is java.time.LocalDateTime -> {
                return this.value.format(DateTimeFormatter.ISO_DATE_TIME)
            }
            is java.time.LocalDate -> {
                return this.value.format(DateTimeFormatter.ISO_DATE)
            }
            is java.time.LocalTime -> {
                return this.value.format(DateTimeFormatter.ISO_TIME)
            }
            is kotlinx.datetime.LocalDateTime -> {
                return this.value.toJavaLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME)
            }
            // return 2021-07-27T16:02:08.070557 if datetime
            is kotlinx.datetime.LocalDate -> {
                return this.value.toJavaLocalDate().format(DateTimeFormatter.ISO_DATE)
            }
            is OffsetDateTime -> {
                return this.value.toString()
            }
            // raise exception if String cast is null
            else -> return value as String
        }
    }

    /**
     * Returns a list of available operators for the given value type.

     *
     * @return
     */
    suspend fun availableOperators(): Array<Ops> {
        return Ops.values()
    }

    // __repr__ vs __str__
    override fun toString(): String {
        return "${this.operator}.${this.getValueStr()}"
    }
}