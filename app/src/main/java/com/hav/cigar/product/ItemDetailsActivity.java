package com.hav.cigar.product;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.R;
import com.hav.cigar.api.APIClient;
import com.hav.cigar.api.APIInterface;
import com.hav.cigar.fragments.ImageListFragment;
import com.hav.cigar.fragments.ViewPagerActivity;
import com.hav.cigar.notification.NotificationCountSetClass;
import com.hav.cigar.options.CartListActivity;
import com.hav.cigar.options.WishlistActivity;
import android.widget.AdapterView.OnItemSelectedListener;
import com.hav.cigar.startup.MainActivity;
import com.hav.cigar.utility.Constant;
import com.hav.cigar.utility.ImageUrlUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_DESC;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_ID;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_POSITION;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_PRICE;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_TITLE;
import static com.hav.cigar.fragments.ImageListFragment.STRING_IMAGE_URI;

public class ItemDetailsActivity extends AppCompatActivity {
    private static final String STRING_IMAGE_QUANTITY = "1";
    private static final String STRING_IMAGE_SIZE ="5*5" ;
    private static final String STRING_IMAGE_CATEGORY_ID ="2" ;
    int position, product_id,category_id;
    String TAG = "ItemDetails",quantity,size;
    AutoCompleteTextView acTextView;
    ElegantNumberButton elegant;
    String stringImageUri, ImgTitle, ImgDesc, ImgPrice;
    TextView price, ITitle, IDesc;
    ImageView mImgWish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        SimpleDraweeView mImageView = (SimpleDraweeView) findViewById(R.id.image1);
        TextView textViewAddToCart = (TextView) findViewById(R.id.text_action_bottom1);
        TextView textViewBuyNow = (TextView) findViewById(R.id.text_action_bottom2);
        TextView textViewWishlist = (TextView) findViewById(R.id.text_action3);
        elegant = (ElegantNumberButton) findViewById(R.id.elegant);
        price = (TextView) findViewById(R.id.Iprice);
        ITitle = (TextView) findViewById(R.id.Ititle);
        IDesc = (TextView) findViewById(R.id.Idesc);
        mImgWish = (ImageView) findViewById(R.id.wishImg);
        //Getting image uri from previous screen
        if (getIntent() != null) {
            stringImageUri = getIntent().getStringExtra(STRING_IMAGE_URI);
            position = getIntent().getIntExtra(STRING_IMAGE_URI, 0);
            ImgTitle = getIntent().getStringExtra(STRING_IMAGE_TITLE);
            ImgDesc = getIntent().getStringExtra(STRING_IMAGE_DESC);
            ImgPrice = getIntent().getStringExtra(STRING_IMAGE_PRICE);
            product_id = getIntent().getIntExtra(STRING_IMAGE_ID, 0);
            category_id = getIntent().getIntExtra(STRING_IMAGE_CATEGORY_ID, 0);
        }
        List<String> allNames = new ArrayList<String>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.GetSize(category_id);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse " + response.body());
                JsonObject js = response.body();
                if (js.get("status").getAsInt() == 200) {
                    if (js.get("data") != null) {
                        //JsonArray data = js.getAsJsonObject("data").getAsJsonArray("message");
                        JsonArray data = js.get("data").getAsJsonObject().get("message").getAsJsonArray();
                        for (int i = 0; i < data.size(); i++) {
                            JsonObject actor = data.get(i).getAsJsonObject();
                            String s1 = actor.get("size").getAsString();
                            Log.d(TAG, s1);
                            allNames.add(s1);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure " + call.toString() + t);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, allNames);
        //Find TextView control
        ImageView drop = (ImageView)findViewById(R.id.drop);
        acTextView = (AutoCompleteTextView) findViewById(R.id.size);
        //Set the number of characters the user must type before the drop down list is shown
        //acTextView.setThreshold(0);
        acTextView.setAdapter(adapter);
        acTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {acTextView.showDropDown();}
        });
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acTextView.showDropDown();
            }
        });
        size = acTextView.getText().toString();
        Uri uri = Uri.parse(stringImageUri);
        mImageView.setImageURI(uri);
        final Uri uri1 = Uri.parse(ImgTitle);
        String Utitle = uri1.toString();
        ITitle.setText(Utitle);
        final Uri uri3 = Uri.parse(ImgPrice);
        String UPrice = uri3.toString();
        price.setText(UPrice);
        final Uri uri2 = Uri.parse(ImgDesc);
        String Udesc = uri2.toString();
        IDesc.setText(Udesc);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, ViewPagerActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("STRING_IMAGE_URI", stringImageUri);
                startActivity(intent);
            }
        });

        textViewAddToCart.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     if(acTextView.getText()!=null&& acTextView.getText().toString().isEmpty())
                                                         Toast.makeText(ItemDetailsActivity.this,"Select size of the Cigar",Toast.LENGTH_LONG).show();

                                                     else
                                                         AddCart();
                //Toast.makeText(ItemDetailsActivity.this, "Item added to cart.", Toast.LENGTH_SHORT).show();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
            }
        });

        textViewBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActivity.this, CartListActivity.class);
                intent.putExtra(STRING_IMAGE_URI, stringImageUri);
                intent.putExtra(STRING_IMAGE_POSITION, position);
                intent.putExtra(STRING_IMAGE_TITLE, ImgTitle);
                intent.putExtra(STRING_IMAGE_DESC, ImgDesc);
                intent.putExtra(STRING_IMAGE_PRICE, price.getText().toString());
                intent.putExtra(STRING_IMAGE_QUANTITY,elegant.getNumber());
                intent.putExtra(STRING_IMAGE_SIZE,size);
                intent.putExtra(STRING_IMAGE_ID,product_id);
                Log.d(TAG,STRING_IMAGE_ID+"   "+STRING_IMAGE_TITLE);

                // ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                //imageUrlUtils.addCartListImageUri(stringImageUri);
                if(acTextView.getText()!=null&& acTextView.getText().toString().isEmpty())
                    Toast.makeText(ItemDetailsActivity.this,"Select size of the Cigar",Toast.LENGTH_LONG).show();

                else{
                    AddCart();
                MainActivity.notificationCountCart++;
                NotificationCountSetClass.setNotifyCount(MainActivity.notificationCountCart);
                startActivity(intent);
                }
                //startActivity(new Intent(ItemDetailsActivity.this, CartListActivity.class));
               // startActivity(intent);
            }
        });
        String y = price.getText().toString();
        //Float pric = Float.parseFloat(y);
        elegant.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegant.setRange(1, 50);
                String num = elegant.getNumber();
               // float x = Float.parseFloat(num);
                float z = Float.parseFloat(num) * Float.parseFloat(y);
                String s = Float.toString(z);
                price.setText(s);
            }
        });
    }

    public void addTextWish(View view) {

        JsonObject js = new JsonObject();
        SharedPreferences sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt("user_id", 0);
        mImgWish.setImageResource(R.drawable.ic_favorite_black_18dp);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<JsonObject> call = apiInterface.AddWishList(id, product_id);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("ItemDetails-Wishlist", "onResponse " + response.body());
                JsonObject js = response.body();
                //String msg = js.get("data").getAsJsonObject().get("message").getAsString();
                //Toast.makeText(cont
                if (js.getAsJsonPrimitive("status").getAsInt() == 200)
                    Toast.makeText(getApplicationContext(), "Item added to wishlist.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Item already in wishlist", Toast.LENGTH_SHORT).show();
                Constant.hideDialog();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ItemDetails-wishlisst", "onFailure " + call.toString());
            }
        });
    }
    public void AddCart()
    {
        JsonObject js = new JsonObject();
        SharedPreferences sharedpreferences = getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt("user_id", 0);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        int quantity = Integer.parseInt(elegant.getNumber());
        String price1 = price.getText().toString();
        String size = acTextView.getText().toString();
        Call<JsonObject> call = apiInterface.AddCart(product_id, id, quantity, price1,size);
        call.enqueue(new retrofit2.Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("ItemDetails-cart", "onResponse " + response.body());
                JsonObject js = response.body();
                String msg = js.get("data").getAsJsonObject().get("message").getAsString();
                //Toast.makeText(cont
                if (js.getAsJsonPrimitive("status").getAsInt() == 200)
                    Toast.makeText(getApplicationContext(), "Item added to Cart", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Item already in cart", Toast.LENGTH_SHORT).show();
                Constant.hideDialog();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ItemDetails-cart", "onFailure " + call.toString());
            }
        });

    }
}
