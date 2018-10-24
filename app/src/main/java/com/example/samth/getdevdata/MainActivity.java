package com.example.samth.getdevdata;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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

//    public static Object mNetworkError;
    RecyclerView mRecyclerView;
    ArrayList<GetDevData> dataItem;
    SwipeRefreshLayout swipeRefresh;
    ProgressDialog pd;
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
//                new DevDataAsync().execute();
//                swipeRefresh.setRefreshing(false);
//                mRecyclerView.smoothScrollToPosition(0);
//                Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();

                loadJsonData();
            }
        });

        dataItem = new ArrayList<>();

        //new DevDataAsync().execute();
        loadJsonData();

    }



    public class DevDataAsync extends AsyncTask<Void, Void, ArrayList<GetDevData>> {

       // ProgressDialog pd = new ProgressDialog(getApplicationContext());
        protected void onPreExecute(Void... voids) {
//            pd.setMessage("getting data...");
//            pd.show();

        }

        @Override
        protected ArrayList<GetDevData> doInBackground(Void... voids) {
            if (voids == null)
                return null;

            dataItem = Utils.getDevDataList();
            Log.i("connection", "data gotten successful");
            return dataItem;
        }

        protected void onPostExecute(ArrayList<GetDevData> dataItemList) {

            for (GetDevData a: dataItemList) {
                Log.i("next to Adapter", a.getmName() + " " + a.getmRank());
            }
            GetDevAdapter adapter = new GetDevAdapter(getApplicationContext(), dataItemList);
            mRecyclerView.setAdapter(adapter);
        }
    }


    public void loadJsonData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<GetDevDataResponse> call = apiService.getDataItems();
        call.enqueue(new Callback<GetDevDataResponse>() {
            @Override
            public void onResponse(Call<GetDevDataResponse> call, Response<GetDevDataResponse> response) {
                List<GetDevData> devData = response.body().getDataItems();
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
