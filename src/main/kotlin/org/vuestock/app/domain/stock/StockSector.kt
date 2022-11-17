package org.vuestock.app.domain.stock

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class StockSector(
    id: String,
    sectorName: String
) {

    @Id
    open var id: String? = id // 업종 코드

    @Column(nullable = false)
    open var sectorName: String = sectorName
}