package org.vuestock.app.infrastructure.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.vuestock.app.domain.member.MemberEntity
import org.vuestock.app.adapter.member.out.MemberRepository
import org.vuestock.app.application.member.port.`in`.MemberCreateUseCase

@Service
class OAuth2Service (
    private val memberRepository: MemberRepository,
    private val memberCreateUseCase: MemberCreateUseCase
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val usernameAttributeKey = userRequest!!.clientRegistration
                                                .providerDetails
                                                .userInfoEndpoint
                                                .userNameAttributeName
        val oauth2User: OAuth2User = DefaultOAuth2UserService().loadUser(userRequest)
        val attr: OAuth2Attribute = OAuth2Attribute.of(oauth2User.attributes)
        val member: MemberEntity? = memberRepository.findByEmail(attr.email)
        if (member == null) {
            memberCreateUseCase.createMember(attr.name, attr.email)
        }
        return DefaultOAuth2User(listOf(SimpleGrantedAuthority("Member")), oauth2User.attributes,
            usernameAttributeKey)
    }
}