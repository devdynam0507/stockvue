package org.vuestock.app.application.member.port.`in`

interface MemberDeletePinPort {

    fun deleteAll(memberEmail: String)

    fun deleteByStockCode(memberEmail: String, stockCode: String)
}