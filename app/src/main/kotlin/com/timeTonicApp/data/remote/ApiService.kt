 package com.timeTonicApp.data.remote

 import com.timeTonicApp.data.model.*
 import retrofit2.Call
 import retrofit2.http.*

 interface ApiService {

     @FormUrlEncoded
     @POST("/live/api.php")
     fun createAppKey(
         @Field("req") req: String,
         @Field("appname") appName: String,
     ): Call<AppKeyResponse>

     @FormUrlEncoded
     @POST("/live/api.php")
     fun createOauthkey(
         @Field("req") req: String,
         @Field("login") login: String,
         @Field("pwd") pwd: String,
         @Field("appkey") appkey: String,
     ): Call<AuthKeyResponse>

     @FormUrlEncoded
     @POST("/live/api.php")
     fun createSesskey(
         @Field("req") req: String,
         @Field("o_u") o_u: String,
         @Field("oauthkey") oauthkey: String,
     ): Call<CreateSessKeyResponse>

 }