package org.vuestock.domain.member

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.vuestock.domain.stock.Stock
import java.util.*
import javax.persistence.*

@Entity
open class MemberStockPin(
    member: MemberEntity,
    stock: Stock
) {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    open var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "member_email")
    open var member: MemberEntity = member

    @ManyToOne
    @JoinColumn(name = "stock_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    open var stock: Stock = stock
}