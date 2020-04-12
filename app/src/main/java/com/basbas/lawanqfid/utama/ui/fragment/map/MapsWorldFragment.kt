package com.basbas.lawanqfid.utama.ui.fragment.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.databinding.ActivityDashboardBinding

import com.basbas.lawanqfid.ui.adapter.ItemTypeFactoryImpl
import com.basbas.lawanqfid.ui.adapter.VisitableRecyclerAdapter
import com.basbas.lawanqfid.ui.adapter.viewholders.DailyItem
import com.basbas.lawanqfid.ui.adapter.viewholders.ErrorStateItem
import com.basbas.lawanqfid.ui.adapter.viewholders.OverviewItem
import com.basbas.lawanqfid.ui.adapter.viewholders.TextItem
import com.basbas.lawanqfid.ui.base.BaseFragment
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.ui.dailygraph.DailyGraphActivity
import com.basbas.lawanqfid.ui.detail.DetailActivity
import com.basbas.lawanqfid.ui.overview.DashboardViewModel
import com.basbas.lawanqfid.util.CaseType
import com.basbas.lawanqfid.util.ext.observe
import org.koin.android.viewmodel.ext.android.viewModel


class MapsWorldFragment : BaseFragment() {

    private val viewModel by viewModel<DashboardViewModel>()
    private val dailyAdapter by lazy {
        VisitableRecyclerAdapter(
                ItemTypeFactoryImpl(),
                ::onItemClicked
        )
    }

    private lateinit var binding: ActivityDashboardBinding

    override fun observeChange() {
        observe(viewModel.loading, ::handleLoading)
        observe(viewModel.items, ::onDataLoaded)
        observe(viewModel.toastMessage, ::showSnackbarMessage)
    }

    override fun onCreateView(

            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onResume() {
        super.onResume()
        viewModel.loadDashboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = dailyAdapter
            recyclerView.setHasFixedSize(true)

            swipeRefresh.setOnRefreshListener { viewModel.loadDashboard() }
            fab.setOnClickListener {
                DetailActivity.startActivity(context,CaseType.FULL)
            }
        }
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
                    R.id.layout_active -> { DetailActivity.startActivity(context,CaseType.CONFIRMED) }
                    R.id.layout_recovered -> {DetailActivity.startActivity(context,CaseType.RECOVERED)}
                    R.id.layout_death -> {DetailActivity.startActivity(context,CaseType.DEATHS)}
                }
            }
            is DailyItem -> {
                Log.e("DailyItem", "DailyItem Click: ${viewItem.deltaConfirmed}")
            }
//            is PerCountryItem -> {
//                /*Assuming every local country data has different API, so we provide dedicated activity,
//                * But with reusable components*/
//                when (viewItem.id) {
//                    PerCountryItem.ID -> CountryIndonesiaActivity.startActivity(activity)
//                }
//            }
            is TextItem -> {
                DailyGraphActivity.startActivity(activity)
            }
            is ErrorStateItem -> {
                viewModel.loadDashboard()
            }
        }
    }

    private fun permission(state: Int) {
        permission {
            DetailActivity.startActivity(context,state)
        }
    }

    private fun permission(state: () -> Unit?) {

    }

}