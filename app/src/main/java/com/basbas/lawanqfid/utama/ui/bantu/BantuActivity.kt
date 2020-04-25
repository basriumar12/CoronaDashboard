package com.basbas.lawanqfid.utama.ui.bantu

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.youtube.ResponseYoutube
import com.basbas.lawanqfid.utama.network.ApiInterface
import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_bantu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BantuActivity : AppCompatActivity() {
    var adapterBantu : AdapterBantu? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bantu)
        rv_bantu?.layoutManager = LinearLayoutManager(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title ="Bantu Yang Terkena Dampak Covid-19"
        getData()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        progress_circular_bantu.visibility = View.VISIBLE
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataYoutube("1JcPX_LzhHNDfNl37hhGCO57efhoho4J6K0q00UyJSFg", "Sheet1")
        call.enqueue(object : Callback<ResponseYoutube> {
            override fun onFailure(call: Call<ResponseYoutube>, t: Throwable) {
                Log.e("TAG","error bantuan ${t.toString()}")
                progress_circular_bantu.visibility = View.GONE
                Snackbar.make(parent_bantu,"Gagal dapatkan data", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseYoutube>, response: Response<ResponseYoutube>) {
                Log.e("TAG","data bantuan ${response.body()?.data?.get(0)?.image}")
                if (response.isSuccessful) {
                    progress_circular_bantu.visibility = View.GONE
                    val data = response.body()?.data
                    if (data != null) {
                        adapterBantu =  AdapterBantu(data, this@BantuActivity)
                        rv_bantu?.adapter = adapterBantu
                        adapterBantu?.notifyDataSetChanged()
                    }
                }
            }
        })
        

    }
}
