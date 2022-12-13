package org.vuestock.app.application.member.port.`in`

interface MemberCreateUseCase {

    fun createMember(name: String, email: String): Boolean
}