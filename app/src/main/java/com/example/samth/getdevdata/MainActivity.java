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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<GetDevData> dataItem;
    SwipeRefreshLayout swipeRefresh;
    ProgressDialog pd;
    TextView mNetworkError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        dataItem = new ArrayList<>();

//        GetDevData example = new GetDevData("Luminous", "Intermediate", R.drawable.ic_launcher_background);
//        GetDevData example1 = new GetDevData("mekus", "beginner", R.drawable.ic_launcher_background);
//        dataItem.add(example);
//        dataItem.add(example1);

        new DevDataAsync().execute();


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

}
