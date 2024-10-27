package vn.xdeuhug.currency_converter.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import vn.xdeuhug.currency_converter.base.AppAdapter
import vn.xdeuhug.currency_converter.databinding.ItemCurrencyBinding
import vn.xdeuhug.currency_converter.domain.model.Currency
import vn.xdeuhug.currency_converter.utils.CurrencyUtils

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 26 / 10 / 2024
 */
class CurrencyAdapter : AppAdapter<Currency, CurrencyAdapter.VH>() {
    private lateinit var listener: OnListenerItemSelected

    fun setListener(listenerItemSelected: OnListenerItemSelected) {
        listener = listenerItemSelected
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindData(holder: VH, item: Currency, position: Int) {
        holder.bind(item)
    }

    inner class VH(private val binding: ItemCurrencyBinding) :
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

        fun bind(item: Currency) {
            binding.imvLogoCurrency.setImageResource(
                CurrencyUtils.mapCurrencyToFlag(item.code) ?: 0
            )
            binding.tvCurrencyCode.text = item.code
            binding.imvCheck.isVisible = item.isSelected
        }
    }

    interface OnListenerItemSelected {
        fun onItemSelected(currency: Currency, position: Int)

    }
}
