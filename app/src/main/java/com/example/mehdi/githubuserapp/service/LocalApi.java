package com.example.mehdi.githubuserapp.service;

import com.example.mehdi.githubuserapp.model.News;
import com.example.mehdi.githubuserapp.model.openshiftUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mehdi on 3/15/16.
 */
public interface LocalApi {

    @GET("users")
    Call<ArrayList<openshiftUser>> getUsers(@Query("start") int start);
}
