package org.vuestock.app.adapter.member.out

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.vuestock.app.domain.member.MemberEntity
import java.util.UUID

@Repository
interface MemberRepository : JpaRepository<MemberEntity, UUID> {
    fun findByEmail(email: String): MemberEntity?
}