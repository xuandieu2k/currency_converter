package vn.xdeuhug.currency_converter.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import vn.xdeuhug.currency_converter.utils.AppConstants
import javax.inject.Singleton

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */

@Singleton
interface CurrencyApiService {
    @GET("latest/{base}")
    suspend fun getExchangeRates(
        @Path("base") base: String = AppConstants.USD
    ): ApiResponse

    @GET("latest/{base}/{target}")
    suspend fun getPairConversion(
        @Path("base") base: String = AppConstants.USD,
        @Path("target") target: String = AppConstants.VND,
    ): ApiResponse

    @GET("history/{base}/{year}/{month}/{day}")
    suspend fun getHistoricalRates(
        @Path("base") base: String = AppConstants.USD,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): ApiResponse


}

