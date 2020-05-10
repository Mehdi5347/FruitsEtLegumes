package com.example.fruitslegumes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FruitActivity extends AppCompatActivity {

    private final String BASE_URL = "https://raw.githubusercontent.com/Mehdi5347/FruitsEtLegumes/master/app/src/main/java/com/example/fruitslegumes/";


    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences s;
    private Gson gson;
    private List<Fruit> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);


        gson = new GsonBuilder().setLenient().create();
        s = getSharedPreferences("Marvel ", Context.MODE_PRIVATE);


        List<Fruit> liste = cache();
        if(liste != null){
            showList(liste);
        }else{
            makeApiCall();
        }



    }

    private void showList(List<Fruit> listeMarvel) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(listeMarvel,getApplicationContext());
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FruitAPI api = retrofit.create(FruitAPI.class);

        Call<List<Fruit>> call = api.getFruit();
        call.enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    saveList(list);
                    showList(list);
                    Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();

                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                showError();
            }
        });
    }

    private void saveList(List<Fruit> list){

        String jsonString  = gson.toJson(list);

        s.edit().putString("jsonString",jsonString).apply();


    }
    private List<Fruit> cache(){

        String jsonMarvel = s.getString("jsonString",null);
        if(jsonMarvel == null){
            return null;
        }else{
            Type listeType = new TypeToken<List<Fruit>>(){}.getType();
            return gson.fromJson(jsonMarvel,listeType);
        }


    }






    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }























}
