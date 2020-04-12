package com.basbas.lawanqfid.utama.model.berita

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(

	val jsonMember: String? = null,

	val nama_berita: String? = null,

	val keterangan: String? = null,

	val sumber: String? = null,
	val tanggal: String? = null,

	val timestamp: String? = null,

	val isi_berita: String? = null,

	val url_image: String? = null
) : Parcelable