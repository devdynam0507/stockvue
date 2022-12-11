package org.vuestock.app.application.member.port.`in`

import org.vuestock.app.application.member.port.`in`.dto.StockPin

interface MemberGetPinsPort {

    fun getPins(memberEmail: String): List<StockPin>

    fun getPin(memberEmail: String, stockCode: String): StockPin?
}