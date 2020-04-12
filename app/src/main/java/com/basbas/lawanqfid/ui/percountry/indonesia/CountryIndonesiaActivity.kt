package com.basbas.lawanqfid.ui.percountry.indonesia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.basbas.lawanqfid.databinding.ActivityCountryIndonesiaBinding
import com.basbas.lawanqfid.ui.adapter.ItemTypeFactoryImpl
import com.basbas.lawanqfid.ui.adapter.VisitableRecyclerAdapter
import com.basbas.lawanqfid.ui.adapter.viewholders.DailyItem
import com.basbas.lawanqfid.ui.adapter.viewholders.TextItem
import com.basbas.lawanqfid.ui.base.BaseActivity
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.ui.dailygraph.DailyGraphActivity
import com.basbas.lawanqfid.util.ext.observe
import org.koin.android.viewmodel.ext.android.viewModel

class CountryIndonesiaActivity : BaseActivity() {
    private val viewModel by viewModel<CountryIndonesiaViewModel>()
    private lateinit var binding: ActivityCountryIndonesiaBinding
    private val viewAdapter by lazy {
        VisitableRecyclerAdapter(
            ItemTypeFactoryImpl(),
            ::onItemClicked
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryIndonesiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Data Indonesia"
        //setupActionBarWithBackButton(binding.toolbar)
        initView()

        viewModel.loadData()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        with(binding.recyclerView) {
            adapter = viewAdapter
            setHasFixedSize(true)
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadData()
        }
    }

    override fun observeChange() {
        observe(viewModel.items, ::onDataLoaded)
        observe(viewModel.toastMessage, ::showSnackbarMessage)
        observe(viewModel.loading, ::loadingSwipeRefresh)
    }

    private fun loadingSwipeRefresh(loading: Boolean) {
        with(binding.swipeRefresh) {
            post {
                isRefreshing = loading
            }
        }
    }

    private fun onDataLoaded(items: List<BaseViewItem>) {
        viewAdapter.submitList(items)
    }


    private fun onItemClicked(viewItem: BaseViewItem, view: View) {
        when (viewItem) {
            is DailyItem -> {

            }
            is TextItem -> {
                DailyGraphActivity.startActivity(this)
            }
        }
    }


    companion object {
        @JvmStatic
        fun startActivity(context: Context?) =
            context?.startActivity(Intent(context, CountryIndonesiaActivity::class.java))
    }
}
