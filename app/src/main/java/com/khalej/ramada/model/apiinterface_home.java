package com.khalej.ramada.model;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface apiinterface_home {


    @FormUrlEncoded
    @POST("api/login")
    Call<contact_general_user> getcontacts_login(@Field("email") String kayWord, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/contacts")
    Call<ResponseBody> CallUs(@Field("name") String name, @Field("email") String address,
                              @Field("message") String message);

    @FormUrlEncoded
    @POST("api/forget/password")
    Call<Reset>getcontacts_ResetPassword(@Field("email") String kayWord);


    @FormUrlEncoded
    @POST("api/forget/password/new")
    Call<Reset>getcontacts_updatePassword(@Field("email") String kayWord,@Field("password") String password);

    @FormUrlEncoded
    @POST("api/forget/password/reset")
    Call<Reset>getcontacts_tokenPassword(@Field("email") String kayWord,@Field("token") String password);


    @GET("api/general")
    Call<contact_general_> getcontacts_g(@Query("lang") String lang);



    @FormUrlEncoded
    @POST("api/register")
    Call<contact_general_user> getcontacts_newaccount(@Field("name") String name, @Field("password") String password, @Field("email") String address,
                                                      @Field("phone") String phone,
                                                      @Field("type") String type);
  @FormUrlEncoded
    @POST("api/orders")
    Call<ResponseBody> content_addOrder(@HeaderMap Map<String, String> headers, @Field("user_id") String user_id, @Field("card_id") String card_id
    );


    @GET("api/profile")
    Call<contact_general_userData> user_profile(@HeaderMap Map<String, String> headers);

    @GET("api/countries")
    Call <contact_country> getCountrys();

    @GET("api/countries/{country_id}/cities")
    Call <contact_country> getCities(@Path("country_id") String country_id);

    @FormUrlEncoded
    @POST("api/shipments/calculator")
    Call<calculate_price> content_calculate(@HeaderMap Map<String, String> headers,
                                            @Field("country_from") String country_from, @Field("city_from") String city_from,
                                            @Field("country_to") String country_to,@Field("city_to")String city_to,
                                            @Field("type")String type,@Field("quantity")int quantity,@Field("weight") double weight);

    @GET("api/branches")
    Call<branches> getbranches();

    @FormUrlEncoded
    @POST("api/feedback")
    Call<ResponseBody> feedback(@Field("rate") String rate, @Field("comment") String comment);

    @FormUrlEncoded
    @PATCH("api/profile/update")
    Call<ResponseBody> changeName(@HeaderMap Map<String, String> headers, @Field("name") String name);
    @FormUrlEncoded
    @PATCH("api/profile/update")
    Call<ResponseBody> changePhone(@HeaderMap Map<String, String> headers, @Field("phone") String phone);
    @FormUrlEncoded
    @PATCH("api/profile/update")
    Call<ResponseBody> changeEmail(@HeaderMap Map<String, String> headers, @Field("email") String email);

    @FormUrlEncoded
    @POST("api/shipments/track")
    Call<Track> track(@HeaderMap Map<String, String> headers, @Field("track_code") String track_code);

    @FormUrlEncoded
    @POST("api/shipments/pickup")
    Call<ResponseBody> add_pickup(@HeaderMap Map<String, String> headers, @Field("day") String day,
                                  @Field("time") String time,@Field("size")String size,@Field("quantity") int quantity,
                                  @Field("weight") double weight, @Field("description")String description,
                                  @Field("address_id") String address_id);

    @FormUrlEncoded
    @POST("api/profile/addresses")
    Call<ResponseBody> add_newAddress(@HeaderMap Map<String, String> headers, @Field("address") String address,
                                      @Field("building_number") int building_number,@Field("floor")int floor,
                                      @Field("flat_number") int flat_number,
                                      @Field("additional_info") String additional_info, @Field("landline_number")String landline_number,
                                      @Field("type") String type,@Field("is_primary") int is_primary ,
                                      @Field("latitude") double latitude,@Field("longitude") double longitude,
                                      @Field("city_id") String city_id);

    @GET("api/profile/addresses")
    Call<contact_address> user_address(@HeaderMap Map<String, String> headers);

    @GET("api/profile/history")
    Call<contact_charges> myCharges(@HeaderMap Map<String, String> headers);

    @DELETE("/api/profile/addresses/{id}/delete")
    Call<ResponseBody> deleteAddress(@HeaderMap Map<String, String> headers,@Path("id") String id);

    @FormUrlEncoded
    @POST("api/shipments")
    Call<contact_charge> make_charge(@HeaderMap Map<String, String> headers,  @Field("shipment_type") String shipment_type,
                                     @Field("currency") String currency,@Field("declared_value") double declared_value,
                                     @Field("national_id") String national_id,@Field("email") String email,
                                     @Field("quantity") int quantity ,@Field("weight") double weight,
                                     @Field("description") String description ,@Field("address_id") String address_id,
                                     @Field("receive_address_id") String receive_address_id);


    @GET("api/shipments/orders?with=address,receive_address")
    Call<Orders> myOrders(@HeaderMap Map<String, String> headers);

    @GET("api/shipments/tasks?with=address,receive_address")
    Call<Orders> myTasks(@HeaderMap Map<String, String> headers);

    @GET("api/shipments/delivered-shipments?with=address,receive_address")
    Call<Orders> myShipments(@HeaderMap Map<String, String> headers);

    @Multipart
    @POST("api/register")
    Call<contact_general_user> getcontacts_newaccount_driver(@Part MultipartBody.Part image, @Part MultipartBody.Part image2
            , @Part MultipartBody.Part image3, @Part("name") RequestBody name, @Part("password") RequestBody password,
                                                             @Part("email") RequestBody address,
                                                             @Part("phone") RequestBody phone,
                                                             @Part("car_type") RequestBody car_type,
                                                             @Part("car_model") RequestBody car_model,
                                                             @Part("type") RequestBody type);


}

