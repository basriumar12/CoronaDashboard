package com.basbas.lawanqfid.ui.base

import com.basbas.lawanqfid.ui.adapter.ItemTypeFactory

interface BaseViewItem {
    fun typeOf(itemFactory: ItemTypeFactory): Int = itemFactory.type(this)
}
