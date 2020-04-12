package com.basbas.lawanqfid.utama.ui.web

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.basbas.lawanqfid.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)


        Handler().postDelayed({

            progress_circular.visibility = View.GONE
        }, 2000)

        web.webChromeClient = WebChromeClient()
        web.settings.javaScriptEnabled = true
        web.loadUrl("https://checkupcovid19.jatimprov.go.id/covid19/#!/checkup/")
    }

    override fun onBackPressed() {
        if (web.canGoBack()){
            web.goBack()
        } else{
            finish()
        }
    }
}
