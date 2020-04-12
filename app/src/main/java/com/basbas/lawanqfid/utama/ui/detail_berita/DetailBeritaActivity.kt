package com.basbas.lawanqfid.utama.ui.detail_berita

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.berita.ResponseBerita
import com.basbas.lawanqfid.utama.network.ApiInterface
import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.adapter.AdapterBerita
import kotlinx.android.synthetic.main.activity_detail_berita.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailBeritaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_berita)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Informasi Dan Tips"

        rv_detail_berita?.layoutManager = LinearLayoutManager(this)
        getBerita()
    }

    private fun getBerita() {
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataBerita("1pbg0fV8rYGKc2tYSL04un80zvTg7vvYLlB4yk-PjQkQ", "1")
        call.enqueue(object : Callback<ResponseBerita> {
            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {
                Toast.makeText(this@DetailBeritaActivity,"Data gagal di dapatkan", Toast.LENGTH_LONG).show()
                progress_circular_berita.visibility = View.GONE
            }

            override fun onResponse(call: Call<ResponseBerita>, response: Response<ResponseBerita>) {
                progress_circular_berita.visibility = View.GONE
                Log.e("TAG","data berita ${response.body()?.data?.get(0)?.url_image}")
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        var adapterBerita = AdapterBerita(data, this@DetailBeritaActivity)
                        rv_detail_berita?.adapter = adapterBerita
                        adapterBerita.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
