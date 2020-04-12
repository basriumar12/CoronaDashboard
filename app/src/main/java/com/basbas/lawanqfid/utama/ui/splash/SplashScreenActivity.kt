package com.basbas.lawanqfid.utama.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.base.BaseActivity
import com.basbas.lawanqfid.utama.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        greetings()
        goToAnotherActivity()
    }

    private fun goToAnotherActivity() {
        Handler().postDelayed({

            startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            finish()
        }, 4000)
    }

    private fun greetings() {
        val calendar: Calendar = Calendar.getInstance()
        val timeOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            tv_greeting.setText("Selamat Pagi #DiRumahAJa")

        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            tv_greeting.setText("Selamat Siang #DiRumahAJa")

        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            tv_greeting.setText("Selamat Sore #DiRumahAJa")

        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tv_greeting.setText("Selamat Malam #DiRumahAJa")
            tv_greeting.setTextColor(Color.WHITE)

        }
    }
}
