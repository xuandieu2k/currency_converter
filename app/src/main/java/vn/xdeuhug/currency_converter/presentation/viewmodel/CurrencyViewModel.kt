package vn.xdeuhug.currency_converter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.xdeuhug.currency_converter.domain.model.ConversionResult
import vn.xdeuhug.currency_converter.domain.model.Currency
import vn.xdeuhug.currency_converter.domain.usecase.ConvertCurrencyUseCase
import vn.xdeuhug.currency_converter.utils.AppConstants
import vn.xdeuhug.currency_converter.utils.CurrencyUtils.getCurrencyByCode
import vn.xdeuhug.currency_converter.utils.CurrencyUtils.getFlag
import vn.xdeuhug.currency_converter.utils.DateUtils
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {
    // Amount
    private val _convertedAmount = MutableLiveData(BigDecimal.ZERO)
    val convertedAmount: LiveData<BigDecimal> get() = _convertedAmount
    private val _amount = MutableLiveData(BigDecimal.ZERO)
    val amount: LiveData<BigDecimal> get() = _amount

    // Code
    private val _targetCode = MutableLiveData(AppConstants.VND)
    val targetCode: LiveData<String> get() = _targetCode
    private val _baseCode = MutableLiveData(AppConstants.USD)
    val baseCode: LiveData<String> get() = _baseCode

    // Hashmap All Currencies
    private val _allCurrencies = MutableLiveData<HashMap<String, Currency>>()
    val allCurrencies: LiveData<HashMap<String, Currency>> get() = _allCurrencies

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _basePrice = MutableLiveData<BigDecimal>()
    val basePrice: LiveData<BigDecimal> get() = _basePrice
    private val _targetPrice = MutableLiveData<BigDecimal>()
    val targetPrice: LiveData<BigDecimal> get() = _targetPrice
    private val _dayUpdate: MutableLiveData<String?> = MutableLiveData()
    val dayUpdate: LiveData<String?> get() = _dayUpdate
    private val _nextUpdate: MutableLiveData<String?> = MutableLiveData()
    val nextUpdate: LiveData<String?> get() = _nextUpdate

    private val _convertLocal: MutableLiveData<Boolean> = MutableLiveData(false)
    val convertLocal: LiveData<Boolean> get() = _convertLocal
    private var newConversionResult = ConversionResult(
        baseCurrency = "",
        targetCurrency = "",
        timeNextUpdateUtc = "",
        timeLastUpdateUtc = "",
        rates = hashMapOf()
    )


    fun setBaseCode(code: String) {
        _baseCode.value = code
    }

    fun setTargetCode(code: String) {
        _targetCode.value = code
    }

    fun setAmount(amount: BigDecimal) {
        _amount.value = amount
    }

    fun setConvertedAmount(amount: BigDecimal) {
        _convertedAmount.value = amount
    }

    fun setConvertLocal(isConvert: Boolean) {
        _convertLocal.value = isConvert
    }


    fun convertCurrency(amount: BigDecimal, from: String, to: String) {
        viewModelScope.launch {
            try {
                val result = convertCurrencyUseCase(amount, from, to)
                _convertedAmount.value = result
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Conversion failed: ${e.message}"
            }
        }
    }


    fun convertAllCurrencies() {
        viewModelScope.launch {
            try {
                newConversionResult = convertCurrencyUseCase.getAllCurrencies(
                    _baseCode.value ?: AppConstants.USD, _targetCode.value ?: AppConstants.VND
                )
                if(newConversionResult == ConversionResult(
                        baseCurrency = "",
                        targetCurrency = "",
                        rates = hashMapOf(),
                        timeLastUpdateUtc = "",
                        timeNextUpdateUtc = ""
                    )){
                    _errorMessage.value = "An error occurred. Please try again later!"
                    return@launch
                }
                val map = HashMap<String, Currency>()
                newConversionResult.rates.forEach { item ->
                    val currencyWithCode = getCurrencyByCode(item.key)
                    currencyWithCode?.let {
                        map[item.key] = Currency(
                            code = item.key,
                            name = currencyWithCode.name,
                            rate = item.value,
                            countryCode = getFlag(item.key),
                            country = currencyWithCode.country
                        )
                    }
                }
                _allCurrencies.value = map
                _convertedAmount.value =
                    _amount.value?.times(newConversionResult.rates[_targetCode.value] ?: BigDecimal.ZERO)
                _basePrice.value = BigDecimal.ONE
                _targetPrice.value = newConversionResult.rates[_targetCode.value] ?: BigDecimal.ZERO
                _dayUpdate.value =
                    DateUtils.convertDateFormat(newConversionResult.timeLastUpdateUtc)
                _nextUpdate.value =
                    DateUtils.convertDateFormat(newConversionResult.timeNextUpdateUtc)
            } catch (e: Exception) {
                _errorMessage.value = "Conversion failed: ${e.message}"
            }
        }
    }

    fun convertLocalAllCurrencies() {
        viewModelScope.launch {
            try {
                _convertedAmount.value =
                    _amount.value?.times(newConversionResult.rates[_targetCode.value] ?: BigDecimal.ZERO)
                _convertLocal.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Conversion failed: ${e.message}"
            }
        }
    }

    // Handle View
    private fun isValidAmount(value: BigDecimal?): Boolean {
        return value != null && value > BigDecimal.ZERO
    }

    fun onAmountChange(value: String) {
        val decimalValue = value.toBigDecimalOrNull()
        if (isValidAmount(decimalValue)) {
            _amount.value = decimalValue
            _errorMessage.value = null
        } else {
            _errorMessage.value = "Amount must be greater than 0"
        }
    }


}