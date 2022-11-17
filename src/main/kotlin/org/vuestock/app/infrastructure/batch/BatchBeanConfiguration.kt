package org.vuestock.app.infrastructure.batch

import org.springframework.batch.extensions.excel.RowMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.vuestock.app.domain.stock.StockSector
import org.vuestock.app.infrastructure.batch.dto.StockBatchDto

@Configuration
class BatchBeanConfiguration {

    @Bean
    fun stockRowMapper(): RowMapper<StockBatchDto> {
        return StockRowMapper()
    }

    @Bean
    fun stockSectorRowMapper(): RowMapper<StockSector> {
        return StockSectorRowMapper()
    }
}