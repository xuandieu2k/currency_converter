package vn.xdeuhug.currency_converter.presentation.ui.dialog

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import vn.xdeuhug.currency_converter.R
import vn.xdeuhug.currency_converter.databinding.CalculatorDialogBinding
import vn.xdeuhug.currency_converter.utils.AppConstants
import vn.xdeuhug.currency_converter.utils.AppUtils
import java.math.BigDecimal

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 26 / 10 / 2024
 */
/**
 *
 */
class CalculatorDialog {
    class Builder(
        private var context: Context,
        private var key: Int,
        private var currentNumber: String,
        private var maxValue: String,
        title: String,
        private var isAllowZero: Boolean,
        private var allowZeroHundred: Boolean
    ) : AppCompatDialog(context, R.style.CustomDialogTheme), View.OnClickListener {
        private var binding: CalculatorDialogBinding =
            CalculatorDialogBinding.inflate(LayoutInflater.from(context))

        private lateinit var onActionDone: OnActionDone

        fun setOnActionDone(onActionDone: OnActionDone): Builder = apply {
            this.onActionDone = onActionDone
        }

        override fun create() {
            show()
        }

        fun showDialog() {
            show()
        }

        init {
            setContentView(binding.root)
            setCanceledOnTouchOutside(true)
            setCancelable(true)

            window?.setLayout(
                Resources.getSystem().displayMetrics.widthPixels * 9 / 10,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            currentNumber = AppUtils.formatBigDecimalToString(currentNumber.toBigDecimal())
            binding.tvInput.text = AppUtils.getDecimalFormattedString(currentNumber)
            binding.tvTitle.text = title
            if (key == AppConstants.INTEGER_INPUT_TABLE) {
                binding.btnKey000.visibility = View.VISIBLE
                binding.btnKeyDot.visibility = View.GONE
            } else if (key == AppConstants.DECIMAL_INPUT_TABLE) {
                binding.btnKey000.visibility = View.GONE
                binding.btnKeyDot.visibility = View.VISIBLE
            }
            eventClick()
        }

        private fun eventClick() {
            binding.btnKeyDot.setOnClickListener(this)
            binding.btnKey000.setOnClickListener(this)
            binding.btnKey0.setOnClickListener(this)
            binding.btnKey1.setOnClickListener(this)
            binding.btnKey2.setOnClickListener(this)
            binding.btnKey3.setOnClickListener(this)
            binding.btnKey4.setOnClickListener(this)
            binding.btnKey5.setOnClickListener(this)
            binding.btnKey6.setOnClickListener(this)
            binding.btnKey7.setOnClickListener(this)
            binding.btnKey8.setOnClickListener(this)
            binding.btnKey9.setOnClickListener(this)
            binding.btnKeyMinus.setOnClickListener(this)
            binding.btnKeyPlus.setOnClickListener(this)
            binding.btnKeyReset.setOnClickListener(this)
            binding.btnAccept.setOnClickListener(this)
            binding.btnKeyBack.setOnClickListener(this)
        }

        private fun handleDot() {
            try {
                val cleanedNumber = currentNumber.replace(",", "")
                val numCheck = if (cleanedNumber.endsWith('.')) {
                    cleanedNumber.replace(".", "").toBigDecimal()
                } else {
                    cleanedNumber.toBigDecimalOrNull() ?: BigDecimal.ZERO
                }
                currentNumber = if (numCheck < maxValue.toBigDecimal()) {
                    if (!cleanedNumber.contains(".")) {
                        "${AppUtils.getNumberFormattedBigDecimal(numCheck)}."
                    } else {
                        AppUtils.getNumberFormattedBigDecimal(numCheck)
                    }
                } else {
                    AppUtils.getNumberFormattedBigDecimal(maxValue.toBigDecimal())
                }
            } catch (e: NumberFormatException) {
                currentNumber = "0"
                Log.e("CalculatorDialog", "Invalid number format: ${e.message}")
            }
            binding.tvInput.text = currentNumber
        }


        private fun handleKey000() {
            currentNumber = currentNumber.replace(",", "")
            customCurrentNumber("0")
            formatCurrentNumber()
        }

        private fun handleBack() {
            currentNumber = currentNumber.replace(",", "")
            currentNumber = if (currentNumber.length < 2) {
                "0"
            } else {
                currentNumber.substring(0, currentNumber.length - 1)
            }
            initNumberToView()
        }

        private fun handleReset() {
            currentNumber = "0"
            binding.tvInput.text = currentNumber
        }

        private fun handleAccept() {
            binding.btnAccept.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnAccept.isEnabled = true
            }, 2000)
            actionDone()
        }

        private fun handleMinus() {
            currentNumber = currentNumber.replace(",", "")
            eventMinus()
            formatCurrentNumber()
        }

        private fun handlePlus() {
            currentNumber = currentNumber.replace(",", "")
            eventPlus()
            formatCurrentNumber()
        }

        private fun handleNumberInput(input: String) {
            currentNumber = currentNumber.replace(",", "")
            customCurrentNumber(input)
            formatCurrentNumber()
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.btnKeyDot -> handleDot()
                R.id.btnKey000 -> handleKey000()
                R.id.btnKeyBack -> handleBack()
                R.id.btnKeyReset -> handleReset()
                R.id.btnAccept -> handleAccept()
                R.id.btnKeyMinus -> handleMinus()
                R.id.btnKeyPlus -> handlePlus()
                else -> {
                    val number = when (view.id) {
                        R.id.btnKey0 -> "0"
                        R.id.btnKey1 -> "1"
                        R.id.btnKey2 -> "2"
                        R.id.btnKey3 -> "3"
                        R.id.btnKey4 -> "4"
                        R.id.btnKey5 -> "5"
                        R.id.btnKey6 -> "6"
                        R.id.btnKey7 -> "7"
                        R.id.btnKey8 -> "8"
                        R.id.btnKey9 -> "9"
                        else -> return
                    }
                    handleNumberInput(number)
                }
            }
        }

        /**
         * Process strings with decimals and display messages if the result does not match
         * Then return the new processed value for @param currentNumber
         */
        private fun customCurrentNumber(keyNumber: String) {
            currentNumber = if (key == AppConstants.DECIMAL_INPUT_TABLE) {
                val newValue = "$currentNumber$keyNumber".toBigDecimalOrNull()
                if ((currentNumber.toBigDecimal() > BigDecimal.ZERO || currentNumber == "0." || currentNumber == "0.0") && newValue != null && newValue <= maxValue.toBigDecimal()) {
                    if (currentNumber.contains(".0") || "$currentNumber$keyNumber".contains(".[0-9][0-9]$".toRegex())) {
                        AppUtils.formatBigDecimalToString(newValue)
                    } else {
                        currentNumber + keyNumber
                    }
                } else {
                    if (keyNumber.toBigDecimal() <= maxValue.toBigDecimal()) {
                        if (currentNumber.toBigDecimal() == BigDecimal.ZERO) keyNumber
                        else {
                            AppUtils.showToast(
                                context, context.getString(R.string.max_value_input) + " ${
                                    AppUtils.getNumberFormattedBigDecimal(maxValue.toBigDecimal())
                                }"
                            )
                            maxValue
                        }
                    } else {
                        AppUtils.showToast(
                            context, context.getString(R.string.max_value_input) + " ${
                                AppUtils.getNumberFormattedBigDecimal(maxValue.toBigDecimal())
                            }"
                        )
                        maxValue
                    }
                }
            } else {
                val newValue = "$currentNumber$keyNumber".toBigDecimalOrNull()
                if (currentNumber.toBigDecimal() > BigDecimal.ZERO && newValue != null && newValue <= maxValue.toBigDecimal()) {
                    currentNumber + keyNumber
                } else {
                    if (currentNumber.toBigDecimal() == BigDecimal.ZERO) keyNumber
                    else {
                        AppUtils.showToast(
                            context, context.getString(R.string.max_value_input) + " ${
                                AppUtils.getNumberFormattedBigDecimal(maxValue.toBigDecimal())
                            }"
                        )
                        maxValue
                    }
                }
            }
        }


        private fun formatCurrentNumber() {
            try {
                val number = BigDecimal(currentNumber)
                if (number < BigDecimal.ZERO || number > maxValue.toBigDecimal()) {
                    currentNumber = "0"
                } else {
                    if (currentNumber.contains(".")) {
                        val temp = currentNumber.split(".")
                        if (temp.size > 1 && temp[1].length > 2) {
                            currentNumber = "${temp[0]}.${temp[1].substring(0, 2)}"
                        }
                    } else if (number > maxValue.toBigDecimal()) {
                        currentNumber = "0"
                    }
                }
            } catch (e: NumberFormatException) {
                currentNumber = "0"
            }

            Log.d("Log NumberCurrent: ", currentNumber)

            initNumberToView()
        }

        private fun initNumberToView() {
            binding.tvInput.text = AppUtils.getDecimalFormattedString(currentNumber)
        }

        private fun eventMinus() {
            val currentValue = currentNumber.toBigDecimalOrNull() ?: BigDecimal.ZERO
            if (key == AppConstants.INTEGER_INPUT_TABLE) {
                currentNumber = if (currentValue <= BigDecimal.ZERO) {
                    "0"
                } else {
                    val result = currentValue.subtract(BigDecimal.ONE)
                    AppUtils.formatBigDecimalToString(result)
                }
            } else {
                if (currentValue <= BigDecimal.ZERO) {
                    currentNumber = "0.00"
                } else {
                    val result = currentValue.subtract(BigDecimal("0.01"))
                    currentNumber = AppUtils.formatBigDecimalToString(result)
                }
            }
        }

        private fun eventPlus() {
            val currentValue = currentNumber.toBigDecimalOrNull() ?: BigDecimal.ZERO
            if (key == AppConstants.INTEGER_INPUT_TABLE) {
                currentNumber = if (currentValue >= maxValue.toBigDecimal()) {
                    maxValue
                } else {
                    val result = currentValue.add(BigDecimal.ONE)
                    AppUtils.formatBigDecimalToString(result)
                }
            } else {
                if (currentValue >= maxValue.toBigDecimal()) {
                    AppUtils.showToast(
                        context, context.getString(R.string.max_value_input) + " ${
                            AppUtils.getDecimalFormattedString(maxValue)
                        }"
                    )
                } else {
                    val result = currentValue.add(BigDecimal("0.01"))
                    currentNumber = AppUtils.formatBigDecimalToString(result)
                }
            }
        }

        private fun actionDone() {
            if (key == AppConstants.INTEGER_INPUT_TABLE) {
                if (checkIntegerInput()) {
                    onActionDone.onDoneAction(currentNumber.replace(",", ""))
                    dismiss()
                }
            } else {
                if (checkDecimalInput()) {
                    if (currentNumber.endsWith('.')) {
                        currentNumber = currentNumber.dropLast(1)
                    }
                    onActionDone.onDoneAction(currentNumber.replace(",", ""))
                    dismiss()
                }
            }
        }

        private fun checkIntegerInput(): Boolean {
            val currentValue = currentNumber.replace(",", "").toBigDecimalOrNull() ?: return false

            if (!allowZeroHundred) {
                if (!isAllowZero && currentValue < BigDecimal(100)) {
                    AppUtils.showToast(
                        context, context.getString(R.string.min_value_of_integer_input)
                    )
                    return false
                }

                return if (currentValue > maxValue.toBigDecimal()) {
                    AppUtils.showToast(
                        context, context.getString(R.string.max_value_input) + " ${
                            AppUtils.getDecimalFormattedString(
                                maxValue
                            )
                        }"
                    )
                    false
                } else {
                    true
                }
            } else {
                if (isAllowZero && currentValue < BigDecimal(100) && currentValue != BigDecimal.ZERO) {
                    AppUtils.showToast(
                        context,
                        context.getString(R.string.min_value_of_integer_input_allow_zero_hundred)
                    )
                    return false
                }

                return if (currentValue > maxValue.toBigDecimal()) {
                    AppUtils.showToast(
                        context, context.getString(R.string.max_value_input) + " ${
                            AppUtils.getDecimalFormattedString(
                                maxValue
                            )
                        }"
                    )
                    false
                } else {
                    true
                }
            }
        }

        private fun checkDecimalInput(): Boolean {
            val numCheck = currentNumber.replace(",", "").toBigDecimalOrNull() ?: return false

            if (!isAllowZero && numCheck < BigDecimal("0.01")) {
                AppUtils.showToast(context, context.getString(R.string.min_value_of_decimal_input))
                return false
            }

            return if (numCheck > maxValue.toBigDecimal()) {
                AppUtils.showToast(
                    context, context.getString(R.string.max_value_input) + " ${
                        AppUtils.getNumberFormattedBigDecimal(
                            maxValue.toBigDecimal()
                        )
                    }"
                )
                false
            } else {
                true
            }
        }

        interface OnActionDone {
            fun onDoneAction(result: String)
        }
    }
}