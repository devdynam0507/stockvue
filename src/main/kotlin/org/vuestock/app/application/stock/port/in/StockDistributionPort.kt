package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockDistribution
import java.time.LocalDateTime

interface StockDistributionPort {

    /**
     * 특정 일자의 분봉 데이터를 모두 가져온다.
     * 특정 일자의 09시 분봉 데이터부터 특정 시 까지의 분봉 데이터를 반환한다.
     * date 매개변수는 날짜, 시간값이 포함되어 들어온다.
     * */
    fun getDistribution(stockCode: String, date: LocalDateTime): StockDistribution
}