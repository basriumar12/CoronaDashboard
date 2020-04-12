package com.basbas.lawanqfid.utama.ui.data_sebaran

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.network.ApiInterface
import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.model.ResponseDataFromSpreadSheet
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_data_gorontalo.*
import kotlinx.android.synthetic.main.item_corona_meninggal.*
import kotlinx.android.synthetic.main.item_corona_odp.*
import kotlinx.android.synthetic.main.item_corona_pdp.*
import kotlinx.android.synthetic.main.item_corona_positif.*
import kotlinx.android.synthetic.main.item_corona_sembuh.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DataGorontaloActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_gorontalo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Gorontalo"
        getData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getData() {
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataFromSpreadsheet("1zPnkFowaQVUqdNQElbrP9SgDagWPZ7R26mQbXWukKj8","Sheet1")
        call.enqueue(object : Callback<ResponseDataFromSpreadSheet> {
            override fun onFailure(call: Call<ResponseDataFromSpreadSheet>?, t: Throwable?) {
                Log.e("TAG","ERRROR ${t.toString()}")
                Snackbar.make(rootLayout,"gagal dapatkan data", Snackbar.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call<ResponseDataFromSpreadSheet>?, response: Response<ResponseDataFromSpreadSheet>?) {
                Log.e("TAG","data ${response?.body()?.data?.get(0)?.nama}")
                if (response != null) {
                    if (response.isSuccessful){
                        tv_positif.text = response.body()?.data?.get(1)?.positif.toString().replace("A","")
                        if(!response.body()?.data?.get(1)?.tambah_positif.equals("")) {
                            tv_tambah_positif.text = "+" + response.body()?.data?.get(1)?.tambah_positif.toString().replace("A", "")

                        } else{
                            tv_tambah_positif.text = ""
                        }

                        tv_sembuh.text = response.body()?.data?.get(1)?.sembuh.toString().replace("A","")
                        tv_odp.text = response.body()?.data?.get(1)?.odp.toString().replace("A","")
                        tv_pdp.text = response.body()?.data?.get(1)?.pdp.toString().replace("A","")
                        tv_meninggal.text = response.body()?.data?.get(1)?.meninggal.toString().replace("A","")
                        val date: String = response.body()?.data?.get(1)?.tanggal.toString()

                        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val formatterEvent = SimpleDateFormat("EEEE, dd MMM yyy", Locale("ID"))
                        val dateStart = formatter.parse(date)
                        val dateStartEvent = formatterEvent.format(dateStart)
                        tv_last_update.text = "Di update "+dateStartEvent


                    }
                } else{

                    Snackbar.make(rootLayout,"gagal dapatkan data", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
