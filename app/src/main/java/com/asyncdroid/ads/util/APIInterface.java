package com.asyncdroid.ads.util;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("login")
    Call<JsonObject> login(@Body JsonObject loginRequest);

    @POST("signUp")
    Call<JsonObject> signUp(@Body JsonObject signUpRequest);
}
