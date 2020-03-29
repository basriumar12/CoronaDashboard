package com.example.coronadashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.coronadashboard.model.ResponseData;
import com.example.coronadashboard.network.ApiInterface;
import com.example.coronadashboard.network.ApiService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvPostif, tvMeninggal, tvSembuh, tvDate;
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
}
