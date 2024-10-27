package vn.xdeuhug.currency_converter.domain.model

import java.math.BigDecimal

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
data class ConversionResult(
    val baseCurrency: String,
    val targetCurrency: String,
    val rates: HashMap<String, BigDecimal>,
    val timeLastUpdateUtc: String,
    val timeNextUpdateUtc: String,
)