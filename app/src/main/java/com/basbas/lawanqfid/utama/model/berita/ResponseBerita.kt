package com.basbas.lawanqfid.utama.model.berita

import com.google.gson.annotations.SerializedName

data class ResponseBerita(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)