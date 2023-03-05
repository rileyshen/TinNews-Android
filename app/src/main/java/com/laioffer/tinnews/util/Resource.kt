package com.laioffer.tinnews.util

import androidx.annotation.StringRes

sealed class Resource<T> (
    val data: T? = null,
    val message: String? = null,
val resId : Int? = null
 ) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T?= null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()

    class StringResource<T : Any>(@StringRes resId: Int) : Resource<T>(resId = resId)
}