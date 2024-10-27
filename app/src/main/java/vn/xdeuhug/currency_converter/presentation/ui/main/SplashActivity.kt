package vn.xdeuhug.currency_converter.presentation.ui.main

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import vn.xdeuhug.currency_converter.base.AppActivity
import vn.xdeuhug.currency_converter.databinding.SplashActivityBinding

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 27 / 10 / 2024
 */
class SplashActivity : AppActivity() {
    private lateinit var binding: SplashActivityBinding
    override fun getLayoutView(): View {
        binding = SplashActivityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun initData() {
        //
    }

    override fun observerData() {
        //
    }
}