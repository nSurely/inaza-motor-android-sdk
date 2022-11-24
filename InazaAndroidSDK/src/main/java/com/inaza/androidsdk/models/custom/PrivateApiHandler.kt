package com.inaza.androidsdk.models.custom

import com.inaza.androidsdk.api.ApiHandler
import io.ktor.client.statement.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties


abstract class PrivateApiHandler {
    abstract val api: ApiHandler?

    @Suppress("UNCHECKED_CAST")
    protected fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
        val property = instance::class.members
            // don't cast here to <Any, R>, it would succeed silently
            .first { it.name == propertyName } as KProperty1<Any, *>
        // force an invalid cast exception if incorrect type here
        return property.get(instance) as R
    }

    protected fun Any.setPropertyValue(propName: String, value: Any?) {
        /*
        * Used by modelUpdate to update class properties dynamically
        */
        for (prop in this::class.declaredMemberProperties) {
            if (prop.name == propName) {
                (prop as? KMutableProperty<*>)?.setter?.call(this, value)
            }
        }
    }

    suspend fun modelUpdate(url: String, persist: Boolean, fields: MutableMap<String, Any?>) {
        /*
        *
        Update a field on the model, call update to persist changes in the API.
        This tracks what has changed and only updates the API if something has changed or is set.
        Args:
            persist (bool): whether to persist the changes to the API. Defaults to False.
            kwargs: the model fields to update.
        Note: when doing multiple updates, it is recommended to call update() after all updates are made.
        *
        *
        *
        * */

        if (fields == null) {
            return
        }

        for ((key, value) in fields) {
            this.setPropertyValue(key, value)
        }
        if (persist) {
            this.modelSave(url, fields)
        }
    }

    suspend fun modelSave(
        url: String,
        fields: MutableMap<String, out Any?>,
        exclude: Set<String>? = null,
        params: MutableMap<String, String?>? = null
    ): HttpResponse {
        var exc = setOf<String>("api", "id", "createdAt")
        if (this.api == null) {
            throw Exception("Api handler not set for model")
        }

        if (exclude != null) {
            exc = exc.union(exclude)
        }

        // Remove fields that are to be excluded
        for (ex in exc) {
            fields.remove(ex)
        }

        return this.api!!.makeAuthRequest("PATCH", url, params, fields, null)


    }

}
