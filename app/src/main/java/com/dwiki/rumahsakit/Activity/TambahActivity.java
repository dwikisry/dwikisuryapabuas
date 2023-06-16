package com.dwiki.rumahsakit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dwiki.rumahsakit.API.APIRequestData;
import com.dwiki.rumahsakit.API.RetroServer;
import com.dwiki.rumahsakit.Model.ModelResponse;
import com.dwiki.rumahsakit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etFoto, etDeskripsi, etAlamat, etKoordinat;
    private Button btnSimpan;
    private String nama, foto, deskripsi, alamat, koordinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etFoto = findViewById(R.id.et_foto);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etAlamat = findViewById(R.id.et_alamat);
        etKoordinat = findViewById(R.id.et_koordinat);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                foto = etFoto.getText().toString();
                deskripsi = etDeskripsi.getText().toString();
                alamat = etAlamat.getText().toString();
                koordinat = etKoordinat.getText().toString();

                if(nama.trim().isEmpty()){
                    etNama.setError("Nama Harus Diisi");
                    
                }
                else if (foto.trim().isEmpty()){
                    etFoto.setError("Foto Harus Diisi");
                }
                else if (deskripsi.trim().isEmpty()){
                    etDeskripsi.setError("Deskripsi Harus Diisi");
                }
                else if (alamat.trim().isEmpty()){
                    etAlamat.setError("Alamat Harus Diisi");
                }
                else if (koordinat.trim().isEmpty()){
                    etKoordinat.setError("Koordinat Harus Diisi");
                }
                else{
                    prosesSimpan();
                }
            }
        });
    }

    private void prosesSimpan(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(nama, foto, deskripsi, alamat, koordinat);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}