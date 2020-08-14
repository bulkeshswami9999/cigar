package com.hav.cigar.options;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.activities.RegisterActivity;
import com.hav.cigar.adapter.sProductAdap;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.model.sProduct;
import com.hav.cigar.utility.Constant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="SearchResultActivity" ;
    EditText searchView;
    //SearchView searchView;
    ImageView imageView2;
    RecyclerView recyclerview;
    private ArrayList<sProduct> sProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        handleIntent(getIntent());
        searchView = (EditText)findViewById(R.id.searchView);
        // searchView = (SearchView) findViewById(R.id.searchView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
        imageView2.setOnClickListener(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));


        // sProductAdap adapter = new sProductAdap(sProduct,this);
        // recyclerview.setAdapter(adapter);
        // adapter.notifyDataSetChanged();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.getItem(0);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setFocusable(true);
        searchItem.expandActionView();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
    public void search(){

        JsonObject js = new JsonObject();
        js.addProperty("searchview", searchView.getText().toString());
        Log.d(TAG,"SEARCH RESULTS"+js.toString());
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.product_search(searchView.getText().toString());
        Log.d(TAG, "Call " + call.request().url());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                // Constant.hideDialog();
                String searchPattern = searchView.getText().toString().toLowerCase().trim();

                if (js.get("data") != null) {
                    if (js.get("status").getAsInt() == 200) {
                        clear();
                        JsonArray dataa = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < dataa.size(); i++) {
                            sProduct sp = new sProduct();
                            JsonObject js1 = dataa.get(i).getAsJsonObject();
                            // Log.d(TAG,String.valueOf(dataa.get(i)));
                            String search = js1.get("name").getAsString();
                            if(search.toLowerCase().contains(searchPattern)){
                                sp.setImage("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                                sp.setDescription(js1.get("description").getAsString());
                                sp.setOriginal_price(js1.get("original_price").getAsString());
                                sp.setName(js1.get("name").getAsString());
                                sProduct.add(sp);
                                sProductAdap adapter = new sProductAdap(sProduct, SearchResultActivity.this);
                                recyclerview.setAdapter(adapter);}


                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString());

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageView2:search();
        }

    }
    public void clear() {
        int size = sProduct.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                sProduct.remove(0);
            }

        }
    }


}
