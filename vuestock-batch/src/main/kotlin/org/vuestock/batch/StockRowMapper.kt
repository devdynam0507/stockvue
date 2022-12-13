package org.vuestock.batch

import org.springframework.batch.extensions.excel.RowMapper
import org.springframework.batch.extensions.excel.support.rowset.RowSet
import org.vuestock.app.infrastructure.batch.dto.StockBatchDto

class StockRowMapper : RowMapper<StockBatchDto> {

    private fun makeSectorCode(code: String): String {
        if (code.length >= 4 || code == "0") {
            return code
        }
        val sb: StringBuilder = java.lang.StringBuilder()
        for(i: Int in 0 until 4 - code.length) {
            sb.append("0")
        }
        return sb.toString() + code
    }

    override fun mapRow(rs: RowSet?): StockBatchDto {
        val id: String = rs!!.currentRow[0]
        val standardCode: String = rs.currentRow[1]
        val stockName: String = rs.currentRow[2]
        val sectorCode1: String = makeSectorCode(rs.currentRow[5])
        val sectorCode2: String = makeSectorCode(rs.currentRow[6])
        val sectorCode3: String = makeSectorCode(rs.currentRow[7])
        val isEnterpriseCompany: Boolean = rs.currentRow[8] != "N"
        return StockBatchDto(id,
                             standardCode,
                             stockName,
                             isEnterpriseCompany,
                             sectorCode1,
                             sectorCode2,
                             sectorCode3)
    }
}