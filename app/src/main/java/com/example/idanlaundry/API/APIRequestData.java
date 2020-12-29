package com.example.idanlaundry.API;

import com.example.idanlaundry.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ResponseModel>ardRetrieveData();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel>ardCreateData(
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel>ardDeleteData(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel>ardUpdateData(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("telepon") String telepon
    );
}
