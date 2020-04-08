package com.basbas.lawanqfid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.basbas.lawanqfid.model.ResponseData;
import com.basbas.lawanqfid.network.ApiInterface;
import com.basbas.lawanqfid.network.ApiService;
import com.basbas.lawanqfid.ui.DataDuniaActivity;
import com.basbas.lawanqfid.ui.DataProvinsiActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvPostif, tvMeninggal, tvSembuh, tvDate;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setData();
    }

    private void initView() {

        tvDate = findViewById(R.id.tv_date);
        tvSembuh = findViewById(R.id.sembuh);
        tvMeninggal = findViewById(R.id.meninggal);
        tvPostif = findViewById(R.id.positif);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM  yyyy   ", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        tvDate.setText(currentDateandTime);


    }

    private void setData() {
        ApiInterface client = ApiService.createService(ApiInterface.class);
        // Fetch and print a list of the contributors to this library.
        Call<List<ResponseData>> call = client.getData();

        call.enqueue(new Callback<List<ResponseData>>() {
            @Override
            public void onResponse(Call<List<ResponseData>> call, Response<List<ResponseData>> response) {
                try {
                    List<ResponseData> mResponseData = response.body();


                    String postif = mResponseData.get(0).getPositif();
                    String sembuh = mResponseData.get(0).getSembuh();
                    String meninggal = mResponseData.get(0).getMeninggal();

                    tvMeninggal.setText(meninggal);
                    tvPostif.setText(postif);
                    tvSembuh.setText(sembuh);

                    // Instruct the widget manager to update the widget

                } catch (Exception e) {
                    Log.e("ERROR", "Error bro " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseData>> call, Throwable t) {
                Log.e("ERROR", "Error bro " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.menu_dunia:
                title = "Data Dunia";
                startActivity(new Intent(this, DataDuniaActivity.class));
                break;

            case R.id.menu_provinsi:
                title = "Data Provinsi";
                startActivity(new Intent(this, DataProvinsiActivity.class));
                break;


        }
        setActionBarTitle(title);
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
