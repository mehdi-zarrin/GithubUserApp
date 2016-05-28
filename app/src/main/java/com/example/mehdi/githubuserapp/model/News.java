package com.example.mehdi.githubuserapp.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("newsModels")
    @Expose
    private List<NewsModel> newsModels = new ArrayList<NewsModel>();

    /**
     *
     * @return
     * The newsModels
     */
    public List<NewsModel> getNewsModels() {
        return newsModels;
    }

    /**
     *
     * @param newsModels
     * The newsModels
     */
    public void setNewsModels(List<NewsModel> newsModels) {
        this.newsModels = newsModels;
    }

}