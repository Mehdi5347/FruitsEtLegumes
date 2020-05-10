package com.example.fruitslegumes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FruitAPI {

    @GET("api.json")
    Call<List<Fruit>> getFruit  ();
}
