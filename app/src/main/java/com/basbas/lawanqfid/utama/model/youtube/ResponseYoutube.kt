package com.basbas.lawanqfid.utama.model.youtube

import com.google.gson.annotations.SerializedName

data class ResponseYoutube(

        @field:SerializedName("data")
	val data: List<DataItemYoutube>? = null,

        @field:SerializedName("status")
	val status: String? = null
)