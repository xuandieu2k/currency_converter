package vn.xdeuhug.currency_converter.presentation.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDialog
import androidx.core.view.isVisible
import vn.xdeuhug.currency_converter.R
import vn.xdeuhug.currency_converter.databinding.CurrencyDialogBinding
import vn.xdeuhug.currency_converter.domain.model.Currency
import vn.xdeuhug.currency_converter.presentation.ui.adapter.CurrencyAdapter
import vn.xdeuhug.currency_converter.utils.AppConstants
import vn.xdeuhug.currency_converter.utils.AppUtils
import vn.xdeuhug.currency_converter.utils.CurrencyUtils

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 26 / 10 / 2024
 */
class CurrencyDialog {
    class Builder(context: Context) : AppCompatDialog(context, R.style.CustomDialogTheme),
        CurrencyAdapter.OnListenerItemSelected {
        private var binding: CurrencyDialogBinding =
            CurrencyDialogBinding.inflate(LayoutInflater.from(context))
        private lateinit var adapter: CurrencyAdapter
        private var listData: MutableList<Currency> = mutableListOf()
        private var listSearch: MutableList<Currency> = mutableListOf()
        private var codeCurrency: String = AppConstants.USD

        private lateinit var listener: OnListenerChooseItem

        fun setListener(listener: OnListenerChooseItem): Builder = apply {
            this.listener = listener
        }

        /**
         * Search current selected index and uncheck
         * Update new selected index location
         */
        fun setCodeCurrency(code: String) {
            this.codeCurrency = code
            val previousSelectedIndex = listData.indexOfFirst { it.isSelected }
            if (previousSelectedIndex >= 0) {
                listData[previousSelectedIndex].isSelected = false
            }
            adapter.notifyItemChanged(previousSelectedIndex)
            val newIndex = listData.indexOfFirst { it.code == code }
            if (newIndex >= 0) {
                listData[newIndex].isSelected = true
                adapter.notifyItemChanged(newIndex)
            }
        }

        fun setDataList(list: List<Currency>?) {
            list?.let {
                listData = it.toMutableList()
                listSearch = it.toMutableList()
                adapter.setData(listData)
            }
        }

        init {
            setContentView(binding.root)
            setCanceledOnTouchOutside(false)
            setCancelable(false)

            window?.setLayout(
                Resources.getSystem().displayMetrics.widthPixels * 9 / 10,
                Resources.getSystem().displayMetrics.heightPixels * 9 / 10
            )
            binding.btnClose.setOnClickListener {
                this.dismiss()
            }
            setUpData()
            initSearch()

        }

        /**
         * Set up search
         */
        private fun initSearch() {
            binding.svCurrency.queryAfterTextChanged(1000) {
                if (it.isEmpty()) {
                    adapter.setData(listData)
                    setViewEmpty(isShow = false)
                } else {
                    search(AppUtils.removeVietnameseFromString(it).lowercase())
                }
            }
        }

        /**
         * Check if currency code or currency name contains keyword then add to list
         */
        private fun search(query: String) {
            listSearch.clear()
            listData.filter {
                it.code.lowercase().contains(query) || it.name.lowercase().contains(query)
            }.let { listSearch.addAll(it) }
            adapter.setData(listSearch)
            setViewEmpty(isShow = listSearch.isEmpty())
        }

        override fun create() {
            show()
        }

        fun showDialog() {
            show()
        }

        private fun setUpData() {
            listData.addAll(CurrencyUtils.listCurrency)
            listData.find { it.code == codeCurrency }?.isSelected = true
            adapter = CurrencyAdapter()
            adapter.setData(listData)
            adapter.setListener(this)
            setViewEmpty(isShow = false)
            AppUtils.initRecyclerView(binding.rvCurrency, adapter)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onItemSelected(currency: Currency, position: Int) {
            val previousSelectedIndex = listData.indexOfFirst { it.isSelected }
            if (previousSelectedIndex >= 0) {
                listData[previousSelectedIndex].isSelected = false
            }
            listData[position].isSelected = true
            adapter.notifyItemChanged(previousSelectedIndex)
            adapter.notifyItemChanged(position)

            listener.onItemChoose(currency)
            dismiss()
        }

        private fun setViewEmpty(isShow: Boolean) {
            binding.llEmptyData.isVisible = isShow
            binding.rvCurrency.isVisible = !isShow
        }

        interface OnListenerChooseItem {
            fun onItemChoose(currency: Currency)
        }

    }
}