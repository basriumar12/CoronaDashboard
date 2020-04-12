package com.basbas.lawanqfid.utama.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basbas.lawanqfid.R;
import com.basbas.lawanqfid.utama.model.Attributes;
import com.basbas.lawanqfid.utama.model.ResponseDataWorld;
import com.basbas.lawanqfid.utama.network.ApiInterface;
import com.basbas.lawanqfid.utama.network.ApiServiceNew;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDuniaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_provinsi);
        recyclerView = findViewById(R.id.rv_data_provinsi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Seluruh Negara");
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void setData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading.....");
        progressDialog.setCancelable(false);
        progressDialog.show();
            ApiInterface client = ApiServiceNew.createService(ApiInterface.class);
            // Fetch and print a list of the contributors to this library.
            Call<List<ResponseDataWorld>> call = client.getDataDunia();
            call.enqueue(new Callback<List<ResponseDataWorld>>() {
                @Override
                public void onResponse(Call<List<ResponseDataWorld>> call, Response<List<ResponseDataWorld>> response) {

                    if (response.isSuccessful()) {
                        ArrayList dataResult = new ArrayList();
                        for (int a=0; a < response.body().size(); a++) {
                            Attributes data = response.body().get(a).getAttributes();
                            dataResult.add(data);
                            Log.e("TAG", "data provinsi " + new Gson().toJson(dataResult));
                        }

                        AdapterDataDunia adapterDataProvinsi = new AdapterDataDunia(dataResult);
                        Log.e("TAG", "data provinsi " + new Gson().toJson(dataResult));
                        recyclerView.setAdapter(adapterDataProvinsi);
                        adapterDataProvinsi.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DataDuniaActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<ResponseDataWorld>> call, Throwable t) {

                    progressDialog.dismiss();
                    Toast.makeText(DataDuniaActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
                }
            });

    }
}
