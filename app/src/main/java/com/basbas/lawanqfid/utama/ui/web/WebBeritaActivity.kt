package com.basbas.lawanqfid.utama.ui.web

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.berita.DataItem
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.activity_web_berita.*

class WebBeritaActivity : AppCompatActivity() {
    var dataItem = DataItem()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_berita)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataItem = intent.getParcelableExtra("DATA")
        supportActionBar?.title =dataItem.nama_berita

        web_berita.webChromeClient = WebChromeClient()
        web_berita.settings.javaScriptEnabled = true
        web_berita.loadData(dataItem.isi_berita,"text/html", "UTF-8")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
           finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (web.canGoBack()){
            web.goBack()
        } else{
            finish()
        }
    }
}
