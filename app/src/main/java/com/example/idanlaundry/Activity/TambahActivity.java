package com.example.idanlaundry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.idanlaundry.API.APIRequestData;
import com.example.idanlaundry.API.RetroServer;
import com.example.idanlaundry.Model.ResponseModel;
import com.example.idanlaundry.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private Context ctx;
    private EditText etNama,etAlamat,etTelepon;
    private Button btnSimpan,btnUbah;
    private String nama,alamat,telepon;
    private TextView titleTambah, titleUbah;
    private ProgressBar pbUpdate;
    private ImageView addPhoto;
    String part_image;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_alamat);
        etTelepon = findViewById(R.id.et_telepon);
        btnSimpan = findViewById(R.id.btn_simpan);
        titleTambah = findViewById(R.id.title_tambah);
        titleUbah = findViewById(R.id.title_ubah);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnUbah = findViewById(R.id.btn_ubah);
        pbUpdate = findViewById(R.id.pb_update);
        addPhoto = findViewById(R.id.add_photo);



        Intent data = getIntent();
        int idnya= data.getIntExtra("id", 0);
        if (idnya > 0){

            titleUbah.setVisibility(View.VISIBLE);
            titleTambah.setVisibility(View.GONE);
            btnUbah.setVisibility(View.VISIBLE);
            btnSimpan.setVisibility(View.GONE);

            etNama.setText(data.getStringExtra("nama"));
            etAlamat.setText(data.getStringExtra("alamat"));
            etTelepon.setText(data.getStringExtra("telepon"));
        }

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APIRequestData api = RetroServer.konekRetrofit().create(APIRequestData.class);
                Call<ResponseModel>update = api.ardUpdateData(idnya,etNama.getText().toString(),
                        etAlamat.getText().toString(),etTelepon.getText().toString());
                update.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        int kode = response.body().getKode();
                        String pesan = response.body().getPesan();

                        Toast.makeText(TambahActivity.this, "Kode :"+kode+"Pesan :"+pesan, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(TambahActivity.this, "Pesan:"+t.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nama = etNama.getText().toString();
               alamat = etAlamat.getText().toString();
               telepon = etTelepon.getText().toString();

               if (nama.trim().equals("")){
                   etNama.setError("Nama harus diisi");

               }else if(alamat.trim().equals("")){
                   etAlamat.setError("Alamat harus diisi");

               }else if(telepon.trim().equals("")){
                   etTelepon.setError("No telepon harus diisi");

               }else{
                   createData();
               }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama,alamat,telepon);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode :"+kode+"Pesan :"+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menyimpan data"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}