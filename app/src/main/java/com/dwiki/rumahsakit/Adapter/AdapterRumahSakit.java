package com.dwiki.rumahsakit.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dwiki.rumahsakit.API.APIRequestData;
import com.dwiki.rumahsakit.API.RetroServer;
import com.dwiki.rumahsakit.Activity.MainActivity;
import com.dwiki.rumahsakit.Activity.TambahActivity;
import com.dwiki.rumahsakit.Activity.UbahActivity;
import com.dwiki.rumahsakit.Model.ModelResponse;
import com.dwiki.rumahsakit.Model.ModelRumahSakit;
import com.dwiki.rumahsakit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRumahSakit extends RecyclerView.Adapter<AdapterRumahSakit.VHRumahsakit> {
    private Context ctx;
    private List<ModelRumahSakit> listRumahsakit;

    public AdapterRumahSakit(Context ctx, List<ModelRumahSakit> listRumahsakit) {
        this.ctx = ctx;
        this.listRumahsakit = listRumahsakit;
    }

    @NonNull
    @Override
    public VHRumahsakit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.list_rumahsakit, parent, false);
        return new VHRumahsakit(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHRumahsakit holder, int position) {
        ModelRumahSakit MR = listRumahsakit.get(position);

        holder.tvNama.setText(MR.getNama());
        holder.tvFoto.setText(MR.getFoto());
        holder.tvDeskripsi.setText(MR.getDeskripsi());
        holder.tvAlamat.setText(MR.getAlamat());
        holder.tvKoordinat.setText(MR.getKoordinat());
    }

    @Override
    public int getItemCount() {
        return listRumahsakit.size();
    }

    public class VHRumahsakit extends RecyclerView.ViewHolder{
        TextView tvNama, tvFoto, tvDeskripsi, tvAlamat, tvKoordinat;

        public VHRumahsakit(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvKoordinat = itemView.findViewById(R.id.tv_koordinat);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Anda Memilih "+ tvNama.getText().toString() +"Operasi apa yang dilakukan?");

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent kirim = new Intent(ctx, UbahActivity.class);
                            kirim.putExtra("xNama", tvNama.getText().toString());
                            kirim.putExtra("xFoto", tvFoto.getText().toString());
                            kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                            kirim.putExtra("xAlamat", tvAlamat.getText().toString());
                            kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                            ctx.startActivity(kirim);
                        }
                    });

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prosesHapus(tvNama.getText().toString());
                        }
                    });

                    pesan.show();
                    return false;
                }
            });
        }

        void prosesHapus(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);

            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode : " + kode + "Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity)ctx).retriveRumahsakit();

                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
