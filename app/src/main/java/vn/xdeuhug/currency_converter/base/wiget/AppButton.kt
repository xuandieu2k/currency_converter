package vn.xdeuhug.currency_converter.base.wiget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import vn.xdeuhug.currency_converter.R

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 01 / 05 / 2024
 */
class AppButton : AppCompatButton {
    private var typeFont = ResourcesCompat.getFont(context, R.font.roboto_regular)

    constructor(context: Context?) : super(context!!) {
        typeface = typeFont
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        typeface = typeFont
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        typeface = typeFont
    }
}