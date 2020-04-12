package com.basbas.lawanqfid.utama.model.youtube

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItemYoutube(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) : Parcelable