package vn.xdeuhug.currency_converter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.xdeuhug.currency_converter.domain.model.ConversionResult
import vn.xdeuhug.currency_converter.domain.model.Currency
import vn.xdeuhug.currency_converter.domain.usecase.ConvertCurrencyUseCase
import vn.xdeuhug.currency_converter.utils.AppConstants
import vn.xdeuhug.currency_converter.utils.CurrencyUtils.getCurrencyByCode
import vn.xdeuhug.currency_converter.utils.CurrencyUtils.getFlag
import vn.xdeuhug.currency_converter.utils.DateUtils
import vn.xdeuhug.currency_converter.utils.Resource
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
    private val _allCurrencies = MutableLiveData<Resource<HashMap<String, Currency>>>()
    val allCurrencies: LiveData<Resource<HashMap<String, Currency>>> get() = _allCurrencies

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
            if (isValidAmount(amount)) {
                try {
                    val result = convertCurrencyUseCase(amount, from, to)
                    _convertedAmount.value = result
                    _errorMessage.value = null
                } catch (e: Exception) {
                    _errorMessage.value = "Conversion failed: ${e.message}"
                    _convertedAmount.value = BigDecimal.ZERO
                }
            } else {
                _errorMessage.value = "Amount must be greater than 0"
                _convertedAmount.value = BigDecimal.ZERO
            }
        }
    }

    /**
     * Call the api to get the list of currencies with the code(baseCode).
     * Then save the list and convert from decimal value to Currency object.
     * Finally, handle errors and update live data variables to update views
     */
    fun convertAllCurrencies() {
        viewModelScope.launch {
            _allCurrencies.value = Resource.Loading()
            try {
                val conversionResultResource = withContext(Dispatchers.IO) {
                    convertCurrencyUseCase.getAllCurrencies(
                        _baseCode.value ?: AppConstants.USD,
                        _targetCode.value ?: AppConstants.VND
                    )
                }

                handleConversionResult(conversionResultResource)

            } catch (e: Exception) {
                _allCurrencies.value = Resource.Error("Conversion failed: ${e.message}")
            }
        }
    }

    // Helper function for handling conversion result
    private fun handleConversionResult(conversionResultResource: Resource<ConversionResult>) {
        when (conversionResultResource) {
            is Resource.Success -> {
                val newConversionResult = conversionResultResource.data ?: return
                if (newConversionResult.baseCurrency.isEmpty() && newConversionResult.targetCurrency.isEmpty()) {
                    _allCurrencies.value = Resource.Error("An error occurred. Please try again later!")
                    return
                }
                val map = createCurrencyMap(newConversionResult.rates)
                updateViewData(map, newConversionResult)
            }
            is Resource.Error -> {
                _allCurrencies.value = Resource.Error(conversionResultResource.message ?: "Conversion failed")
            }
            is Resource.Loading -> {
                _allCurrencies.value = Resource.Loading()
            }
        }
    }

    // Helper function for creating currency map
    private fun createCurrencyMap(rates: Map<String, BigDecimal>): HashMap<String, Currency> {
        val map = HashMap<String, Currency>()
        rates.forEach { item ->
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
        return map
    }

    // Helper function for updating LiveData values
    private fun updateViewData(map: HashMap<String, Currency>, result: ConversionResult) {
        _allCurrencies.value = Resource.Success(map)
        _convertedAmount.value = _amount.value?.times(result.rates[_targetCode.value] ?: BigDecimal.ZERO)
        _basePrice.value = BigDecimal.ONE
        _targetPrice.value = result.rates[_targetCode.value] ?: BigDecimal.ZERO
        _dayUpdate.value = DateUtils.convertDateFormat(result.timeLastUpdateUtc)
        _nextUpdate.value = DateUtils.convertDateFormat(result.timeNextUpdateUtc)
        newConversionResult = result
    }

    /**
     * baseCode and targetCode are unchanged and have called convertAllCurrencies() api then call this function to update local.
     * Limit api calls
     */
    fun convertLocalAllCurrencies() {
        viewModelScope.launch {
            _allCurrencies.value = Resource.Loading()
            try {
                _convertedAmount.value =
                    _amount.value?.times(newConversionResult.rates[_targetCode.value] ?: BigDecimal.ZERO)
                _convertLocal.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Conversion failed: ${e.message}"
            }
        }
    }

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