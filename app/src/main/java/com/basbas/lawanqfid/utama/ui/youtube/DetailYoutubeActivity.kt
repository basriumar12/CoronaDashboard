package com.basbas.lawanqfid.utama.ui.youtube

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
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.adapter.AdapterYoutube
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail_youtube.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailYoutubeActivity : AppCompatActivity() {

    var adapterYoutube : AdapterYoutube? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_youtube)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title ="Video"

        rv_youtube_detail.layoutManager = LinearLayoutManager(this)
        getYoutube()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    fun getYoutube(){
        progress_circular_youtube.visibility = View.VISIBLE
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataYoutube("1--m45_J8JbHTZJSCp8cjN_p3anQfQh4_mhpL6GDS8L4", "Sheet1")
        call.enqueue(object : Callback<ResponseYoutube> {
            override fun onFailure(call: Call<ResponseYoutube>, t: Throwable) {
                Log.e("TAG","error youtube ${t.toString()}")
                progress_circular_youtube.visibility = View.GONE
               Snackbar.make(parent_youtube,"Gagal dapatkan data", Snackbar.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseYoutube>, response: Response<ResponseYoutube>) {
                Log.e("TAG","data youtube ${response.body()?.data?.get(0)?.image}")
                if (response.isSuccessful) {
                    progress_circular_youtube.visibility = View.GONE
                    val data = response.body()?.data
                    if (data != null) {
                        adapterYoutube =  AdapterYoutube(data, this@DetailYoutubeActivity)
                        rv_youtube_detail?.adapter = adapterYoutube
                        adapterYoutube?.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
