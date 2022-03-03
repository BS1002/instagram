package com.mahfuznow.instagram.util

data class Resource<out T>(val data: T?, val message: String?, val loading: Boolean) {
    companion object {
        fun <T> success(data: T?) = Resource(data, null, false)
        fun <T> error(message: String?): Resource<T> = Resource(null, message, false)
        fun loading() = Resource(null, null, true)
    }
}