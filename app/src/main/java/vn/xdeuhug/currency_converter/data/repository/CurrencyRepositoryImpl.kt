package vn.xdeuhug.currency_converter.data.repository

import javax.inject.Singleton
import vn.xdeuhug.currency_converter.data.remote.CurrencyApiService
import vn.xdeuhug.currency_converter.domain.model.ConversionResult
import vn.xdeuhug.currency_converter.domain.repository.CurrencyRepository
import javax.inject.Inject

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: CurrencyApiService
) : CurrencyRepository {

    override suspend fun getExchangeRates(base: String, target: String): Result<ConversionResult> {
        return try {
            val response = apiService.getExchangeRates(base)
            Result.success(
                ConversionResult(
                    base,
                    target,
                    response.conversionRates,
                    response.timeLastUpdateUtc,
                    response.timeNextUpdateUtc
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHistoryRates(
        base: String, target: String, year: String, month: String, day: String
    ): Result<ConversionResult> {
        return try {
            val response = apiService.getHistoricalRates(base, year, month, day)
            Result.success(
                ConversionResult(
                    base,
                    target,
                    response.conversionRates,
                    response.timeLastUpdateUtc,
                    response.timeNextUpdateUtc
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
