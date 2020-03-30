package com.example.coronadashboard.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.coronadashboard.R;
import com.example.coronadashboard.model.ResponseData;
import com.example.coronadashboard.model.provinsi.AttributesProvinsi;
import com.example.coronadashboard.model.provinsi.ResponseDataProvinsi;
import com.example.coronadashboard.network.ApiInterface;
import com.example.coronadashboard.network.ApiService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataProvinsiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_provinsi);
        recyclerView = findViewById(R.id.rv_data_provinsi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Provinsi");
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
        progressDialog.setCancelable(false);
        progressDialog.show();
            ApiInterface client = ApiService.createService(ApiInterface.class);
            // Fetch and print a list of the contributors to this library.
            Call<List<ResponseDataProvinsi>> call = client.getDataProvinsi();
            call.enqueue(new Callback<List<ResponseDataProvinsi>>() {
                @Override
                public void onResponse(Call<List<ResponseDataProvinsi>> call, Response<List<ResponseDataProvinsi>> response) {

                    if (response.isSuccessful()) {
                        ArrayList dataResult = new ArrayList();
                        for (int a=0; a < response.body().size(); a++) {
                            AttributesProvinsi data = response.body().get(a).getAttributesProvinsi();
                            dataResult.add(data);
                            Log.e("TAG", "data provinsi " + new Gson().toJson(dataResult));
                        }

                        AdapterDataProvinsi adapterDataProvinsi = new AdapterDataProvinsi(dataResult);
                        Log.e("TAG", "data provinsi " + new Gson().toJson(dataResult));
                        recyclerView.setAdapter(adapterDataProvinsi);
                        adapterDataProvinsi.notifyDataSetChanged();
                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(DataProvinsiActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<ResponseDataProvinsi>> call, Throwable t) {

                    progressDialog.dismiss();
                    Toast.makeText(DataProvinsiActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
                }
            });

    }
}
