package org.vuestock.app.application.stock.type

enum class StockSign(val sign: String) {
    UpperLimit("1"), // 상한
    Increase("2"), // 상승
    Flatten("3"), // 보합
    Degradation("4"), // 하락
    LowerLimit("5"), // 하한
    None("-1"); // 예외용 타입

    companion object  {
        fun findBySign(sign: String): StockSign {
            return StockSign.values().find { it.sign == sign } ?: None
        }
    }
}