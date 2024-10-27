package vn.xdeuhug.currency_converter.domain.usecase

import vn.xdeuhug.currency_converter.domain.model.ConversionResult
import vn.xdeuhug.currency_converter.domain.repository.CurrencyRepository
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(
        amount: BigDecimal, baseCurrency: String, targetCurrency: String
    ): BigDecimal {
        val ratesResult = repository.getExchangeRates(baseCurrency, targetCurrency)

        return ratesResult.fold(onSuccess = { conversionResult ->
            val rate = conversionResult.rates[targetCurrency] ?: throw IllegalArgumentException(
                "Currency not found: $targetCurrency"
            )
            rate * amount
        }, onFailure = { throw it })
    }

    suspend fun getAllCurrencies(baseCurrency: String, targetCurrency: String): ConversionResult {
        val ratesResult = repository.getExchangeRates(baseCurrency, targetCurrency)
        return ratesResult.fold(onSuccess = { conversionResult ->
            conversionResult
        }, onFailure = {
            ConversionResult(
                baseCurrency = "",
                targetCurrency = "",
                rates = hashMapOf(),
                timeLastUpdateUtc = "",
                timeNextUpdateUtc = ""
            )
        })
    }
}