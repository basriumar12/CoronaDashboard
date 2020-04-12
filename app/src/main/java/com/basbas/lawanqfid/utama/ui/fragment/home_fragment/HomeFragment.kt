package com.basbas.lawanqfid.utama.ui.fragment.home_fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.ui.percountry.indonesia.CountryIndonesiaActivity
import com.basbas.lawanqfid.utama.model.berita.ResponseBerita
import com.basbas.lawanqfid.utama.network.ApiInterface
import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet
import com.basbas.lawanqfid.utama.ui.data_sebaran.DataSebaranActivity
import com.basbas.lawanqfid.utama.ui.detail_berita.DetailBeritaActivity
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.adapter.AdapterBerita
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.model.ResponseDataFromSpreadSheet
import com.basbas.lawanqfid.utama.ui.web.WebActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_corona_meninggal.*
import kotlinx.android.synthetic.main.item_corona_positif.*
import kotlinx.android.synthetic.main.item_corona_sembuh.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {


     var adapterBerita : AdapterBerita? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getData()
        greetings()
        actionBtn()
        getBerita()

        swipe?.setOnRefreshListener {
            getData()
            getBerita()
            tv_sembuh?.text = "Loading..."
            tv_positif?.text = "Loading..."
            tv_last_update?.text = "Loading..."
            tv_meninggal?.text = "Loading..."
        }
    }

    private fun initView() {
        rv_berita?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
    }

    private fun actionBtn() {
        tv_lihat_semua_berita.setOnClickListener {
            startActivity(Intent(activity, DetailBeritaActivity::class.java))
        }

        btn_seluruh_provinsi.setOnClickListener {
            startActivity(Intent(activity, WebActivity::class.java))
        }

        tv_lihat_detail.setOnClickListener {
            //DetailActivity.startActivity(context, CaseType.FULL)
            CountryIndonesiaActivity.startActivity(activity)
            // startActivity(Intent(activity,DataSebaranActivity::class.java))
        }
        parent_indonesia_title.setOnClickListener {
            startActivity(Intent(activity, DataSebaranActivity::class.java))
        }
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

    private fun getBerita() {
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataBerita("1pbg0fV8rYGKc2tYSL04un80zvTg7vvYLlB4yk-PjQkQ", "1")
        call.enqueue(object : Callback<ResponseBerita> {
            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBerita>, response: Response<ResponseBerita>) {

                Log.e("TAG","data berita ${response.body()?.data?.get(0)?.url_image}")
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        activity?.let { adapterBerita =  AdapterBerita(data, it) }
                        rv_berita?.adapter = adapterBerita
                        adapterBerita?.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun getData() {
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataFromSpreadsheet("1zPnkFowaQVUqdNQElbrP9SgDagWPZ7R26mQbXWukKj8", "Sheet1")
        call.enqueue(object : Callback<ResponseDataFromSpreadSheet> {
            override fun onFailure(call: Call<ResponseDataFromSpreadSheet>?, t: Throwable?) {
                Log.e("TAG", "ERRROR ${t.toString()}")
                Snackbar.make(rootLayout, "gagal dapatkan data", Snackbar.LENGTH_LONG).show()
                swipe?.isRefreshing = false
            }

            override fun onResponse(call: Call<ResponseDataFromSpreadSheet>?, response: Response<ResponseDataFromSpreadSheet>?) {
                Log.e("TAG", "data ${response?.body()?.data?.get(0)?.nama}")
                if (response != null) {
                    if (response.isSuccessful) {
                        swipe?.isRefreshing = false
                        tv_positif?.text = response.body()?.data?.get(0)?.positif.toString().replace("A", "")
                        if (!response.body()?.data?.get(0)?.tambah_positif.equals("")) {
                            tv_tambah_positif?.text = "+" + response.body()?.data?.get(0)?.tambah_positif.toString().replace("A", "")

                        } else {
                            tv_tambah_positif?.text = ""
                        }

                        tv_sembuh?.text = response.body()?.data?.get(0)?.sembuh.toString().replace("A", "")
                        tv_meninggal?.text = response.body()?.data?.get(0)?.meninggal.toString().replace("A", "")
                        val date: String = response.body()?.data?.get(0)?.tanggal.toString()

                        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val formatterEvent = SimpleDateFormat("EEEE, dd MMM yyy", Locale("ID"))
                        val dateStart = formatter.parse(date)
                        val dateStartEvent = formatterEvent.format(dateStart)
                        tv_last_update?.text = "Di update " + dateStartEvent


                    }
                } else {
                    swipe?.isRefreshing = false
                    Snackbar.make(rootLayout, "gagal dapatkan data", Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}