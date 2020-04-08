package com.basbas.lawanqfid.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basbas.lawanqfid.R;
import com.basbas.lawanqfid.model.provinsi.AttributesProvinsi;
import com.basbas.lawanqfid.model.provinsi.ResponseDataProvinsi;

import java.util.ArrayList;

public class AdapterDataProvinsi extends RecyclerView.Adapter<AdapterDataProvinsi.MyViewHolder> {
    private ArrayList<AttributesProvinsi> lisData;
    private OnItemClickCallback onItemClickCallback;
    public interface OnItemClickCallback {
        void onItemClicked(ResponseDataProvinsi data);
    }
    void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    AdapterDataProvinsi(ArrayList<AttributesProvinsi> list) {
        this.lisData = list;
    }
    @NonNull
    @Override
    public AdapterDataProvinsi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_provinsi, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataProvinsi.MyViewHolder holder, int position) {
//
        holder.tvSembuh.setText(""+lisData.get(position).getKasusSemb());
        holder.tvPositif.setText(""+lisData.get(position).getKasusPosi());
        holder.tvMeninggal.setText(""+lisData.get(position).getKasusMeni());
        holder.tvProvinsi.setText(lisData.get(position).getProvinsi());
    }

    @Override
    public int getItemCount() {
        return lisData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPositif, tvMeninggal, tvSembuh, tvProvinsi;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPositif = itemView.findViewById(R.id.tv_positif);
            tvProvinsi = itemView.findViewById(R.id.tv_provinsi);
            tvMeninggal = itemView.findViewById(R.id.tv_meninggal);
            tvSembuh = itemView.findViewById(R.id.tv_sembuh);
        }
    }
}
