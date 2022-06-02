//package com.example.myapplication.api;
//
//import retrofit2.Call;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.http.Body;
//import retrofit2.http.DELETE;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.PUT;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//
//import com.example.myapplication.models.Donation;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.util.HashMap;
//
//
//public interface RetrofitInterface {
//
//    Gson gson = new GsonBuilder()
//            .setDateFormat("yyyy-MM--dd HH:mm:ss")
//            .create();
//
//    RetrofitInterface retrofitInterface = new Retrofit.Builder()
//            .baseUrl("https://donation-testapi.herokuapp.com/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(RetrofitInterface.class);
//
//    @GET("/donation/GET/{id}")
//    Call<Donation> excuteGet(@Path("id") int id);
//
//    @GET("/donation/GET")
//    Call<Donation> excuteGetAll();
//
//    @POST("/donation/POST")
//    Call<Donation> excutePost();
//
//    @PUT("/donation/PUT/{id}")
//    Call<Donation> excutePut(@Path("id") int id);
//
//    @DELETE("/donation/DELETE/{id}")
//    Call<Donation> excuteDelete(@Path("id") int id);
//}
