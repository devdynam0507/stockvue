package org.vuestock.app.adapter.stock.out

import org.vuestock.app.adapter.stock.out.command.KisApiHeader
import java.time.LocalDateTime

interface StockDistributionExternal {

    /**
     * @param kisApiHeader KIS Api에서 정의한 필수 헤더 정보
     * @param stockCode 종목 코드
     * @param benchmarkTime 기준 시간
     * @return benchmarkTime 기준 -30분전 분봉 raw 데이터
     * */
    fun requestDistributions(kisApiHeader: KisApiHeader, stockCode: String, benchmarkTime: LocalDateTime):
            Map<*, *>
}