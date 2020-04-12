package com.basbas.lawanqfid.utama.ui.fragment.home_fragment.model

import com.google.gson.annotations.SerializedName

data class ResponseDataFromSpreadSheet(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)