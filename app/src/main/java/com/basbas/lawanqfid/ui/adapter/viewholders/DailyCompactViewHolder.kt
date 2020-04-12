package com.basbas.lawanqfid.ui.adapter.viewholders

import android.view.View
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.databinding.ItemDailyCompactBinding
import com.basbas.lawanqfid.ui.adapter.BaseViewHolder
import com.basbas.lawanqfid.util.NumberUtils

class DailyCompactViewHolder(itemView: View) : BaseViewHolder<DailyItem>(itemView) {

    private val binding = ItemDailyCompactBinding.bind(itemView)

    override fun bind(item: DailyItem) {
        with(binding) {
            txtDate.text = NumberUtils.formatStringDate(item.reportDate)

            root.context?.let {
                txtConfirmed.text = it.getString(R.string.confirmed_case_count, NumberUtils.numberFormat(item.deltaConfirmed))
                txtRecovered.text = it.getString(R.string.recovered_case_count, NumberUtils.numberFormat(item.deltaRecovered))
            }
        }
    }

    override fun setOnClickListener(listener: (View) -> Unit) {
        // no op
    }

    companion object {
        val LAYOUT = R.layout.item_daily_compact
    }
}