/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hav.cigar.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.model.MyListData;
import com.hav.cigar.product.ItemDetailsActivity;
import com.hav.cigar.startup.StoreArray;
import com.hav.cigar.utility.Constant;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class ImageListFragment extends Fragment {
    private static final String STRING_IMAGE_CATEGORY = "2";
    String TAG = "Image List";
    public static final String STRING_IMAGE_URI = "ImageUri", STRING_IMAGE_TITLE = "ImgTitle", STRING_IMAGE_DESC = "ImgDescription";
    public static final String STRING_IMAGE_POSITION = "ImagePosition", STRING_IMAGE_PRICE = "$9",STRING_IMAGE_ID="1";
    private static FragmentActivity mActivity;
    public TextView ImgTitle, ImgDesc, ImgPrice;
    private static RecyclerView rv;
    SimpleStringRecyclerViewAdapter adapter;
    public SimpleDraweeView mImageView;
    List<MyListData> listOfMyDatd;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //ImgTitle1 = mActivity.findViewById(R.id.ImgTitle);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recylerview_list, container, false);
        // View view1 = inflater.inflate(R.layout.list_item, container, false);
        rv = view.findViewById(R.id.recyclerview);
        mActivity = getActivity();
       setupRecyclerView(rv);
        return view;
    }

    private void setupRecyclerView(RecyclerView rv) {
        StoreArray st = new StoreArray();
        Log.d(TAG,"setupRecyclerview");
        if (ImageListFragment.this.getArguments().getInt("type") == 1){
            listOfMyDatd = st.getMonth(getContext()); }
        else  if (ImageListFragment.this.getArguments().getInt("type") == 2){
            listOfMyDatd = st.getBargain(getContext()); }
        else  if (ImageListFragment.this.getArguments().getInt("type") == 3){
            listOfMyDatd = st.getCigars(getContext());}
        else  if (ImageListFragment.this.getArguments().getInt("type") == 4){
            listOfMyDatd = st.getFlavr(getContext());}
        else  if (ImageListFragment.this.getArguments().getInt("type") == 5){
            listOfMyDatd = st.getPack(getContext()); }
        else  if (ImageListFragment.this.getArguments().getInt("type") == 6){
            listOfMyDatd = st.getSpecial(getContext());}
        else{
            listOfMyDatd = st.getMonth(getContext());}
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                rv.setLayoutManager(layoutManager);
               // recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), recyclerView, listOfMyDatd));
                 adapter = new SimpleStringRecyclerViewAdapter(getActivity(), rv,listOfMyDatd);
                rv.setAdapter(adapter);
        }

    public class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private RecyclerView mRecyclerView;
        Context context;
        List<MyListData> listOfData;
        private TextView ImgTitle, ImgDesc, ImgPrice;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            TextView ImgTitle, ImgDesc, ImgPrice;
            public final SimpleDraweeView image;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                ImgTitle = view.findViewById(R.id.ImgTitle);
                ImgDesc = view.findViewById(R.id.ImgDesc);
                ImgPrice = view.findViewById(R.id.Imgprice);
                image = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);

            }
        }
        public SimpleStringRecyclerViewAdapter(Context context, RecyclerView recyclerView,List<MyListData> listData) {
           this.listOfData = listData;
            mRecyclerView = recyclerView;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.image.getController() != null) {
                holder.image.getController().onDetach();
            }
            if (holder.image.getTopLevelDrawable() != null) {
                holder.image.getTopLevelDrawable().setCallback(null);

            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
             MyListData mydata = listOfData.get(position);
            holder.ImgTitle.setText(mydata.getName());
            holder.ImgPrice.setText(mydata.getPrice());
            holder.ImgDesc.setText(mydata.getDescription());
            //holder.mImageView.setImageResource(R.drawable.beforedawn);
            //else*/
            Glide.with(context).load(mydata.getImgId()).
                   placeholder(R.drawable.beforedawn).error(R.drawable.actdot).into(holder.image);
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mydata.getImgId());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    intent.putExtra(STRING_IMAGE_TITLE, mydata.getName());
                    intent.putExtra(STRING_IMAGE_DESC, mydata.getDescription());
                    intent.putExtra(STRING_IMAGE_PRICE, mydata.getPrice());
                    intent.putExtra(STRING_IMAGE_ID,mydata.getId());
                    intent.putExtra(STRING_IMAGE_CATEGORY,mydata.getCategory_id());
                    Log.d(TAG,STRING_IMAGE_ID+"   "+STRING_IMAGE_TITLE);
                    context.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    //imageUrlUtils.addWishlistImageUri(String.valueOf(mValues[position]));
                    holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                    notifyDataSetChanged();
                    int product_id = mydata.getId();
                    SharedPreferences sp = context.getSharedPreferences(Constant.MyPREFERENCES,context.MODE_PRIVATE);
                   int user_id = sp.getInt("user_id",0);
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<JsonObject> call = apiInterface.AddWishList(user_id,product_id);
                    call.enqueue(new retrofit2.Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d(TAG, "onResponse " + response.body());
                            JsonObject js = response.body();
                            String msg = js.get("data").getAsJsonObject().get("message").getAsString();
                            //Toast.makeText(cont
                           if(js.getAsJsonPrimitive("status").getAsInt()==200)
                            Toast.makeText(context, "Item added to wishlist.", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context,"Item already in wishlist",Toast.LENGTH_SHORT).show();
                            Constant.hideDialog();
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d(TAG, "onFailure " + call.toString());
                        }
                    });
                }
            });
        }
        @Override
        public int getItemCount() {
            if(listOfData!=null)
            {
                Log.d(TAG,"Size"+String.valueOf(listOfData.size()));
                return listOfData.size();
            }else return 0;

        }
        }
        }
