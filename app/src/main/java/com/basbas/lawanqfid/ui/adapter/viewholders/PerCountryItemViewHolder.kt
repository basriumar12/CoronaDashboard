package com.basbas.lawanqfid.ui.adapter.viewholders

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.databinding.ItemPerCountryBinding
import com.basbas.lawanqfid.ui.adapter.BaseViewHolder
import com.basbas.lawanqfid.ui.base.BaseViewItem
import com.basbas.lawanqfid.util.ext.getString

data class PerCountryItem(
    val id: Int,
    @StringRes val country: Int,
    val source: String,
    @DrawableRes val icon: Int
) : BaseViewItem {

    companion object {
        /*Generated country ID for identifier*/
        const val ID = 1
        const val MY = 2
        const val UK = 3
    }
}

class PerCountryViewHolder(itemView: View) : BaseViewHolder<PerCountryItem>(itemView) {
    private val binding: ItemPerCountryBinding = ItemPerCountryBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.root.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: PerCountryItem) {
        with(binding) {
            txtCountry.text = getString(item.country)
            txtInformation.text = itemView.context.getString(R.string.source, item.source)
            imgIcon.setImageDrawable(itemView.context.resources.getDrawable(item.icon))
        }
    }


    companion object {
        const val LAYOUT = R.layout.item_per_country
    }
}