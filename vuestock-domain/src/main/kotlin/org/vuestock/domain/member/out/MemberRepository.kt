package org.vuestock.domain.member.out

import org.springframework.data.jpa.repository.JpaRepository
import org.vuestock.domain.member.MemberEntity
import java.util.UUID

interface MemberRepository : JpaRepository<MemberEntity, UUID> {
    fun findByEmail(email: String): MemberEntity?
}