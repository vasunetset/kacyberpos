package com.kacyber.pos.retrofitManager;


import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by Naveen on 5/5/17.
 */
public interface ApiInterface {
    @POST("/loginAdminUser/")
    Call<JsonObject> loginApi(@Body RequestBody requestBody);

    @POST("/logoutPOSUser/")
    Call<JsonObject> logoutApi(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("/getBookingDetailsByEticketOrPin/")
    Call<JsonObject> getTicketDetailsByETicket(@Header("AUTHORIZATION") String Token,
                                               @Field("ticketNumber") String ticketNumber,
                                               @Field("deviceInfo") String deviceInfo);

    @FormUrlEncoded
    @POST("/searchBusFleetsByPOS/")
    Call<JsonObject> searchBusFleets(@Header("AUTHORIZATION") String Token,
                                     @Field("sourceStation") String sourceStation,
                                     @Field("destinationStation") String destinationStation,
                                     @Field("onwarddate") String date,
                                     @Field("filters") String filters);

    @GET("/getStationsForPOS/")
    Call<JsonObject> getCitiesApi(@Header("AUTHORIZATION") String Token);

    @FormUrlEncoded
    @POST("/getBusDetails/")
    Call<JsonObject> getBusDeatils(@Field("id") String busId);

    @FormUrlEncoded
    @POST("getBusSeatsDetails/")
    Call<JsonObject> getBusSeatsDetails(
            @Header("AUTHORIZATION") String Token,
            @Field("sourceStation") String sourceStation,
            @Field("destinationStation") String destinationStation,
            @Field("onwarddate") String date,
            @Field("busFleetId") int busFleetId);

    @FormUrlEncoded
    @POST("checkOrHoldUnholdSeatsByPOS/")
    Call<JsonObject> checkOrHoldUnholdSeats(
            @Header("AUTHORIZATION") String Token,
            @Field("sourceStation") String sourceStation,
            @Field("destinationStation") String destinationStation,
            @Field("bookingOfDate") String bookingOfData,
            @Field("busFleetId") int busFleetId,
            @Field("holdOrNot") int holdOrNot,
            @Field("seatNumber") int seatNumber);

    @POST("unholdAllSeatsByUserByPOS/")
    Call<JsonObject> unholdAllSeatsByUser(@Header("AUTHORIZATION") String Token);

    @FormUrlEncoded
    @POST("bookTicketByPOS/")
    Call<JsonObject> bookTicket(
            @Header("AUTHORIZATION") String Token,
            @Field("busFleetId") int busFleetId,
            @Field("currency") String currency,
            @Field("isDiscountApplicableForSingle") boolean isDiscountApplicable,
            @Field("discountCouponSingle") String discountCouponSingle,
            @Field("bookingOfDate") String bookingDate,
            @Field("passangersDetails") String pass_details,
            @Field("paymentFrom") int paymentFrom,
            @Field("sourceStation") String sourceStation,
            @Field("destinationStation") String destinationStation);

    @FormUrlEncoded
    @POST("setPrintingStatusTrue/")
    Call<JsonObject> printTicket(@Header("AUTHORIZATION") String Token, @Field("bookingId") int bookingId,
                                 @Field("deviceInfo") String deviceInfo);

    @GET("getUserInfo/")
    Call<JsonObject> getUserProfile(@Header("AUTHORIZATION") String Token);

    /*
        @POST("updateUserInfo/")
        Call<JsonObject> getUpdateUserProfile(@Header("AUTHORIZATION") String Token);
    */
    /*   @Multipart
       @POST("updateUserInfo")
       Call<Object> setUserIdentity(@Part MultipartBody.Part body, @Part("userid") RequestBody id);*/

    @Multipart
    @POST("updateUserInfo/")
    Call<JsonObject> updateUserProfile(@Header("AUTHORIZATION") String Token, @PartMap HashMap<String, RequestBody> requestBody);

    @FormUrlEncoded
    @POST("getBookingsDataForPOS/")
    Call<JsonObject> getReportData(@Header("AUTHORIZATION") String Token, @FieldMap HashMap<String, String> hashMa);

    @FormUrlEncoded
    @POST("getInsightDataForPOS/")
    Call<JsonObject> getInsightData(@Header("AUTHORIZATION") String Token, @FieldMap HashMap<String, String> hashMa);


    @GET("getBusFleetsByPOS/")
    Call<JsonObject> getBusFleetsByPOS(@Header("AUTHORIZATION") String Token);


    @FormUrlEncoded
    @POST("getActivityDataForPOS/")
    Call<JsonObject> getActivityDataForPOS(@Header("AUTHORIZATION") String Token, @FieldMap HashMap<String, String> hashMa);

    @FormUrlEncoded
    @POST("applyPromoCode/")
    Call<JsonObject> applyPromoCode(@Header("AUTHORIZATION") String Token, @FieldMap HashMap<String, Object> hashMa);


}
