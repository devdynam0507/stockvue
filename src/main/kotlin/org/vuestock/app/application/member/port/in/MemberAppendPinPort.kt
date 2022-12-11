package org.vuestock.app.application.member.port.`in`

interface MemberAppendPinPort {

    fun appendPin(memberEmail: String, stockCode: String)
}