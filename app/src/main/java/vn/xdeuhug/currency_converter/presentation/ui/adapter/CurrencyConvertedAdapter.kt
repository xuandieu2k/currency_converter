package vn.xdeuhug.currency_converter.presentation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.xdeuhug.currency_converter.R
import vn.xdeuhug.currency_converter.base.AppAdapter
import vn.xdeuhug.currency_converter.databinding.ItemCurrencyConvertedBinding
import vn.xdeuhug.currency_converter.domain.model.Currency
import vn.xdeuhug.currency_converter.utils.AppConstants
import vn.xdeuhug.currency_converter.utils.AppUtils
import vn.xdeuhug.currency_converter.utils.CurrencyUtils
import java.math.BigDecimal

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 26 / 10 / 2024
 */
class CurrencyConvertedAdapter(private val context: Context) :
    AppAdapter<Currency, CurrencyConvertedAdapter.VH>() {

    private lateinit var listener: OnListenerItemSelected
    private var baseCode: String = AppConstants.USD
    private var valueConversion = BigDecimal.ONE

    fun setBaseCode(code: String) {
        if (baseCode != code) {
            baseCode = code
            updateAllItems()
        }
    }

    fun setValueConversion(value: BigDecimal) {
        if (valueConversion != value) {
            valueConversion = value
            updateAllItems()
        }
    }

    fun setListener(listenerItemSelected: OnListenerItemSelected) {
        listener = listenerItemSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCurrencyConvertedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindData(holder: VH, item: Currency, position: Int) {
        holder.bind(item)
    }

    inner class VH(private val binding: ItemCurrencyConvertedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener.onItemSelected(item, position)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Currency) {
            binding.imvLogoCurrency.setImageResource(
                CurrencyUtils.mapCurrencyToFlag(item.code) ?: 0
            )
            binding.tvCurrency.text = "${item.code} - ${item.name}"
            binding.tvCountry.text = "( ${item.country} )"
            binding.tvRateConversion.text =
                "1 $baseCode = ${AppUtils.getNumberFormattedBigDecimal(item.rate)} ${item.code}"
            binding.tvRatePrice.text =
                "${context.getString(R.string.conversion_value)}: ${
                    AppUtils.getNumberFormattedBigDecimal(
                        item.rate * valueConversion
                    )
                } ${item.code}"
        }
    }

    interface OnListenerItemSelected {
        fun onItemSelected(currency: Currency, position: Int)
    }
}