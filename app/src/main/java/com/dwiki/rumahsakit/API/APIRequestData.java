package com.dwiki.rumahsakit.API;

import com.dwiki.rumahsakit.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponse> ardRetrieve();

    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("foto") String foto,
            @Field("deskripsi") String deskripsi,
            @Field("alamat") String alamat,
            @Field("koordinat") String koordinat
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("foto") String foto,
            @Field("deskripsi") String deskripsi,
            @Field("alamat") String alamat,
            @Field("koordinat") String koordinat
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponse> ardDelete(
            @Field("id") String id
    );
}
