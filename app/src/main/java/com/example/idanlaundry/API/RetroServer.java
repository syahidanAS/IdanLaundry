package com.example.idanlaundry.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "http://192.168.43.34/laundry/";
    public static final String IMAGES_URL = "http://192.168.43.34/laundry/upload";
    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if (retro==null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}
