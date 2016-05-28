package com.example.mehdi.githubuserapp.service;

import com.example.mehdi.githubuserapp.model.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mehdi on 3/15/16.
 */
public interface GitHubApi {

    @GET("/users/mehdi-zarrin")
    Call<GithubUser> getUserInfo();

}
