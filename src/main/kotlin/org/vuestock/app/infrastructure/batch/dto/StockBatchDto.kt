package org.vuestock.app.infrastructure.batch.dto

data class StockBatchDto(
    val id: String,
    val standardCode: String,
    val stockName: String,
    val isEnterpriseCompany: Boolean,
    val sectorCode1: String, // 업종 대분류
    val sectorCode2: String, // 업종 중분류
    val sectorCode3: String // 업중 소분류
)