package com.example.inazaandroidsdk.helpers

import api.ApiHandler

suspend fun checkApiHandler(api: ApiHandler?) {
    if (api == null) {
        throw Exception("API not set, can't send request")
    }
}