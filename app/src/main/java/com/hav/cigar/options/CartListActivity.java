package com.hav.cigar.options;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.PaymentActivity;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.fragments.ImageListFragment;
import com.hav.cigar.model.MyListData;
import com.hav.cigar.product.ItemDetailsActivity;
import com.hav.cigar.startup.MainActivity;
import com.hav.cigar.utility.Constant;
import com.hav.cigar.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Array;
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

public class CartListActivity extends AppCompatActivity {
    private static final String STRING_IMAGE_SIZE = "",STRING_IMAGE_URI=" ",STRING_IMAGE_DESC=" ",STRING_IMAGE_PRICE=" ";
    String TAG = "Cart List";
    public static String STRING_IMAGE_Quantity;
    public String stringImageUri, ImgTitle, ImgDesc, ImgPrice,quantity;
    int position,product_id;
    private static Context mContext;
    float pay;
   public static TextView payment,tvPayment;
    private String Imgsize;
    private String order_ids="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        mContext = CartListActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        payment = findViewById(R.id.paymentAmount);
        tvPayment = findViewById(R.id.text_payment);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(STRING_IMAGE_URI);
            position = getIntent().getIntExtra(String.valueOf(position), 0);
            ImgTitle = getIntent().getStringExtra(STRING_IMAGE_TITLE);
            quantity = getIntent().getStringExtra(STRING_IMAGE_Quantity);
            ImgDesc = getIntent().getStringExtra(STRING_IMAGE_DESC);
            ImgPrice = getIntent().getStringExtra(STRING_IMAGE_PRICE);
            Imgsize = getIntent().getStringExtra(STRING_IMAGE_SIZE); 
            product_id = getIntent().getIntExtra(STRING_IMAGE_ID, 0);
        }

        //ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
       // ArrayList<String> cartlistImageUri =imageUrlUtils.getCartListImageUri();
        JsonObject js = new JsonObject();
        List<MyListData> listofMycart = new ArrayList<>();
        SharedPreferences sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt("user_id", 0);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.GetCart(id);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                //String msg = js.get("data").getAsJsonObject().get("message").getAsString();
                JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
               // paymentAmt = new float[data.size()+1];
                pay = 0;
                for (int i = 0; i < data.size(); i++) {
                    MyListData my = new MyListData();
                    JsonObject js1 = data.get(i).getAsJsonObject();
                    Log.d(TAG, String.valueOf(data.get(i)));
                    String url = js1.get("image").getAsString();
                    my.setImgId("https://piersoncigars.com/uploads/products/" + js1.get("image").getAsString());
                    my.setDescription(js1.get("quantity").getAsString());
                    my.setPrice(js1.get("total_cost").getAsString());
                    my.setName(js1.get("product_name").getAsString());
                    my.setId(js1.get("id").getAsInt());
                   my.setSize(js1.get("size").getAsString());
                   float paymentAmt=Float.parseFloat(js1.get("total_cost").getAsString());
                    //Log.d(TAG, "NAME" + js1.get("name").getAsString() + "MY" + my);
                    listofMycart.add(my);
                    if(order_ids.equals(""))
                    {
                        order_ids=String.valueOf(js1.get("id").getAsInt());

                    }else {
                        order_ids=order_ids+","+String.valueOf(js1.get("id").getAsInt());;
                    }
                    Log.d(TAG, "list of my cart"+i+" amt "+paymentAmt);
                     pay = paymentAmt + pay;
                    payment.setText(Float.toString(pay));
                    Log.d(TAG,"TOTAL"+paymentAmt+"   "+pay);
                    Constant.ORDER_IDS=order_ids;
                    //Show cart layout based on items
                   // setCartLayout();

                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
                    RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

                    recyclerView.setLayoutManager(recylerViewLayoutManager);
                    recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, listofMycart,paymentAmt));
                }
            }
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString());
            }
        });

     /* setCartLayout();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager recylerViewLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView,listofMycart));*/

        tvPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payAmount=Float.toString(pay);
                startActivity(new Intent(CartListActivity.this, PaymentActivity.class).putExtra("pay",payAmount));
            }
        });
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private List<MyListData> mlistOfMyCart;
        private RecyclerView mRecyclerView;
        float pay=0;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem, mLayoutRemove ;
            public final TextView mTitle,mPrice,mQuantity,msize;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image_cartlist);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item_desc);
                mLayoutRemove = (LinearLayout) view.findViewById(R.id.layout_action1);
              //  mLayoutEdit = (LinearLayout) view.findViewById(R.id.layout_action2);
                mTitle = (TextView)view.findViewById(R.id.CTitle);
                //mDesc = (TextView)view.findViewById(R.id.CDesc);
                msize =(TextView)view.findViewById(R.id.Csize);
               mQuantity = (TextView)view.findViewById(R.id.Cquantity);
                mPrice = (TextView)view.findViewById(R.id.CPrice);
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView,List<MyListData> listofMycart,float payAmt) {
           mlistOfMyCart = listofMycart;
           payAmt = payAmt;
            mRecyclerView = recyclerView;
        }

        @Override
        public CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cartlist_item, parent, false);
            return new CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {
            MyListData mydata = mlistOfMyCart.get(position);
            holder.mTitle.setText(mydata.getName());
            holder.mPrice.setText(mydata.getPrice());
           holder.mQuantity.setText(mydata.getDescription());
            holder.msize.setText(mydata.getSize());
            //Uri uri = Uri.parse(STRING_IMAGE_URI);
            //holder.mImageView.setImageURI(uri);
            // holder.mImageView.setImageResource(Integer.parseInt(STRING_IMAGE_URI));
            //else*/
            Glide.with(mContext).load(mydata.getImgId()).
                  placeholder(R.drawable.beforedawn).error(R.drawable.actdot).into(holder.mImageView);

            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mydata.getImgId());
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    intent.putExtra(STRING_IMAGE_TITLE, mydata.getName());
                    //intent.putExtra(STRING_IMAGE_DESC, mydata.getDescription());
                    intent.putExtra(STRING_IMAGE_PRICE, mydata.getPrice());
                    intent.putExtra(STRING_IMAGE_ID, mydata.getId());
                    mContext.startActivity(intent);

                }
            });

            //Set click action
            holder.mLayoutRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 //   ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                   // imageUrlUtils.removeCartListImageUri(position);
                    Call<JsonObject> call;
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    call = apiInterface.DeleteCart(mydata.getId());
                    call.enqueue(new retrofit2.Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("Remove Cart", "onResponse " + response.body());
                            JsonObject js = response.body();
                            Toast.makeText(mContext,"Item Removed from cart",Toast.LENGTH_LONG).show();
                            //notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("Remove Cart", "onFailure " + call.toString() + t);
                        }
                    });
                    //notifyDataSetChanged();
                    //Decrease notification count
                    MainActivity.notificationCountCart--;

                }
            });

        }

        @Override
        public int getItemCount() {
            return mlistOfMyCart.size();
        }
    }


    protected void setCartLayout(){
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
       // LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        if(MainActivity.notificationCountCart >0){
           // layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.VISIBLE);
            layoutCartPayments.setVisibility(View.VISIBLE);
        }else {
           // layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

          //  Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            //bStartShopping.setOnClickListener(new View.OnClickListener() {
              //  @Override
                //public void onClick(View view) {
                   // finish();
                }
            //});
        //}
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void payment()
    {

    }
}
