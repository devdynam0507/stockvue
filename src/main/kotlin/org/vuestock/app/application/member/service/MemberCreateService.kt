package org.vuestock.app.application.member.service

import org.springframework.stereotype.Service
import org.vuestock.app.adapter.member.out.MemberRepository
import org.vuestock.app.application.member.port.`in`.MemberCreateUseCase
import org.vuestock.app.domain.member.MemberEntity
import javax.transaction.Transactional

@Service
class MemberCreateService(
    private val memberRepository: MemberRepository
) : MemberCreateUseCase {

    @Transactional
    override fun createMember(name: String, email: String): Boolean {
        val member: MemberEntity? = memberRepository.findByEmail(email)
        if (member != null) {
            return false
        }
        val newMember = MemberEntity(email, name, true, "Member")
        memberRepository.save(newMember)
        return true
    }
}