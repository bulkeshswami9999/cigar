package com.hav.cigar.options;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.OrderListAdapter;
import com.hav.cigar.R;
import com.hav.cigar.adapter.MyListAdapter;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.model.Notification;
import com.hav.cigar.model.NotificationApiResponse;
import com.hav.cigar.model.NotificationListingApi;
import com.hav.cigar.startup.FrescoApplication;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyListAdapter adapter;
    public Context context;

    private int[] images = {R.drawable.beforedawn, R.drawable.cedar, R.drawable.cubanshade,
            R.drawable.darknight};
    private String[] str = {"Ordered Placed Successfully", "Ordered Replaced Successfully",
            "Delivery boy out to deliver", "Ordered Delivered"};

    FrescoApplication frescoApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        frescoApplication=(FrescoApplication) getApplication();
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        callNotificationApi();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private  void callNotificationApi()
    {

        APIInterface mInterface= APIClient.getClient().create(APIInterface.class);

        String customer_id=frescoApplication.getPreferences().getToken("user_id","");
        Log.e("==cust_id",""+(frescoApplication.getPreferences().getToken("user_id","")));

        Call<NotificationListingApi> call= mInterface.getOrderList(customer_id);

        call.enqueue(new Callback<NotificationListingApi>()
        {
            @Override
            public void onResponse(Call<NotificationListingApi> call, Response<NotificationListingApi> response)
            {
                if(response.isSuccessful())
                {
                    if(response.body().getStatus().equals("200")) {
                        adapter = new MyListAdapter(context, response.body().getNotificationListingResponse().getMessage());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationListingApi> call, Throwable t)
            {

            }
        });
    }
}