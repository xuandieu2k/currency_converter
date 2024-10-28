package vn.xdeuhug.currency_converter.presentation.ui.main

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.xdeuhug.currency_converter.R
import vn.xdeuhug.currency_converter.base.AppActivity
import vn.xdeuhug.currency_converter.databinding.MainActivityBinding
import vn.xdeuhug.currency_converter.domain.model.Currency
import vn.xdeuhug.currency_converter.presentation.ui.adapter.CurrencyConvertedAdapter
import vn.xdeuhug.currency_converter.presentation.ui.dialog.CalculatorDialog
import vn.xdeuhug.currency_converter.presentation.ui.dialog.CurrencyDialog
import vn.xdeuhug.currency_converter.presentation.viewmodel.CurrencyViewModel
import vn.xdeuhug.currency_converter.presentation.viewmodel.NetworkViewModel
import vn.xdeuhug.currency_converter.utils.AppConstants
import vn.xdeuhug.currency_converter.utils.AppUtils
import vn.xdeuhug.currency_converter.utils.AppUtils.setOnChildClickListener
import vn.xdeuhug.currency_converter.utils.CurrencyUtils
import vn.xdeuhug.currency_converter.utils.Resource
import java.math.BigDecimal

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 25 / 10 / 2024
 */
@AndroidEntryPoint
class MainActivity : AppActivity(), CurrencyConvertedAdapter.OnListenerItemSelected {
    private lateinit var binding: MainActivityBinding
    private val viewModel: CurrencyViewModel by viewModels()
    private val netWorkViewModel: NetworkViewModel by viewModels()
    private lateinit var dialogCurrency: CurrencyDialog.Builder
    private lateinit var currencyConvertedAdapter: CurrencyConvertedAdapter
    private var listCurrency: MutableList<Currency> = mutableListOf()
    private var listCurrencyDialog: MutableList<Currency> = mutableListOf()
    private var tempAmount = BigDecimal.ZERO
    private var tempConvertedAmount = BigDecimal.ZERO
    private var tempCode = AppConstants.USD
    private var tempTargetCode = AppConstants.VND

    override fun getLayoutView(): View {
        binding = MainActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        showLayoutMoreData(isShow = false, isHaveData = false)
        showLayoutInformation(isShow = false)
        setUpView()
    }

    override fun initData() {
        currencyConvertedAdapter = CurrencyConvertedAdapter(this)
        currencyConvertedAdapter.setData(listCurrency)
        currencyConvertedAdapter.setBaseCode(viewModel.baseCode.value ?: AppConstants.USD)
        currencyConvertedAdapter.setValueConversion(viewModel.amount.value ?: BigDecimal.ONE)
        currencyConvertedAdapter.setListener(this)
        AppUtils.initRecyclerView(
            binding.llOtherCurrencies.rvOtherCurrencies, currencyConvertedAdapter
        )
        listCurrencyDialog.addAll(CurrencyUtils.listCurrency)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun observerData() {
        viewModel.amount.observe(this) { amount ->
            binding.edtAmount.text = AppUtils.getNumberFormattedBigDecimal(amount)
        }

        viewModel.convertedAmount.observe(this) {
            binding.edtConvertedAmount.text = AppUtils.getNumberFormattedBigDecimal(it)
            tempConvertedAmount = it
            tempAmount = viewModel.amount.value ?: BigDecimal.ZERO
            showLayoutInformation(isShow = it != BigDecimal.ZERO)
        }

        viewModel.baseCode.observe(this) {
            binding.tvCode.text = it
            binding.imvBaseCurrency.setImageResource(CurrencyUtils.mapCurrencyToFlag(it) ?: 0)
            binding.llInformation.imvBaseCurrencyMini.setImageResource(
                CurrencyUtils.mapCurrencyToFlag(
                    it
                ) ?: 0
            )
            viewModel.setConvertedAmount(BigDecimal.ZERO)
            showLayoutInformation(isShow = false)
            showLayoutMoreData(isShow = false, isHaveData = false)
        }

        viewModel.targetCode.observe(this) {
            binding.tvConvertedCode.text = it
            binding.imvTargetCurrency.setImageResource(CurrencyUtils.mapCurrencyToFlag(it) ?: 0)
            binding.llInformation.imvTargetCurrencyMini.setImageResource(
                CurrencyUtils.mapCurrencyToFlag(
                    it
                ) ?: 0
            )
            viewModel.setConvertedAmount(BigDecimal.ZERO)
            showLayoutInformation(isShow = false)
            showLayoutMoreData(isShow = false, isHaveData = false)
        }

        viewModel.basePrice.observe(this) {
            it?.let {
                binding.llInformation.tvBasePrice.text =
                    "${AppUtils.getNumberFormattedBigDecimal(it)} ${viewModel.baseCode.value}"
            }
        }

        viewModel.targetPrice.observe(this) {
            it?.let {
                binding.llInformation.tvTargetPrice.text =
                    "${AppUtils.getNumberFormattedBigDecimal(it)} ${viewModel.targetCode.value}"
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                resetTempData()
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                hideDialog()
            }
        }

        viewModel.dayUpdate.observe(this) {
            it?.let {
                binding.llInformation.tvUpdateAt.text = getString(R.string.update_at) + ": " + it
            }
        }

        viewModel.nextUpdate.observe(this) {
            it?.let {
                binding.llInformation.tvNextUpdateAt.text =
                    getString(R.string.next_update_at) + ": " + it
            }
        }

        /**
         * @param resource
         * resource is Loading: show dialog Loading
         * resource is Success: update data, layout and hide dialog
         * resource is Error: hide dialog and show notification error
         */
        viewModel.allCurrencies.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        showDialog()
                    }
                }
                is Resource.Success -> {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val listCurrency = resource.data ?: emptyMap()
                        val updatedList = if (listCurrency.isNotEmpty()) {
                            listCurrency.values.toList()
                        } else {
                            emptyList()
                        }

                        withContext(Dispatchers.Main) {
                            if (updatedList.isNotEmpty()) {
                                currencyConvertedAdapter.setBaseCode(
                                    viewModel.baseCode.value ?: AppConstants.USD
                                )
                                currencyConvertedAdapter.setValueConversion(
                                    viewModel.amount.value ?: BigDecimal.ONE
                                )

                                this@MainActivity.listCurrency.clear()
                                this@MainActivity.listCurrency.addAll(updatedList)
                                currencyConvertedAdapter.notifyDataSetChanged()
                                showLayoutMoreData(isShow = true, isHaveData = true)
                            } else {
                                showLayoutMoreData(isShow = true, isHaveData = false)
                            }
                            hideDialog()
                        }
                    }
                }
                is Resource.Error -> {
                    val errorMessage = resource.message ?: "An unknown error occurred"
                    lifecycleScope.launch(Dispatchers.Main) {
                        resetTempData()
                        showLayoutMoreData(isShow = false, isHaveData = false)
                        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        hideDialog()
                    }
                }
            }
        }

        viewModel.convertLocal.observe(this) {
            if (it) {
                currencyConvertedAdapter.setBaseCode(viewModel.baseCode.value ?: AppConstants.USD)
                currencyConvertedAdapter.setValueConversion(
                    viewModel.amount.value ?: BigDecimal.ONE
                )
                showLayoutInformation(isShow = true)
                showLayoutMoreData(isShow = true, isHaveData = true)
                viewModel.setConvertLocal(false)
                Handler(Looper.getMainLooper()).postDelayed({ hideDialog() }, 2000)
            }

        }


        netWorkViewModel.isNetworkAvailable.observe(this) {
            if (it) {
                Toast.makeText(this, getString(R.string.internet_available), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setUpView() {
        binding.imvFlag.setImageResource(AppUtils.getFlagByLanguage())
        dialogCurrency = CurrencyDialog.Builder(this)
        dialogCurrency.setDataList(listCurrencyDialog)

        binding.edtAmount.setOnClickListener {
            val amountValue = viewModel.amount.value ?: 0.0
            Log.d("TAG", "formatCurrentNumber: $$amountValue")
            CalculatorDialog.Builder(
                this,
                AppConstants.DECIMAL_INPUT_TABLE,
                amountValue.toString(),
                Double.MAX_VALUE.toString(),
                title = getString(R.string.enter_money),
                isAllowZero = false,
                allowZeroHundred = false
            ).setOnActionDone(object : CalculatorDialog.Builder.OnActionDone {
                override fun onDoneAction(result: String) {
                    Log.d("TAG", "onActionDone: $result")
                    viewModel.onAmountChange(result)
                }
            }).create()
        }



        binding.llBaseCurrency.setOnChildClickListener {
            if (dialogCurrency.isShowing) {
                return@setOnChildClickListener
            }
            dialogCurrency = CurrencyDialog.Builder(this)
                .setListener(object : CurrencyDialog.Builder.OnListenerChooseItem {
                    override fun onItemChoose(currency: Currency) {
                        viewModel.setBaseCode(currency.code)
                        listCurrency.clear()
                        resetTempData()
                    }
                })
            dialogCurrency.setCodeCurrency(viewModel.baseCode.value ?: AppConstants.USD)
            dialogCurrency.create()

        }

        binding.llTargetCurrency.setOnChildClickListener {
            if (dialogCurrency.isShowing) {
                return@setOnChildClickListener
            }
            dialogCurrency = CurrencyDialog.Builder(this)
                .setListener(object : CurrencyDialog.Builder.OnListenerChooseItem {
                    override fun onItemChoose(currency: Currency) {
                        viewModel.setTargetCode(currency.code)
                        listCurrency.clear()
                        resetTempData()
                    }
                })
            dialogCurrency.setCodeCurrency(viewModel.targetCode.value ?: AppConstants.VND)
            dialogCurrency.create()
        }

        binding.btnConvert.setOnClickListener {
            validConvert()
        }
    }

    private fun validConvert() {
        if (netWorkViewModel.isNetworkAvailable.value == false) {
            AppUtils.showToast(this, getString(R.string.check_internet_and_try_again))
            return
        }

        if (tempAmount == viewModel.amount.value && tempConvertedAmount == viewModel.convertedAmount.value && viewModel.amount.value != BigDecimal.ZERO) {
            return
        }
        if ((viewModel.amount.value ?: BigDecimal.ZERO) <= BigDecimal.ZERO) {
            AppUtils.showToast(this, getString(R.string.money_conversion_not_valid))
        } else {
            if (viewModel.baseCode.value == tempCode && viewModel.targetCode.value == tempTargetCode && viewModel.amount.value != BigDecimal.ZERO && listCurrency.isNotEmpty()) {
                viewModel.convertLocalAllCurrencies()
                return
            }
            viewModel.convertAllCurrencies()
            tempAmount = viewModel.amount.value ?: BigDecimal.ZERO
            tempConvertedAmount = viewModel.convertedAmount.value ?: BigDecimal.ZERO
            tempCode = viewModel.baseCode.value ?: AppConstants.USD
            tempTargetCode = viewModel.targetCode.value ?: AppConstants.VND
        }
    }

    private fun showLayoutMoreData(isShow: Boolean, isHaveData: Boolean) {
        binding.llOtherCurrencies.root.isVisible = isShow
        binding.llOtherCurrencies.rvOtherCurrencies.isVisible = isHaveData
        binding.llOtherCurrencies.llNoData.isVisible = !isHaveData
    }

    private fun showLayoutInformation(isShow: Boolean) {
        binding.llInformation.root.isVisible = isShow
    }

    private fun resetTempData() {
        tempAmount = BigDecimal.ZERO
        tempConvertedAmount = BigDecimal.ZERO
        tempCode = AppConstants.USD
        tempTargetCode = AppConstants.VND
    }

    override fun onItemSelected(currency: Currency, position: Int) {
        //
    }
}