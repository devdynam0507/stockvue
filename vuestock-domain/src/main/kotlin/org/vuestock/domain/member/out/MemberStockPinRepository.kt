package org.vuestock.domain.member.out

import org.springframework.data.jpa.repository.JpaRepository
import org.vuestock.domain.member.MemberStockPin

interface MemberStockPinRepository : JpaRepository<MemberStockPin, String> {

    fun findByMember_email(email: String): List<MemberStockPin>

    fun findByMember_emailAndStock_id(email: String, stockCode: String): MemberStockPin?

    fun deleteAllByMember_email(email: String)
}