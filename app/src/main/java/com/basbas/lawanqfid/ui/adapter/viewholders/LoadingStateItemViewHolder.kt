package com.basbas.lawanqfid.ui.adapter.viewholders

import android.view.View
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.ui.adapter.BaseViewHolder
import com.basbas.lawanqfid.ui.base.BaseViewItem



class LoadingStateItem: BaseViewItem

class LoadingStateItemViewHolder(itemView: View) : BaseViewHolder<LoadingStateItem>(itemView) {

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
    }

    override fun bind(item: LoadingStateItem) {

    }

    companion object {
        const val LAYOUT = R.layout.item_loading_state
    }
}