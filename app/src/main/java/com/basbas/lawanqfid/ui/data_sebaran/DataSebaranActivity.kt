package com.basbas.lawanqfid.ui.data_sebaran

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.ui.DataDuniaActivity
import com.basbas.lawanqfid.ui.DataProvinsiActivity
import kotlinx.android.synthetic.main.activity_data_sebaran.*

class DataSebaranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sebaran)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail"

        actionBtn()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionBtn() {
        btn_seluruh_negara.setOnClickListener {
            startActivity(Intent(this, DataDuniaActivity::class.java))
        }

        btn_seluruh_provinsi.setOnClickListener {
            startActivity(Intent(this, DataProvinsiActivity::class.java))
        }

        btn_provinsi_gorontalo.setOnClickListener {
            startActivity(Intent(this, DataGorontaloActivity::class.java))
        }
    }
}
