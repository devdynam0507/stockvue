package org.vuestock.domain.member

import org.hibernate.annotations.GenericGenerator
import org.vuestock.domain.common.EntityBase
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class MemberEntity (
    email: String,
    username: String,
    isEnable: Boolean,
    role: String
) : EntityBase() {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    open var id: UUID? = null

    @Column(nullable = false)
    open var email: String = email

    @Column(nullable = false)
    open var username: String = username

    @Column(nullable = false)
    open var isEnable: Boolean = isEnable

    @Column(nullable = false)
    open var role: String = role
}