package vn.xdeuhug.currency_converter.domain.repository

import vn.xdeuhug.currency_converter.domain.model.ConversionResult

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
interface CurrencyRepository {
    suspend fun getExchangeRates(base: String, target: String): Result<ConversionResult>

    suspend fun getHistoryRates(
        base: String,
        target: String,
        year: String,
        month: String,
        day: String
    ): Result<ConversionResult>
}