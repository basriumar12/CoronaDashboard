package com.basbas.lawanqfid.ui.adapter.viewholders

import android.view.View
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.databinding.ItemPinnedBinding
import com.basbas.lawanqfid.ui.adapter.BaseViewHolder
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.util.NumberUtils

data class PinnedItem(
    val confirmed: Int? = null,
    val recovered: Int? = null,
    val deaths: Int? = null,
    val locationName: String,
    val lastUpdate: Long
): BaseViewItem

class PinnedItemViewHolder(itemView: View) : BaseViewHolder<PinnedItem>(itemView) {
    private val binding: ItemPinnedBinding = ItemPinnedBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        //Listener
    }

    override fun bind(item: PinnedItem) {
        with(binding) {
            val lastUpdate = NumberUtils.formatTime(item.lastUpdate)
            txtLocation.text = item.locationName
            txtUpdate.text = itemView.context.getString(R.string.information_last_update, lastUpdate)
            txtData.text = NumberUtils.numberFormat(item.confirmed)
            txtRcv.text = NumberUtils.numberFormat(item.recovered)
            txtDeath.text = NumberUtils.numberFormat(item.deaths)
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_pinned
    }
}