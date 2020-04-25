package com.basbas.lawanqfid.utama.pref

import android.content.Context
import android.content.SharedPreferences

class UserSession(private val context: Context) {

    val sp: SharedPreferences = context.getSharedPreferences("USER_SESSION",0)
    val edit:SharedPreferences.Editor = sp.edit()

    fun makeSession(userName:String?){
        edit.putString("userName",userName)
        edit.commit()
    }

    fun hasSession() : Boolean{
        return if(sp.getString("userName",null).isNullOrBlank()){
            false
        }else{
            true
        }
    }

    fun destroySession(){
        edit.clear()
        edit.commit()
    }

    fun getUserName() : String?{
        return sp.getString("userName",null)
    }

}