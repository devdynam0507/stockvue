package org.vuestock.domain.stock

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
open class Stock(
    id: String,
    standardCode: String,
    stockName: String,
    isEnterpriseCompany: Boolean
) {
    @Id
    open var id: String = id

    @Column(nullable = false)
    open var standardCode: String = standardCode

    @Column(nullable = false)
    open var stockName: String = stockName

    @Column
    open var isEnterpriseCompany: Boolean = isEnterpriseCompany

    @ManyToOne
    @JoinColumn(name = "sector1Id")
    open var sector1: StockSector? = null // 업종 대분류

    @ManyToOne
    @JoinColumn(name = "sector2Id")
    open var sector2: StockSector? = null // 업종 중분류

    @ManyToOne
    @JoinColumn(name = "sector3Id")
    open var sector3: StockSector? = null // 업종 소분류
}