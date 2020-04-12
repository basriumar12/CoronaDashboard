package com.basbas.lawanqfid.utama.base

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        mainViewModel.getType().observe(this, Observer { typeModel ->
            if (typeModel.connect.equals(false)){
               // showErrorMessage("tidak connect internet")
                dialog()
            }

        })

    }

    fun dialog (){
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Internet anda sedang tidak aktif, aktifkan internet segera !")
        dialog.setPositiveButton("OK"){dialog, which ->
            finish()

        }
        dialog.show()


        }

    }
    class MainViewModel(application: Application) : AndroidViewModel(application) {
        private val disposable = CompositeDisposable()

        override fun onCleared() {
            super.onCleared()
            disposable.clear()
        }

        private val isConnected: MutableLiveData<Boolean> = MutableLiveData()
        private val connectivityTypeName: MutableLiveData<String> = MutableLiveData()

        private val typeModel: MutableLiveData<TypeModel> = MutableLiveData()

        init {
            val rxNetwork = ReactiveNetwork.observeNetworkConnectivity(application)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { connect ->
                        isConnected.postValue(connect.available())
                        connectivityTypeName.postValue(connect.typeName())

                        val type = TypeModel(connect.available(), connect.typeName())
                        typeModel.postValue(type)
                    }

            disposable.add(rxNetwork)
        }


        fun getListenerNetwork() = isConnected
        fun getTypeName() = connectivityTypeName
        fun getType() = typeModel
    }

    data class TypeModel(val connect: Boolean,
                         val connectType: String)

    fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


