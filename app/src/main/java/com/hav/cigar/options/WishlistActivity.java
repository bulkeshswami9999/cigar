package com.hav.cigar.options;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.model.MyListData;
import com.hav.cigar.product.ItemDetailsActivity;
import com.hav.cigar.utility.Constant;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Response;

import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_DESC;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_ID;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_PRICE;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_TITLE;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_URI;

public class WishlistActivity<user_id, user_id1> extends AppCompatActivity {
    private static Context mContext;
    static String TAG = "Wishlist";
    static List<MyListData> listofMywish;
    static APIInterface apiInterface;
    public static int user;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        mContext = WishlistActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
         recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences sp = getSharedPreferences(Constant.MyPREFERENCES, MODE_PRIVATE);
        user = sp.getInt("user_id", 0);
        user = GetWishlist(user,mContext);
    }

    //   ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
    // ArrayList<String> wishlistImageUri =imageUrlUtils.getWishlistImageUri();
    public int GetWishlist(int user,Context mContext) {
        Call<JsonObject> call;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        call = apiInterface.GetWishList(user);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                listofMywish = new ArrayList<>();
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
                            my.setId(js1.get("wishlist_id").getAsInt());
                            my.setProduct_id(js1.get("id").getAsInt());
                            Log.d(TAG, "NAME" + js1.get("name").getAsString() + "MY" + my);
                            listofMywish.add(my);
                            Log.d(TAG, "list of my wishlist");
                        }
                    }

                    RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(WishlistActivity.this);
                    recyclerView.setLayoutManager(recylerViewLayoutManager);
                    recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, listofMywish));
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString() + t);
            }
        });
        return user;
    }

        // Log.d("My Wishlist",""+wishlistImageUri.size());
        /*RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, listofMywish));*/


    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private List<MyListData> listOfMyWish;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final TextView mdesc, mprice, mname;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_wishlist);
                mdesc = (TextView) view.findViewById(R.id.desc);
                mname = (TextView) view.findViewById(R.id.name);
                mprice = (TextView) view.findViewById(R.id.price);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, List<MyListData> listOfMyWish) {
            this.listOfMyWish = listOfMyWish;
            mRecyclerView = recyclerView;
        }

        @Override
        public WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wishlist_item, parent, false);
            return new WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final WishlistActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            MyListData mydata = listOfMyWish.get(position);
            holder.mname.setText(mydata.getName());
            holder.mprice.setText(mydata.getPrice());
            holder.mdesc.setText(mydata.getDescription());
            //holder.mImageView.setImageResource(R.drawable.beforedawn);
            Glide.with(mContext).load(mydata.getImgId()).
                    placeholder(R.drawable.beforedawn).error(R.drawable.actdot).into(holder.mImageView);

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mydata.getImgId());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    intent.putExtra(STRING_IMAGE_TITLE, mydata.getName());
                    intent.putExtra(STRING_IMAGE_DESC, mydata.getDescription());
                    intent.putExtra(STRING_IMAGE_PRICE, mydata.getPrice());
                    intent.putExtra(STRING_IMAGE_ID, mydata.getProduct_id());
                    mContext.startActivity(intent);

                }
            });
            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    //imageUrlUtils.removeWishlistImageUri(position);
                    Call<JsonObject> call;
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    call = apiInterface.delete_whishlist(mydata.getId());
                    call.enqueue(new retrofit2.Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("Remove Wishlist", "onResponse " + response.body());
                            JsonObject js = response.body();
                            Toast.makeText(mContext,"Item Removed from wishlist",Toast.LENGTH_LONG).show();
                            //WishlistActivity im = new WishlistActivity();
                           // im.GetWishlist(im.user,mContext);
                            //holder.itemView.setVisibility(View.GONE);
                            //holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                            //notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("Remove wishlist", "onFailure " + call.toString() + t);
                        }
                    });
                }
            });
        }
            @Override
            public int getItemCount () {
                return listOfMyWish.size();
            }
        }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return true;
        }
    }
