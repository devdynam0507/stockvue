package org.vuestock.app.application.member.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.vuestock.app.application.member.exceptions.MemberStockPinNotFoundException
import org.vuestock.app.application.member.port.`in`.MemberDeletePinPort
import org.vuestock.domain.member.out.MemberStockPinRepository

@Service
@Transactional
class MemberDeletePinUseCase(
    val memberStockPinRepository: MemberStockPinRepository,
): MemberDeletePinPort {

    override fun deleteAll(memberEmail: String) {
        memberStockPinRepository.deleteAllByMember_email(memberEmail)
    }

    override fun deleteByStockCode(memberEmail: String, stockCode: String) {
        val stockPin = memberStockPinRepository.findByMember_emailAndStock_id(memberEmail, stockCode)
            ?: throw MemberStockPinNotFoundException(stockCode, memberEmail, "등록된 핀이 없습니다.")
        memberStockPinRepository.delete(stockPin)
    }
}