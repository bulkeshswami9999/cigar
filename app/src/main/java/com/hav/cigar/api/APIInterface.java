package com.hav.cigar.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hav.cigar.model.NotificationApiResponse;
import com.hav.cigar.model.NotificationListingApi;
import com.hav.cigar.model.Payment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @FormUrlEncoded
    @POST("customer_api.php?apicall=customer_signup")
    Call<JsonObject> signUp(@Field("mob") String mob,@Field("full_name") String full_name,@Field("email_id") String email_id,@Field("password") String password,
                            @Field("dob") String dob);

    @FormUrlEncoded
    @POST("customer_api.php?apicall=customer_login")
    Call<JsonObject> customerLogin(@Field("email_id") String email_id,@Field("password") String password,@Field("token") String token);
    @FormUrlEncoded
    @POST("product_api.php?apicall=typeof_cigars_smoke")
    Call<JsonObject> InAnswer(@Field("customer_id")String customer_id,@Field("answer")String answer);

    @FormUrlEncoded
    @POST("driver_api.php?update_driver")
    Call<JsonObject> signUpDriver(@Field("mob") String mob,@Field("full_name") String full_name,@Field("dob") String dob
            , @Field("address_1")String address,@Field("address_2")String address2,@Field("country")String country,@Field("state")String state,
                                  @Field("city")String city,@Field("pin_code")String pin_code,@Field("licence")String licence);

    @FormUrlEncoded
    @POST("partner_api.php?apicall=partner_signup")
    Call<JsonObject> signUpPartner(@Field("mob") String mob,@Field("full_name") String full_name,@Field("email_id") String email_id,@Field("password") String password,
                                   @Field("dob") String dob,@Field("address_1")String address_1,@Field("address_2")String address2
            ,@Field("country")String country,@Field("state")String state,@Field("city")String city
            ,@Field("pin_code")String pin_code);
    @FormUrlEncoded
    @POST("product_api.php?apicall=product_list")
    Call<JsonObject> product_list(@Field("category_id") String category_id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=add_whishlist")
    Call<JsonObject> AddWishList(@Field("customer_id") int customer_id,@Field("product_id") Integer product_id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=get_whishlist")
    Call<JsonObject> GetWishList(@Field("customer_id") int customer_id);
   // @FormUrlEncoded
    @GET("product_api.php?apicall=get_cigarofmonth")
    Call<JsonObject> GetCigarOfMonth();
    @FormUrlEncoded
    @POST("product_api.php?apicall=product_list")
    Call<JsonObject> GetProductList(@Field("category_id")int category_id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=add_cart")
    Call<JsonObject> AddCart(@Field("product_id")int product_id,@Field("customer_id")int customer_id,@Field("quantity")int quantity,@Field("cost")String cost,@Field("size")String size);
    @FormUrlEncoded
    @POST("product_api.php?apicall=get_cart_details")
    Call<JsonObject> GetCart(@Field("customer_id")int customer_id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=update_cart")
    Call<JsonObject> UpdateCart(@Field("quantity")int quantity,@Field("cost")float cost,@Field("id")int id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=delete_cart")
    Call<JsonObject> DeleteCart(@Field("id")int id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=product_size")
    Call<JsonObject> GetSize(@Field("category_id")int category_id);
    @FormUrlEncoded
    @POST("product_api.php?apicall=product_search")
    Call<JsonObject>product_search(@Field("searchView")String searchView);
    @FormUrlEncoded
    @POST("product_api.php?apicall=delete_whishlist")
    Call<JsonObject>delete_whishlist(@Field("id")int id);
    @FormUrlEncoded
    @POST("customer_api.php?apicall=check_email_exists")
    Call<JsonObject>check_email(@Field("email_id")String email_id);
    @FormUrlEncoded
    @POST("customer_api.php?apicall=change_password")
    Call<JsonObject>change_password(@Field("id")int id,@Field("password")String password);

    @FormUrlEncoded
    @POST("payment/index.php")
    Call<Payment> doPayment(@Field("card-number") String cardnumber,
                            @Field("year") String year,
                            @Field("month") String month,
                            @Field("amount") String amount ,
                            @Field("pay_now") String pay_now,
                            @Field("cvv") String cvv,
                            @Field("product_ids") String product_ids ,
                            @Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("push_customer.php?apicall=order_placed")
    Call<NotificationApiResponse> sendNotification(@Field("id") String id);

    @FormUrlEncoded
    @POST("product_api.php?apicall=completed_order_list")
    Call<NotificationListingApi> getOrderList(@Field("customer_id") String customer_id);

}
