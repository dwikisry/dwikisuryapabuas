package com.dwiki.rumahsakit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dwiki.rumahsakit.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvDeskripsi, tvAlamat;
    private ImageView ivFoto;
    private String yNama, yFoto, yDeskripsi, yAlamat, yKoordinat;

    private Button btn_alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNama = findViewById(R.id.tv_nama);
        tvAlamat = findViewById(R.id.tv_alamat);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        ivFoto = findViewById(R.id.iv_foto);
        btn_alamat = findViewById(R.id.bt_koordinat);

        Intent tangkap = getIntent();
        yNama = tangkap.getStringExtra("xNama");
        yFoto = tangkap.getStringExtra("xFoto");
        yDeskripsi = tangkap.getStringExtra("xDeskripsi");
        yAlamat = tangkap.getStringExtra("xAlamat");
        yKoordinat = tangkap.getStringExtra("xKoordinat");

        tvNama.setText(yNama);
        tvDeskripsi.setText(yDeskripsi);
        tvAlamat.setText(yAlamat);
        Glide
                .with(DetailActivity.this)
                .load(yFoto)
                .into(ivFoto);

        btn_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
