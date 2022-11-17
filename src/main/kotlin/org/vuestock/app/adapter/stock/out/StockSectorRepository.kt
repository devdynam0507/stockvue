package org.vuestock.app.adapter.stock.out

import org.springframework.data.jpa.repository.JpaRepository
import org.vuestock.app.domain.stock.StockSector

interface StockSectorRepository : JpaRepository<StockSector, String> {
}