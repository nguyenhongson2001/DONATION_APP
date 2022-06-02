package com.example.myapplication.api;

import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.example.myapplication.models.Donation;

public class DonationApi {
	//////////////////////////////////////////////////////////////////////////////////
	public static List<Donation> getAll(String call) {
		call = "/get";
		String json = Rest.get(call);
		Log.v("donate", "JSON RESULT : " + json);
		Type collectionType = new TypeToken<List<Donation>>(){}.getType();

		return new Gson().fromJson(json, collectionType);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public static Donation get(String call,String id) {
		call = "/get";
		String json = Rest.get(call + "/" + id);
		Log.v("donate", "JSON RESULT : " + json);
		Type objType = new TypeToken<Donation>(){}.getType();

		return new Gson().fromJson(json, objType);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public static String deleteAll(String call) {
		call = "/delete";
		return Rest.delete(call);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public static String delete(String call, String id) {
		call = "/delete";
		return Rest.delete(call + "/" + id);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public static String insert(String call,Donation donation) {
		call = "/post";
		Type objType = new TypeToken<Donation>(){}.getType();
		String json = new Gson().toJson(donation, objType);

		return Rest.post(call,json);
	}
}
