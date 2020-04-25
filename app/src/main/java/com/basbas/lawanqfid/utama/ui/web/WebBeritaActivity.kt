package com.basbas.lawanqfid.utama.ui.web

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.berita.DataItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_web_berita.*

class WebBeritaActivity : AppCompatActivity() {
    var dataItem = DataItem()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_berita)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataItem = intent.getParcelableExtra("DATA")
        supportActionBar?.title =dataItem.nama_berita

        try {


            web_berita.webViewClient = WebViewClient()
            web_berita.settings.javaScriptEnabled = true
            web_berita.loadData(dataItem.isi_berita, "text/html", "UTF-8")
        }catch (e : Exception){
            Snackbar.make(container_web,"Hanphone anda tidak di support", Snackbar.LENGTH_LONG).show()

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
           finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
