//package com.example.myapplication.api;
//
//import android.widget.Toast;
//
//import com.example.myapplication.models.Donation;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class API {
//    Donation donation;
//    public void getAllDonation(){
//        RetrofitInterface.retrofitInterface.excuteGetAll().enqueue(new Callback<Donation>() {
//            @Override
//            public void onResponse(Call<Donation> call, Response<Donation> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Donation> call, Throwable t) {
////                Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void getDonation(){
//        RetrofitInterface.retrofitInterface.excuteGet(donation.id).enqueue(new Callback<Donation>() {
//            @Override
//            public void onResponse(Call<Donation> call, Response<Donation> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Donation> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void postDonation(){
//        RetrofitInterface.retrofitInterface.excutePost().enqueue(new Callback<Donation>() {
//            @Override
//            public void onResponse(Call<Donation> call, Response<Donation> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Donation> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void putDonation(){
//        RetrofitInterface.retrofitInterface.excutePut(donation.id).enqueue(new Callback<Donation>() {
//            @Override
//            public void onResponse(Call<Donation> call, Response<Donation> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Donation> call, Throwable t) {
//
//            }
//        });
//    }
//    public void deleteDonation(){
//        RetrofitInterface.retrofitInterface.excuteDelete(donation.id).enqueue(new Callback<Donation>() {
//            @Override
//            public void onResponse(Call<Donation> call, Response<Donation> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Donation> call, Throwable t) {
//
//            }
//        });
//    }
//
//}
