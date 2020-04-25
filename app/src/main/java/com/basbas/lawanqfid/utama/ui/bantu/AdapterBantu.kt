package com.basbas.lawanqfid.utama.ui.bantu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.youtube.DataItemYoutube
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_berita.view.img_berita
import kotlinx.android.synthetic.main.item_berita.view.tv_title_berita
import kotlinx.android.synthetic.main.item_youtube.view.*


class AdapterBantu(private val itemData: List<DataItemYoutube>, private val contex: Context)
    : RecyclerView.Adapter<AdapterBantu.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_youtube, parent, false))

    override fun getItemCount(): Int {
        return itemData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tv_title_berita.text = itemData.get(position).judul
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.check)
                .error(R.drawable.check)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
        Glide.with(contex).load(itemData.get(position).image)
                .apply(options)
                .into(holder.itemView.img_berita)

        Log.e("TAG","image ${itemData.get(0).image}")
        holder.itemView.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(itemData.get(position).url))
                contex.startActivity(intent)
            } catch (e: Exception) {

            }


        }

        holder.itemView.btn_youtube.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(itemData.get(position).url))
                contex.startActivity(intent)
            } catch (e: Exception) {

            }

        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}