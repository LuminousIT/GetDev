package com.example.samth.getdevdata;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import RestApi.ApiClient;
import RestApi.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<UserData> dataItem;
    private SwipeRefreshLayout swipeRefresh;
    public static TextView mNetworkError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetworkError = findViewById(R.id.networkerror);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJsonData();
            }
        });

        dataItem = new ArrayList<>();

        loadJsonData();

    }


    public void loadJsonData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<GetDevDataResponse> call = apiService.getDataItems();
        call.enqueue(new Callback<GetDevDataResponse>() {
            @Override
            public void onResponse(Call<GetDevDataResponse> call, Response<GetDevDataResponse> response) {
                List<UserData> devData = response.body().getDataItems();
                swipeRefresh.setRefreshing(false);
                mRecyclerView.setAdapter(new GetDevAdapter(getApplicationContext(), devData));
                mRecyclerView.smoothScrollToPosition(0);

            }

            @Override
            public void onFailure(Call<GetDevDataResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.this, "Error fetching data...", Toast.LENGTH_SHORT).show();
                //disconnected.setVisibility(View.VISIBLE);
            }
        });
    }
}
