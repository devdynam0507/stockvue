package org.vuestock.app.application.member.exceptions

open class MemberPinException(
    val stockCode: String,
    val memberEmail: String,
    override val message: String
) : Exception()