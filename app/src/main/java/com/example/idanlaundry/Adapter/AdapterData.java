package com.example.idanlaundry.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idanlaundry.API.APIRequestData;
import com.example.idanlaundry.API.RetroServer;
import com.example.idanlaundry.Activity.MainActivity;
import com.example.idanlaundry.Activity.TambahActivity;
import com.example.idanlaundry.Model.DataModel;
import com.example.idanlaundry.Model.ResponseModel;
import com.example.idanlaundry.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listData;
    private int idLaundry;

    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        this.listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        //Buat udah data
        DataModel dm = listData.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvAlamat.setText(dm.getAlamat());
        holder.tvTelepon.setText(dm.getTelepon());

        //Buat ubah data
        holder.dm = dm;

    }

    @Override
    public int getItemCount() {

        return listData.size();
    }

    //Membuat class baru di dalam class adapter data
    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvId,tvNama, tvAlamat, tvTelepon;
        ImageView ivPelanggan;
        DataModel dm;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvTelepon = itemView.findViewById(R.id.tv_telepon);
            ivPelanggan = itemView.findViewById(R.id.iv_pelanggan);

            //Method buat get data yang mau diupdate
          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent goInput = new Intent(ctx, TambahActivity.class);
                  goInput.putExtra("id",dm.getId());
                  goInput.putExtra("nama",dm.getNama());
                  goInput.putExtra("alamat",dm.getAlamat());
                  goInput.putExtra("telepon",dm.getTelepon());
                  ctx.startActivity(goInput);
              }
          });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih");
                    dialogPesan.setCancelable(true);
                    idLaundry = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData();
                            dialog.dismiss();
                            //Casting terhadap variabel ctx
                            ((MainActivity)ctx).retrieveData();
                        }
                    });

                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateData();
                            dialog.dismiss();
                            //Casting terhadap variabel ctx
                            ((MainActivity)ctx).retrieveData();
                        }
                    });
                    dialogPesan.show();

                    return false;
                }
            });
        }
        private void updateData(){

        }
        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idLaundry);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode:"+kode+"Pesan:"+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
