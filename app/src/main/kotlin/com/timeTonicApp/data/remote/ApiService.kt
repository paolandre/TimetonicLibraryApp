 package com.timeTonicApp.data.remote

 import com.timeTonicApp.data.model.OauthKeyResponse
 import com.timeTonicApp.data.model.*
 import retrofit2.Call
 import retrofit2.http.*

 interface ApiService {

     @FormUrlEncoded
     @POST("/live/api.php")
     fun createAppKey(
         @Field("req") req: String,
         @Field("appname") appName: String)
     : Call<AppKeyResponse>

     @FormUrlEncoded
     @POST("/live/api.php")
     fun createOAuthKey(
         @Field("login") login: String,
         @Field("pwd") password: String,
         @Field("appkey") appKey: String
     ): Call<OauthKeyResponse>

     @FormUrlEncoded
     @POST("/live/api.php")
     fun createSessionKey(
         @Field("version") version: String,
         @Field("req") req: String,
         @Field("o_u") oauthUserId: String,
         @Field("u_c") userId: String,
         @Field("oauthkey") oauthKey: String
     ): Call<SessionKeyResponse>


     @FormUrlEncoded
     @POST("/live/api.php")
     fun getUserInfo(
         @Field("o_u") oauthUser: String,
         @Field("u_c") userCode: String,
         @Field("sesskey") sessionKey: String
     ): Call<UserInfoResponse>
 }
