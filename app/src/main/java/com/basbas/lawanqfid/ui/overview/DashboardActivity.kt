package com.basbas.lawanqfid.ui.overview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.databinding.ActivityDashboardBinding
import com.basbas.lawanqfid.ui.adapter.ItemTypeFactoryImpl
import com.basbas.lawanqfid.ui.adapter.VisitableRecyclerAdapter
import com.basbas.lawanqfid.ui.adapter.viewholders.*
import com.basbas.lawanqfid.ui.base.BaseActivity
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.ui.detail.DetailActivity
import com.basbas.lawanqfid.ui.percountry.indonesia.CountryIndonesiaActivity
import com.basbas.lawanqfid.util.CaseType
import com.basbas.lawanqfid.util.ext.observe
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardActivity : BaseActivity() {

        private val viewModel by viewModel<DashboardViewModel>()
        private val dailyAdapter by lazy {
            VisitableRecyclerAdapter(
                ItemTypeFactoryImpl(),
                ::onItemClicked
            )
        }

        private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDashboard()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        with(binding) {
            recyclerView.adapter = dailyAdapter
            recyclerView.setHasFixedSize(true)

            swipeRefresh.setOnRefreshListener { viewModel.loadDashboard() }
            fab.setOnClickListener { permission(CaseType.FULL) }
        }
    }

    private fun permission(state: Int) {
        permission { DetailActivity.startActivity(this, state) }
    }

    override fun observeChange() {
        observe(viewModel.loading, ::handleLoading)
        observe(viewModel.items, ::onDataLoaded)
        observe(viewModel.toastMessage, ::showSnackbarMessage)
    }

    private fun handleLoading(status: Boolean) {
        binding.swipeRefresh.isRefreshing = status
    }

    private fun onDataLoaded(items: List<BaseViewItem>) {
        dailyAdapter.submitList(items)
    }

    private fun onItemClicked(viewItem: BaseViewItem, view: View) {
        when (viewItem) {
            is OverviewItem -> {
                when (view.id) {
                    R.id.layout_active -> permission(CaseType.CONFIRMED)
                    R.id.layout_recovered -> permission(CaseType.RECOVERED)
                    R.id.layout_death -> permission(CaseType.DEATHS)
                }
            }
            is DailyItem -> {
                Log.e("DailyItem", "DailyItem Click: ${viewItem.deltaConfirmed}")
            }
            is PerCountryItem -> {
                /*Assuming every local country data has different API, so we provide dedicated activity,
                * But with reusable components*/
                when (viewItem.id) {
                    PerCountryItem.ID -> CountryIndonesiaActivity.startActivity(this)
                }
            }
            is TextItem -> {
                //DailyGraphActivity.startActivity(this)
            }
            is ErrorStateItem -> {
                viewModel.loadDashboard()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                when (item.itemId) {
                    R.id.action_update -> getString(R.string.update_url)
                    else -> getString(R.string.feedback_url)
                }
            )
        )
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}
