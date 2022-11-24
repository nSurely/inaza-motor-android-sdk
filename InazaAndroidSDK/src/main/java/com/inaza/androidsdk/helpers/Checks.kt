package com.inaza.androidsdk.helpers

import com.inaza.androidsdk.api.ApiHandler

suspend fun checkApiHandler(api: ApiHandler?) {
    if (api == null) {
        throw Exception("API not set, can't send request")
    }
}