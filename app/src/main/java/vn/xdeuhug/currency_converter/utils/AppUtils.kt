package vn.xdeuhug.currency_converter.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.*
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import vn.xdeuhug.currency_converter.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.math.BigDecimal
import java.math.RoundingMode
import java.security.SecureRandom
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.Normalizer
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern

object AppUtils {

    fun initRecyclerView(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        configRecyclerView(
            view, LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        )
        view.adapter = adapter
    }

    private fun configRecyclerView(
        recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager?
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        (recyclerView.itemAnimator)!!.changeDuration = 0
        ((recyclerView.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.isNestedScrollingEnabled = false
    }

    private fun configRecyclerViewWithFlexBoxLayout(
        recyclerView: RecyclerView, layoutManager: FlexboxLayoutManager?
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        (recyclerView.itemAnimator)!!.changeDuration = 0
        ((recyclerView.itemAnimator) as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.isNestedScrollingEnabled = false
        layoutManager!!.flexWrap = FlexWrap.WRAP
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun getMimeType(url: String): String {
        try {
            return url.substring(url.lastIndexOf(".") + 1)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getRandomString(len: Int): String {
        val AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val rnd = SecureRandom()
        val sb = java.lang.StringBuilder(len)
        for (i in 0 until len) sb.append(AB[rnd.nextInt(AB.length)])
        return sb.toString()
    }

    fun get(string: String): String {
        var result = ""
        val stringText = string.split(" ")
        for (i in stringText.indices) {
            if (stringText[i].isNotEmpty()) {
                result += stringText[i].substring(0, 1).lowercase()
            }
        }
        return result
    }

    fun formatDoubleToString(value: Double): String {
        val s: String = if (value.toInt().toDouble().compareTo(value) == 0) {
            java.lang.String.format(Locale.getDefault(), "%s", value.toInt())
        } else {
            java.lang.String.format(Locale.getDefault(), "%s", value)
        }
        return s
    }

    fun getNumberFormattedDouble(value: Double): String {
        val formatter = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.US))
        return formatter.format(value)
    }

    fun roundOffDecimal(numInDouble: Double): Double {
        return BigDecimal(numInDouble.toString()).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    fun formatAmount(amount: String): String {
        return if (amount.isNotEmpty()) {
            try {
                val bigDecimalValue = BigDecimal(amount)
                bigDecimalValue.toPlainString()
            } catch (e: NumberFormatException) {
                "Invalid Number"
            }
        } else {
            "Invalid Number"
        }
    }

    fun getNumberFormattedBigDecimal(value: BigDecimal): String {
        val formatter = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.US))
        return formatter.format(value)
    }

    fun formatBigDecimalToString(value: BigDecimal): String {
        val s: String = if (value.stripTrailingZeros().scale() <= 0) {
            // Nếu giá trị là một số nguyên
            String.format(Locale.getDefault(), "%s", value.toBigInteger())
        } else {
            String.format(Locale.getDefault(), "%s", value)
        }
        return s
    }

    fun getDecimalFormattedString(input: String): String {
        return try {
            val number = input.toBigDecimal()
            val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
                maximumFractionDigits = 2
                minimumFractionDigits = 0
            }
            formatter.format(number)
        } catch (e: NumberFormatException) {
            input
        }
    }

    fun <T> List<T>.toArrayList(): ArrayList<T> {
        return ArrayList(this)
    }

    fun ViewGroup.setOnChildClickListener(onClick: (View) -> Unit) {
        this.setOnClickListener { view ->
            onClick(view)
        }

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.setOnClickListener { view ->
                onClick(view)
            }

            if (child is ViewGroup) {
                child.setOnChildClickListener(onClick)
            }
        }
    }

    fun removeVietnameseFromString(str: String?): String {
        val slug: String = try {
            val temp = Normalizer.normalize(str, Normalizer.Form.NFD)
            val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
            pattern.matcher(temp).replaceAll("")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            ""
        }
        return slug
    }

    fun showToast(context: Context, message: String) {
        MotionToast.darkToast(
            context as Activity,
            "",
            message,
            MotionToastStyle.INFO,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, R.font.roboto_regular)
        )

    }

    fun getFlagByLanguage(): Int {
        val currentLocale = Locale.getDefault()
        val language = currentLocale.language
        return when (language) {
            "en" -> R.drawable.us
            "vi" -> R.drawable.vn
            else -> R.drawable.us

        }
    }


}