package org.vuestock.app.application.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.vuestock.app.application.member.port.`in`.MemberGetPinsPort
import org.vuestock.app.application.member.port.`in`.dto.StockPin
import org.vuestock.domain.member.out.MemberStockPinRepository

@Service
@Transactional(readOnly = true)
class MemberGetPinsUseCase(
    val memberStockPinRepository: MemberStockPinRepository
) : MemberGetPinsPort {

    override fun getPins(memberEmail: String): List<StockPin> {
        return memberStockPinRepository.findByMember_email(memberEmail)
                                       .map { StockPin(it.stock.id, it.stock.stockName) }
    }

    override fun getPin(memberEmail: String, stockCode: String): StockPin? {
        val stockPin = memberStockPinRepository.findByMember_emailAndStock_id(memberEmail, stockCode)
        return if (stockPin == null) {
            null
        } else {
            StockPin(stockCode, stockPin.stock.stockName)
        }
    }
}