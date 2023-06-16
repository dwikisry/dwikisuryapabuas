package com.dwiki.rumahsakit.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dwiki.rumahsakit.API.APIRequestData;
import com.dwiki.rumahsakit.API.RetroServer;
import com.dwiki.rumahsakit.Adapter.AdapterRumahSakit;
import com.dwiki.rumahsakit.Model.ModelResponse;
import com.dwiki.rumahsakit.Model.ModelRumahSakit;
import com.dwiki.rumahsakit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvrumahsakit;
    private ProgressBar pbrumahsakit;
    private FloatingActionButton fabtambah;
    private RecyclerView.Adapter adRumahsakit;
    private RecyclerView.LayoutManager lmRumahsakit;
    private List<ModelRumahSakit> listRumahsakit = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvrumahsakit = findViewById(R.id.rv_rumahsakit);
        pbrumahsakit = findViewById(R.id.pb_rumahsakit);
        fabtambah = findViewById(R.id.fab_tambah);

        lmRumahsakit = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvrumahsakit.setLayoutManager(lmRumahsakit);

        fabtambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });
    }

    public void retrieveRumahsakit(){
        pbrumahsakit.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listRumahsakit = response.body().getData();

                adRumahsakit = new AdapterRumahSakit(MainActivity.this, listRumahsakit);
                rvrumahsakit.setAdapter(adRumahsakit);
                adRumahsakit.notifyDataSetChanged();

                pbrumahsakit.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbrumahsakit.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveRumahsakit();
    }
}