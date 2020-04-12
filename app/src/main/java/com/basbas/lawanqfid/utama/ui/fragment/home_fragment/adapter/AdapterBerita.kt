package com.basbas.lawanqfid.utama.ui.fragment.home_fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basbas.lawanqfid.R
import com.basbas.lawanqfid.utama.model.berita.DataItem
import com.basbas.lawanqfid.utama.ui.web.WebBeritaActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_berita.view.*

class AdapterBerita(private val itemData: List<DataItem>, private val contex: Context)
    : RecyclerView.Adapter<AdapterBerita.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_berita, parent, false))

    override fun getItemCount(): Int {
        return itemData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tv_title_berita.text = itemData.get(position).nama_berita
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.check)
                .error(R.drawable.check)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
        Glide.with(contex).load(itemData.get(position).url_image)
                .apply(options)
                .into(holder.itemView.img_berita)

//        Glide.with(contex).load(itemcart.get(position).foto)
//            .apply(options)
//            .into(holder.image)


        holder.itemView.setOnClickListener {

            //  contex.startActivity()
            val intent = Intent(contex, WebBeritaActivity::class.java)
            intent.putExtra("DATA", itemData[position])
            contex.startActivity(intent)


        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}