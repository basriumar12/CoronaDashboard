package com.basbas.lawanqfid.utama.ui.fragment.home_fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.ui.percountry.indonesia.CountryIndonesiaActivity
import com.basbas.lawanqfid.utama.model.berita.ResponseBerita
import com.basbas.lawanqfid.utama.model.youtube.ResponseYoutube
import com.basbas.lawanqfid.utama.network.ApiInterface
import com.basbas.lawanqfid.utama.network.ApiServiceFromSpreadsheet
import com.basbas.lawanqfid.utama.pref.UserSession
import com.basbas.lawanqfid.utama.ui.data_sebaran.DataSebaranActivity
import com.basbas.lawanqfid.utama.ui.detail_berita.DetailBeritaActivity
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.adapter.AdapterBerita
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.adapter.AdapterYoutube
import com.basbas.lawanqfid.utama.ui.fragment.home_fragment.model.ResponseDataFromSpreadSheet
import com.basbas.lawanqfid.utama.ui.setting.SettingActivity
import com.basbas.lawanqfid.utama.ui.web.WebActivity
import com.basbas.lawanqfid.utama.ui.youtube.DetailYoutubeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_corona_meninggal.*
import kotlinx.android.synthetic.main.item_corona_positif.*
import kotlinx.android.synthetic.main.item_corona_sembuh.*
import kotlinx.android.synthetic.main.item_insert_name.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

        var name = ""
     var adapterBerita : AdapterBerita? = null
    var adapterYoutube : AdapterYoutube? =null

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
        getYoutube()

        if (!activity?.let { UserSession(it).hasSession() }!!){
            insertYourName()
        }
        swipe?.setOnRefreshListener {
            rv_youtube?.visibility = View.GONE
            rv_berita?.visibility = View.GONE
            getData()
            getYoutube()
            getBerita()
            tv_sembuh?.text = "Loading..."
            tv_positif?.text = "Loading..."
            tv_last_update?.text = "Loading..."
            tv_meninggal?.text = "Loading..."
        }
    }

    private fun initView() {
        rv_berita?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        rv_youtube?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
    }

    private fun actionBtn() {
        tv_lihat_semua_berita.setOnClickListener {
            startActivity(Intent(activity, DetailBeritaActivity::class.java))
        }
        img_setting.setOnClickListener {
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        tv_lihat_semua_youtube.setOnClickListener {
            startActivity(Intent(activity, DetailYoutubeActivity::class.java))
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
       val nameFromPref = activity?.let { UserSession(it).getUserName().toString() }.toString()
        if (nameFromPref.equals("null")){
           name =""

        } else{
            tv_lokasi.visibility = View.VISIBLE
          name = nameFromPref
        }

        val calendar: Calendar = Calendar.getInstance()
        val timeOfDay: Int = calendar.get(Calendar.HOUR_OF_DAY)

        if (timeOfDay >= 0 && timeOfDay < 12) {
            tv_greeting.setText("Selamat Pagi $name #DiRumahAJa")

        } else if (timeOfDay >= 12 && timeOfDay < 15) {
            tv_greeting.setText("Selamat Siang $name #DiRumahAJa")

        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            tv_greeting.setText("Selamat Sore $name #DiRumahAJa")

        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tv_greeting.setText("Selamat Malam $name #DiRumahAJa")
            tv_greeting.setTextColor(Color.WHITE)

        }
    }

   fun getYoutube(){
       rv_youtube?.visibility = View.VISIBLE

       loading_youtube?.visibility = View.VISIBLE
       val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
       val call = client.getDataYoutube("1--m45_J8JbHTZJSCp8cjN_p3anQfQh4_mhpL6GDS8L4", "Sheet1")
       call.enqueue(object :Callback<ResponseYoutube>{
           override fun onFailure(call: Call<ResponseYoutube>, t: Throwable) {
                Log.e("TAG","error youtube ${t.toString()}")
               loading_youtube?.visibility = View.GONE
           }

           override fun onResponse(call: Call<ResponseYoutube>, response: Response<ResponseYoutube>) {
               Log.e("TAG","data youtube ${response.body()?.data?.get(0)?.image}")
               if (response.isSuccessful) {
                   loading_youtube?.visibility = View.GONE
                   val data = response.body()?.data
                   if (data != null) {
                       activity?.let { adapterYoutube =  AdapterYoutube(data, it) }
                       rv_youtube?.adapter = adapterYoutube
                       adapterYoutube?.notifyDataSetChanged()
                   }
               }
           }
       })
    }
    private fun getBerita() {
        rv_berita?.visibility = View.VISIBLE
        loading_berita?.visibility = View.VISIBLE
        val client = ApiServiceFromSpreadsheet.createService(ApiInterface::class.java)
        val call = client.getDataBerita("1pbg0fV8rYGKc2tYSL04un80zvTg7vvYLlB4yk-PjQkQ", "1")
        call.enqueue(object : Callback<ResponseBerita> {
            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {
                loading_berita?.visibility = View.GONE
            }

            override fun onResponse(call: Call<ResponseBerita>, response: Response<ResponseBerita>) {

                Log.e("TAG","data berita ${response.body()?.data?.get(0)?.url_image}")
                if (response.isSuccessful) {
                    loading_berita?.visibility = View.GONE
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

    fun insertYourName (){
        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.item_insert_name, null)
        //AlertDialogBuilder
        val mBuilder = context?.let {
            AlertDialog.Builder(it)
                .setView(mDialogView)
                .setTitle("")
        }
        //show dialog
        val  mAlertDialog = mBuilder?.show()

        mDialogView.btn_simpan.setOnClickListener {
            val nameView = mDialogView.edt_nama.text.toString()
            activity?.let { UserSession(it).makeSession(nameView) }
            Log.e("TAG","name $nameView")
            greetings()
            mAlertDialog?.dismiss()
        }

        mDialogView.btn_cancel.setOnClickListener {
            mAlertDialog?.dismiss()
        }



    }
}