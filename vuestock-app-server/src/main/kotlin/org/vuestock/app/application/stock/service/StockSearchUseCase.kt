package org.vuestock.app.application.stock.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.vuestock.app.application.stock.exceptions.StockNotFoundException
import org.vuestock.app.application.stock.port.`in`.StockCurrentPricePort
import org.vuestock.app.application.stock.port.`in`.StockSearchPort
import org.vuestock.app.application.stock.port.`in`.dto.StockSearchResultsWithPrice
import org.vuestock.domain.stock.out.StockRepository

@Service
@Transactional(readOnly = true)
class StockSearchUseCase(
    private val stockRepository: StockRepository,
    private val stockCurrentPricePort: StockCurrentPricePort
) : StockSearchPort {

    override fun searchByStockNameWithPrices(stockName: String): List<StockSearchResultsWithPrice> {
        val stocks = stockRepository.findByStockNameContains(stockName)
        return stocks.filter { !it.id.startsWith("Q") }.map {
            val stockPrice = stockCurrentPricePort.getCurrentPrice(it.id, false)
            StockSearchResultsWithPrice(it.stockName, it.id, stockPrice)
        }
    }

    override fun searchByStockNameWithPriceOne(stockName: String): StockSearchResultsWithPrice {
        val stock = stockRepository.findByStockName(stockName) ?:
            throw StockNotFoundException("", stockName, "주식 정보를 찾을 수 없습니다.")
        val stockPrice = stockCurrentPricePort.getCurrentPrice(stock.id, false)
        return StockSearchResultsWithPrice(stock.stockName, stock.id, stockPrice)
    }
}