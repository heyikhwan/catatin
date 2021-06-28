package com.ikhwan.catatin.Rest;

import com.ikhwan.catatin.Response.ResponseLogin;
import com.ikhwan.catatin.Response.ResponseProduct;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("address") String address,
            @Field("gender") String gender
    );

    @GET("read_product.php")
    Call<ResponseProduct> readProduct();

    @FormUrlEncoded
    @POST("create_product.php")
    Call<ResponseProduct> createProduct(
            @Field("name") String name,
            @Field("purchase_price") Integer purchase_price,
            @Field("selling_price") Integer selling_price,
            @Field("qty") Integer qty,
            @Field("entry_by") String entry_by
    );

    @FormUrlEncoded
    @POST("delete_product.php")
    Call<ResponseProduct> deleteProduct(
            @Field("id") Integer id
    );
}
