package com.example.mehdi.githubuserapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehdi.githubuserapp.adapter.CustomListAdapter;
import com.example.mehdi.githubuserapp.model.openshiftUser;
import com.example.mehdi.githubuserapp.service.LocalApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mehdi on 3/15/16.
 */
public class NextActivity extends AppCompatActivity {

    TextView email, phone;
    ListView usersListView;
    Context context;
    CustomListAdapter adapter;
    Boolean flag = false;
    int lastLoad = 0;
    ArrayList<openshiftUser> usersFeed;
    View footerLoading;
    ProgressBar mainLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        context = this;
        usersListView = (ListView) findViewById(R.id.listView);
        usersFeed = new ArrayList<openshiftUser>();
        adapter = new CustomListAdapter(NextActivity.this, usersFeed);
        usersListView.setAdapter(adapter);
        footerLoading = getLayoutInflater().inflate(R.layout.progress_dialog, null);
        ProgressBar pb = (ProgressBar) footerLoading.findViewById(R.id.progressBar1);
        pb.setIndeterminate(true);

        mainLoader = (ProgressBar) findViewById(R.id.progressBar1);
        mainLoader.setVisibility(View.VISIBLE);

        loadData(0);
        usersListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 2)
                    flag = true;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if ((visibleItemCount == (totalItemCount - firstVisibleItem))
                        && flag) {
                    flag = false;
                    lastLoad = lastLoad + 1;
                    loadData(lastLoad);
                }
            }
        });

    }


    public void loadData(int start) {

        // show progress footer
        if(start > 0) {
            usersListView.addFooterView(footerLoading);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://laravelapp-ramstin.rhcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocalApi service = retrofit.create(LocalApi.class);
        Call<ArrayList<openshiftUser>> call = service.getUsers(start);

        call.enqueue(new Callback<ArrayList<openshiftUser>>() {


            @Override
            public void onResponse(Call<ArrayList<openshiftUser>> call, Response<ArrayList<openshiftUser>> response) {
                try {
                    mainLoader.setVisibility(View.GONE);
                    usersListView.removeFooterView(footerLoading);
                    ArrayList<openshiftUser> results = response.body();
                    for(int i = 0; i < results.size(); i++) {
                        usersFeed.add(results.get(i));
                    }
                    adapter.notifyDataSetChanged();
                    usersListView.removeFooterView(footerLoading);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<openshiftUser>> call, Throwable t) {
                Toast.makeText(context, "could not connect to web", Toast.LENGTH_LONG).show();
            }
        });
    }
}
