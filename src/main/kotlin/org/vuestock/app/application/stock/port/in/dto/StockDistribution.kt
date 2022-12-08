package org.vuestock.app.application.stock.port.`in`.dto

data class StockDistribution(
    val currentPrice: StockPrice?,
    val distributions: Array<StockPrice>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockDistribution

        if (currentPrice != other.currentPrice) return false
        if (!distributions.contentEquals(other.distributions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currentPrice.hashCode()
        result = 31 * result + distributions.contentHashCode()
        return result
    }
}
