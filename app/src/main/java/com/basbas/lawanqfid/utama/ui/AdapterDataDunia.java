package com.basbas.lawanqfid.utama.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basbas.lawanqfid.R;
import com.basbas.lawanqfid.utama.model.Attributes;
import com.basbas.lawanqfid.utama.model.provinsi.ResponseDataProvinsi;

import java.util.ArrayList;

public class AdapterDataDunia extends RecyclerView.Adapter<AdapterDataDunia.MyViewHolder> {
    private ArrayList<Attributes> lisData;
    private OnItemClickCallback onItemClickCallback;
    public interface OnItemClickCallback {
        void onItemClicked(ResponseDataProvinsi data);
    }
    void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    AdapterDataDunia(ArrayList<Attributes> list) {
        this.lisData = list;
    }
    @NonNull
    @Override
    public AdapterDataDunia.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_provinsi, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataDunia.MyViewHolder holder, int position) {
//
        holder.tvSembuh.setText(""+lisData.get(position).getRecovered());
        holder.tvPositif.setText(""+lisData.get(position).getConfirmed());
        holder.tvMeninggal.setText(""+lisData.get(position).getDeaths());
        holder.tvProvinsi.setText(lisData.get(position).getCountryRegion());
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