package org.vuestock.common

data class CommonResponse<T>(
    val message: String,
    val code: String,
    val data: T
)
