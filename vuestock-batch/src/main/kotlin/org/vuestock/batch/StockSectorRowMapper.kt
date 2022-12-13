package org.vuestock.batch

import org.springframework.batch.extensions.excel.RowMapper
import org.springframework.batch.extensions.excel.support.rowset.RowSet
import org.vuestock.app.domain.stock.StockSector

class StockSectorRowMapper : RowMapper<StockSector> {

    override fun mapRow(rs: RowSet?): StockSector {
        val id: String = rs!!.currentRow[0]
        val sectorName: String = rs.currentRow[1]
        return StockSector(id, sectorName)
    }
}