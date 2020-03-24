package com.example.coronadashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.coronadashboard.Models.ResponseCovid19;
import com.example.coronadashboard.MyRetrofit.ApiInterface;
import com.example.coronadashboard.MyRetrofit.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView tvPostif, tvMeninggal, tvSembuh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSembuh = findViewById(R.id.sembuh);
        tvMeninggal = findViewById(R.id.meninggal);
        tvPostif = findViewById(R.id.positif);
        setData();
    }

    private void setData() {
        ApiInterface client = ServiceGenerator.createService(ApiInterface.class);
        // Fetch and print a list of the contributors to this library.
        Call<List<ResponseCovid19>> call = client.get_covid19_indonesia();

        call.enqueue(new Callback<List<ResponseCovid19>>() {
            @Override
            public void onResponse(Call<List<ResponseCovid19>> call, Response<List<ResponseCovid19>> response) {
                try {
                    List<ResponseCovid19> mResponseCovid19 = response.body();


                    String postif = mResponseCovid19.get(0).getPositif();
                    String sembuh = mResponseCovid19.get(0).getSembuh();
                    String meninggal = mResponseCovid19.get(0).getMeninggal();

                    tvMeninggal.setText(meninggal);
                    tvPostif.setText(postif);
                    tvSembuh.setText(sembuh);

                    // Instruct the widget manager to update the widget

                } catch (Exception e) {
                    Log.e("ERROR", "Error bro " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseCovid19>> call, Throwable t) {
                Log.e("ERROR", "Error bro " + t.getMessage());
            }
        });
    }
}
