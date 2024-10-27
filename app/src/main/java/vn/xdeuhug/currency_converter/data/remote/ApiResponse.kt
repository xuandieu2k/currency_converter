package vn.xdeuhug.currency_converter.data.remote


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ApiResponse(
    @SerializedName("base_code")
    var baseCode: String = "",
    @SerializedName("documentation")
    var documentation: String = "",
    @SerializedName("result")
    var result: String = "",
    @SerializedName("terms_of_use")
    var termsOfUse: String = "",
    @SerializedName("time_last_update_unix")
    var timeLastUpdateUnix: Int = 0,
    @SerializedName("time_last_update_utc")
    var timeLastUpdateUtc: String = "",
    @SerializedName("time_next_update_unix")
    var timeNextUpdateUnix: Int = 0,
    @SerializedName("time_next_update_utc")
    var timeNextUpdateUtc: String = "",
    @SerializedName("conversion_rates")
    var conversionRates: HashMap<String, BigDecimal> = hashMapOf()

)