package vn.xdeuhug.currency_converter.domain.model

import java.math.BigDecimal

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
data class Currency(
    val code: String, val name: String, val country: String, var rate: BigDecimal
) {
    var isSelected: Boolean = false
    var countryCode: String = ""

    constructor(
        code: String,
        name: String,
        country: String,
        rate: BigDecimal,
        countryCode: String
    ) : this(code, name, country, rate) {
        this.countryCode = countryCode
    }
}