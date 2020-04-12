package com.basbas.lawanqfid.utama.ui.fragment.bantuan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_bantuan.*

/**
 * A simple [Fragment] subclass.
 */
class BantuanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bantuan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionBtn()
    }

    private fun actionBtn() {
        tv_web.setOnClickListener {
            startActivity(Intent(activity,WebActivity::class.java))
        }
    }
}