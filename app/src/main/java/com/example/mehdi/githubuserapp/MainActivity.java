package com.example.mehdi.githubuserapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.GpsSatellite;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehdi.githubuserapp.adapter.CustomSwipAdapter;
import com.example.mehdi.githubuserapp.model.GithubUser;
import com.example.mehdi.githubuserapp.service.GitHubApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    TextView logintxt, idtxt, repostxt;
    ImageView imgView;
    Context context;
    Boolean loadingShowing;
    ViewPager viewPager;
    CustomSwipAdapter adapter;
    Button nextBtn, cardViewBtn, popupBtn;
    ProgressBar progress;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logintxt = (TextView) findViewById(R.id.login);
        idtxt = (TextView) findViewById(R.id.id);
        repostxt = (TextView) findViewById(R.id.repos);
        imgView = (ImageView) findViewById(R.id.avatar);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        cardViewBtn = (Button) findViewById(R.id.card_view);
        popupBtn = (Button) findViewById(R.id.popup);
        context = this;
        progress = (ProgressBar) findViewById(R.id.marker_progress);
        progress.setVisibility(View.VISIBLE);
        // navigation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable., /* nav drawer image to replace 'Up' caret */
                R.string.app_drawer_open, /* "open drawer" description for accessibility */
                R.string.app_drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        GitHubApi service = retrofit.create(GitHubApi.class);

        Call<GithubUser> call = service.getUserInfo();

        loadingShowing = true;

        call.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                try {

                    if(loadingShowing) {
                        progress.setVisibility(GONE);
                        loadingShowing = false;
                    }

                    logintxt.setText(response.body().getLogin());
                    idtxt.setText(response.body().getId().toString());
                    repostxt.setText(response.body().getPublicRepos().toString());
                    Picasso.with(getApplicationContext())
                            .load(response.body().getAvatarUrl().toString())
                            .placeholder(R.drawable.sample_1)
                            .into(imgView);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                progress.setVisibility(GONE);
                Toast.makeText(getApplicationContext(), "Could not connect to internet", Toast.LENGTH_LONG).show();

            }
        });

        // goto next activity button
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this, NextActivity.class);
                startActivity(nextActivity);
            }
        });


        // open popup btn
        popupBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something and open popup activity
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.show();
            }
        });


        // card view btn click
        cardViewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cardActivity = new Intent(MainActivity.this, CardViewActivity.class);
                startActivity(cardActivity);
            }
        });

        // view pager related code
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        adapter = new CustomSwipAdapter(this);
        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
