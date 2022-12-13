package org.vuestock.app.application.authentication.port.`in`

interface CheckLoggedInPort {
    fun isLoggedIn(jwt: String): Boolean
}