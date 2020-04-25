package com.basbas.lawanqfid.utama.ui.web

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.berita.DataItem
import com.basbas.lawanqfid.utama.model.youtube.ResponseYoutube
import com.basbas.lawanqfid.utama.network.ApiInterface
import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_web_notif.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebNotifActivity : AppCompatActivity() {
    var dataItem = DataItem()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_notif)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Notification"

        getData()


    }

    fun getData() {
        val progressDialog = ProgressDialog(this)
        progressDialog.show()
        progressDialog.setTitle("Loading....")
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataYoutube("1DbFhYDUJtScnC6ksqE9SHLnuH-HGlsAd-9seczTaNy4", "Sheet1")
        call.enqueue(object : Callback<ResponseYoutube> {
            override fun onFailure(call: Call<ResponseYoutube>, t: Throwable) {
                Log.e("TAG", "error youtube ${t.toString()}")
                progressDialog.dismiss()
                Snackbar.make(parent_web_notif, "Gagal dapatkan data", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseYoutube>, response: Response<ResponseYoutube>) {
                Log.e("TAG", "data youtube ${response.body()?.data?.get(0)?.image}")
                if (response.isSuccessful) {
                    progressDialog.dismiss()
                    supportActionBar?.title =response.body()?.data?.get(0)?.judul
                    try {
                        web_notif.webViewClient = WebViewClient()
                        web_notif.settings.javaScriptEnabled = true
                        web_notif.loadData(response.body()?.data?.get(0)?.url, "text/html", "UTF-8")
                    } catch (e: Exception) {
                        Snackbar.make(parent_web_notif, "Hanphone anda tidak di support", Snackbar.LENGTH_LONG).show()
                    }

                    if (response.body()?.data?.get(0)?.url.equals("")) {
                        img_notif.visibility = View.GONE
                    } else {
                        Glide.with(this@WebNotifActivity)
                                .load(response.body()?.data?.get(0)?.image)
                                .into(img_notif)
                    }

                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}
