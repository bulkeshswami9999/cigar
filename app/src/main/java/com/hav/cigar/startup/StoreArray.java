package com.hav.cigar.startup;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.fragments.ImageListFragment;
import com.hav.cigar.model.MyListData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StoreArray {
    String TAG = "dYNAMIC";
    int category;
    JsonObject js;
   public static List<MyListData> listOfMyMonth,listOfMyBargain,listOfMyCigars,listOfMyFlavr,listOfMypack,listofMyspecial;
    APIInterface apiInterface;

    public void setMonth() {
        Call<JsonObject> call;
            apiInterface = APIClient.getClient().create(APIInterface.class);
            call = apiInterface.GetCigarOfMonth();
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listOfMyMonth = new ArrayList<>();
                if (js.get("data") != null) {
                    if(js.get("status").getAsInt()==200) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        if(js.get("data").getAsJsonObject().get("message").getAsJsonArray()!=null) {
                            JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                            for (int i = 0; i < data.size(); i++) {
                                MyListData my = new MyListData();
                                JsonObject js1 = data.get(i).getAsJsonObject();
                                Log.d(TAG, String.valueOf(data.get(i)));
                                String url = js1.get("image").getAsString();
                                my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                                my.setDescription(js1.get("description").getAsString());
                                my.setPrice(js1.get("original_price").getAsString());
                                my.setName(js1.get("name").getAsString());
                                my.setId(js1.get("id").getAsInt());
                                my.setCategory_id(js1.get("category_id").getAsInt());
                                Log.d(TAG, "NAME" + js1.get("name").getAsString() + "MY" + my);
                                listOfMyMonth.add(my);
                            }
                        }
                        else {
                            MyListData my = new MyListData();
                            my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                            my.setDescription("cigars");
                            my.setPrice("50");
                            my.setName("cigars");
                            my.setId(1);
                            listOfMyMonth.add(my);
                        }
                    }
                }
                else {
                        MyListData my = new MyListData();
                        my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                        my.setDescription("cigars");
                        my.setPrice("50");
                        my.setName("cigars");
                        my.setId(1);
                        listOfMyMonth.add(my);
                    }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString()+t);
            }
        });

        //return listOfMyMonth;
    }

    public void setBargain() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.GetProductList(2);
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listOfMyBargain= new ArrayList<>();
                if (js.get("data") != null) {
                    if(js.get("status").getAsInt()==200) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            MyListData my = new MyListData();
                            JsonObject js1 = data.get(i).getAsJsonObject();
                            Log.d(TAG, String.valueOf(data.get(i)));
                            String url = js1.get("image").getAsString();
                            my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                            my.setDescription(js1.get("description").getAsString());
                            my.setPrice(js1.get("original_price").getAsString());
                            my.setName(js1.get("name").getAsString());
                            my.setId(js1.get("id").getAsInt());
                            my.setCategory_id(js1.get("category_id").getAsInt());
                            Log.d(TAG, "NAME" + js1.get("name").getAsString() + "MY" + my);
                            listOfMyBargain.add(my);
                        }
                    }
                    }
                    else {
                        MyListData my = new MyListData();
                        my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                        my.setDescription("cigars");
                        my.setPrice("50");
                        my.setName("cigars");
                        my.setId(1);
                        listOfMyBargain.add(my);
                    }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString()+t);
            }
        });

        //return listOfMyBargain;
    }

    public void  setCigars() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.GetProductList(1);
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listOfMyCigars = new ArrayList<>();
                if (js.get("data") != null) {
                    if (js.get("status").getAsInt() == 200) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            MyListData my = new MyListData();
                            JsonObject js1 = data.get(i).getAsJsonObject();
                            Log.d(TAG, String.valueOf(data.get(i)));
                            String url = js1.get("image").getAsString();
                            my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                            my.setDescription(js1.get("description").getAsString());
                            my.setPrice(js1.get("original_price").getAsString());
                            my.setName(js1.get("name").getAsString());
                            my.setId(js1.get("id").getAsInt());
                            my.setCategory_id(js1.get("category_id").getAsInt());
                            Log.d(TAG, "NAME" + js1.get("name").getAsString() + "MY" + my);
                            listOfMyCigars.add(my);
                            Log.d(TAG, "list of my data");
                        }
                    }
                }
                    else {
                        MyListData my = new MyListData();
                        my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                        my.setDescription("cigars");
                        my.setPrice("50");
                        my.setName("cigars");
                        my.setId(1);
                        listOfMyCigars.add(my);
                    }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString()+t);
            }
        });

        //return listOfMyCigars;
    }

    public void setFlavr() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.GetProductList(3);
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listOfMyFlavr = new ArrayList<>();
                if (js.get("data") != null) {
                    if (js.get("status").getAsInt() == 200) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            MyListData my = new MyListData();
                            JsonObject js1 = data.get(i).getAsJsonObject();
                            Log.d(TAG, String.valueOf(data.get(i)));
                            String url = js1.get("image").getAsString();
                            my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                            my.setDescription(js1.get("description").getAsString());
                            my.setPrice(js1.get("original_price").getAsString());
                            my.setName(js1.get("name").getAsString());
                            my.setId(js1.get("id").getAsInt());
                            my.setCategory_id(js1.get("category_id").getAsInt());
                            Log.d(TAG, "NAME" + js1.get("name").getAsString() + "MY" + my);
                            listOfMyFlavr.add(my);
                            Log.d(TAG, "list of my data");
                        }
                    }
                }
                    else {
                        MyListData my = new MyListData();
                        my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                        my.setDescription("cigars");
                        my.setPrice("50");
                        my.setName("cigars");
                        my.setId(1);
                        listOfMyFlavr.add(my);
                    }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString()+t);
            }
        });

        //return listOfMyFlavr;
    }

    public void setPack() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.GetProductList(4);
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listOfMypack = new ArrayList<>();
                if (js.get("data") != null) {
                    if(js.get("status").getAsInt()==200) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            MyListData my = new MyListData();
                            JsonObject js1 = data.get(i).getAsJsonObject();
                            Log.d(TAG, String.valueOf(data.get(i)));
                            String url = js1.get("image").getAsString();
                            my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                            my.setDescription(js1.get("description").getAsString());
                            my.setPrice(js1.get("original_price").getAsString());
                            my.setName(js1.get("name").getAsString());
                            my.setId(js1.get("id").getAsInt());
                            my.setCategory_id(js1.get("category_id").getAsInt());
                            Log.d(TAG,"NAME"+js1.get("name").getAsString()+"MY"+my);
                            listOfMypack.add(my);
                            Log.d(TAG,"list of my data");
                        }
                    }}
                    else {
                        MyListData my = new MyListData();
                        my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                        my.setDescription("cigars");
                        my.setPrice("50");
                        my.setName("cigars");
                        my.setId(1);
                        listOfMypack.add(my);
                    }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString()+t);
            }
        });

       // return listOfMypack;
    }

    public void setSpecial() {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.GetProductList(5);
        Log.d(TAG, "Call Main " + call.request().url());
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listofMyspecial = new ArrayList<>();
                    if(js.get("status").getAsInt()==200) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            MyListData my = new MyListData();
                            JsonObject js1 = data.get(i).getAsJsonObject();
                            Log.d(TAG, String.valueOf(data.get(i)));
                            String url = js1.get("image").getAsString();
                            my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                            my.setDescription(js1.get("description").getAsString());
                            my.setPrice(js1.get("original_price").getAsString());
                            my.setName(js1.get("name").getAsString());
                            my.setId(js1.get("id").getAsInt());
                            my.setCategory_id(js1.get("category_id").getAsInt());
                            Log.d(TAG,"NAME"+js1.get("name").getAsString()+"MY"+my);
                            listofMyspecial.add(my);
                            Log.d(TAG,"list of my data");
                        }
                    }
                    else {
                        MyListData my = new MyListData();
                        my.setImgId("https://piersongeoffreyscigars-store.com/images/products/detail/IMG_0704.jpg");
                        my.setDescription("cigars");
                        my.setPrice("50");
                        my.setName("cigars");
                        my.setId(1);
                        listofMyspecial.add(my);
                    }
                }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString()+t);
            }
        });

        //return listofMyspecial;
    }
    public List<MyListData> getMonth(Context context)
    {
        Log.d("StoreArrayMonth", String.valueOf(listOfMyMonth));
        return listOfMyMonth;
    }
    public List<MyListData> getCigars(Context context)
    {
        Log.d("StoreArrayCigar", String.valueOf(listOfMyCigars));
        return listOfMyCigars;
    }
    public List<MyListData> getBargain(Context context)
    {
        Log.d("StoreArrayBargain", String.valueOf(listOfMyBargain));
        return listOfMyBargain;
    }
    public List<MyListData> getFlavr(Context context)
    {
        Log.d("StoreArray", String.valueOf(listOfMyFlavr));
        return listOfMyFlavr;
    }
    public List<MyListData> getPack(Context context)
    {
        return listOfMypack;
    }
    public List<MyListData> getSpecial(Context context)
    {
        return listofMyspecial;
    }

}
