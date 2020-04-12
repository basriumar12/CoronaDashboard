package com.basbas.lawanqfid.utama.ui.fragment.map

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.databinding.ActivityDetailBinding
import com.basbas.lawanqfid.ui.adapter.ItemTypeFactoryImpl
import com.basbas.lawanqfid.ui.adapter.VisitableRecyclerAdapter
import com.basbas.lawanqfid.ui.adapter.viewholders.LocationItem
import com.basbas.lawanqfid.ui.base.BaseFragment
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.ui.detail.DetailViewModel
import com.basbas.lawanqfid.ui.maps.VisualMapsFragment
import com.basbas.lawanqfid.util.CaseType
import com.basbas.lawanqfid.util.ext.observe
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jakewharton.rxbinding.widget.RxTextView
import org.koin.android.viewmodel.ext.android.viewModel
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class MapFragment : BaseFragment() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModel<DetailViewModel>()
    private var mapsFragment: VisualMapsFragment? = null
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ViewGroup>
    private val bottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            //nothing
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                hideSoftKeyboard()
                binding.txtSearch.clearFocus()
            } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
        }
    }

    private val detailAdapter by lazy {
        VisitableRecyclerAdapter(ItemTypeFactoryImpl(), ::onItemClick, ::onLongItemClick)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        initView()
        attachMaps()

        viewModel.getDetail(CaseType.FULL)
    }

    private fun initView() {
        with(binding) {
            recyclerView.adapter = detailAdapter
            bottomSheetBehaviour = BottomSheetBehavior.from(layoutBottomSheet)
            bottomSheetBehaviour.setBottomSheetCallback(bottomSheetBehaviorCallback)

            fabBack.setOnClickListener {
                //onBackPressed()
            }
            txtSearch.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) expandBottomSheet() }
            imgClear.setOnClickListener { txtSearch.setText("") }
        }
        RxTextView.textChanges(binding.txtSearch)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.imgClear.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
                    viewModel.findLocation(it.toString())
                }
    }
    override fun observeChange() {
        //observe(viewModel.loading, ::loading)
        observe(viewModel.errorMessage, ::showSnackbarMessage)
        observe(viewModel.detailListViewItems, ::onListChanged)
    }

    private fun onListChanged(data: List<BaseViewItem>) {
        detailAdapter.submitList(data)
    }


    private fun attachMaps() {
        mapsFragment = VisualMapsFragment.newInstance(3)
        mapsFragment?.let {
            childFragmentManager.beginTransaction().replace(R.id.layout_visual, it)
                    .commitAllowingStateLoss()
        }
    }

    private fun showItemListDialog(item: LocationItem) {
        val items = resources.getStringArray(R.array.detail_item_menu).toMutableList()

        if (item.isPinned.not()) {
            AlertDialog.Builder(activity)
                    .setItems(items.toTypedArray()) { dialog, which ->
                        viewModel.putPinnedRegion(item.compositeKey())
                    }
                    .show()
        }
    }

    private fun onLongItemClick(item: BaseViewItem, view: View) {
        when (item) {
            is LocationItem -> {
                showItemListDialog(item)
            }
        }
    }

    override fun onCreateView(

            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun onItemClick(item: BaseViewItem, view: View) {
        when (item) {
            is LocationItem -> {
                when (view.id) {
                    R.id.root -> {
                        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                        view.postDelayed({
                            hideSoftKeyboard()
                            collapseBottomSheet()
                            mapsFragment?.selectItem(item)
                        }, 100)
                    }
                    R.id.relative_pinned -> {
                        viewModel.removePinnedRegion()
                    }
                }
            }
        }
    }

    private fun collapseBottomSheet() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun expandBottomSheet() {
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }



}