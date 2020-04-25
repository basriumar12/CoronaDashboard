package com.basbas.lawanqfid.utama.ui.fragment.bantuan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.ui.bantu.BantuActivity
import kotlinx.android.synthetic.main.fragment_bantuan.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

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
        parent_bantu.setOnClickListener {
            startActivity(Intent(activity,BantuActivity::class.java))
        }

        parent_call.setOnClickListener {
            Call()
        }
        btn_panggil.setOnClickListener {
            Call()
        }

    }

    @AfterPermissionGranted(123)
    private fun Call() {
        val perms =
                arrayOf<String>(Manifest.permission.CALL_PHONE)
        if (context?.let {
                    EasyPermissions.hasPermissions(
                            it,
                            *perms
                    )
                }!!) { // Already have permission, do the thing
            if (context?.let {
                        ActivityCompat.checkSelfPermission(
                                it,
                                Manifest.permission.CALL_PHONE
                        )
                    } != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        context as Activity, arrayOf(Manifest.permission.CALL_PHONE),
                        3
                )
            } else {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:119")))
            }
        } else { // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    this, "we need permission",
                    123, *perms
            )
        }
    }
}