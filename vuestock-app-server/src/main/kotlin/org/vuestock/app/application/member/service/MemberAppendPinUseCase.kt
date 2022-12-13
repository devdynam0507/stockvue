package org.vuestock.app.application.member.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.vuestock.app.application.member.exceptions.MemberPinAppendException
import org.vuestock.app.application.member.port.`in`.MemberAppendPinPort
import org.vuestock.app.application.member.port.`in`.MemberGetPinsPort
import org.vuestock.domain.member.MemberStockPin
import org.vuestock.domain.member.out.MemberRepository
import org.vuestock.domain.member.out.MemberStockPinRepository
import org.vuestock.domain.stock.out.StockRepository

@Service
class MemberAppendPinUseCase(
    val stockRepository: StockRepository,
    val memberGetPinsPort: MemberGetPinsPort,
    val memberStockPinRepository: MemberStockPinRepository,
    val memberRepository: MemberRepository,
    @Value("\${stock.pin-append-limit}")
    val pinMaxCount: Int
): MemberAppendPinPort {

    @Transactional
    override fun appendPin(memberEmail: String, stockCode: String) {
        val stockPins = memberGetPinsPort.getPins(memberEmail)
        if (stockPins.size >= pinMaxCount) {
            throw MemberPinAppendException(stockCode, memberEmail, "이미 $pinMaxCount 만큼의 관심 주식이 등록되어있습니다.")
        }
        if (stockPins.any { it.stockCode == stockCode }) {
            throw MemberPinAppendException(stockCode, memberEmail, "이미 추가된 종목입니다.")
        }
        val member = memberRepository.findByEmail(memberEmail)
        val stock = stockRepository.findById(stockCode).get()
        memberStockPinRepository.save(MemberStockPin(member!!, stock))
    }
}