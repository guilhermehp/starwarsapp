package com.example.starwarsapp.domain.utils

object UrlUtils {

    fun getPageNumberFromUrl(url: String): String {
        return url.substringAfter("page=","")
    }

    fun getIdFromUrl(url: String) : String {
        return url.trimEnd('/').substringAfterLast('/')
    }

}