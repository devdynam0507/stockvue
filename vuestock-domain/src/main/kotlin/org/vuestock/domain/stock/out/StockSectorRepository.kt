package org.vuestock.domain.stock.out

import org.springframework.data.jpa.repository.JpaRepository
import org.vuestock.domain.stock.StockSector

interface StockSectorRepository : JpaRepository<StockSector, String> {
}