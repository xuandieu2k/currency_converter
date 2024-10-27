package vn.xdeuhug.currency_converter.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.xdeuhug.currency_converter.data.remote.CurrencyApiService
import vn.xdeuhug.currency_converter.data.repository.CurrencyRepositoryImpl
import vn.xdeuhug.currency_converter.domain.repository.CurrencyRepository
import vn.xdeuhug.currency_converter.domain.usecase.ConvertCurrencyUseCase
import vn.xdeuhug.currency_converter.utils.AppConstants.API_KEY
import vn.xdeuhug.currency_converter.utils.AppConstants.API_VERSION
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String = "https://v6.exchangerate-api.com/$API_VERSION/$API_KEY/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CurrencyApiService {
        return retrofit.create(CurrencyApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        apiService: CurrencyApiService
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideConvertCurrencyUseCase(
        currencyRepository: CurrencyRepository
    ): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(currencyRepository)
    }
}