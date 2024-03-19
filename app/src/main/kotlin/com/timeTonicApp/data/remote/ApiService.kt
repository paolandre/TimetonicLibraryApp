/* package com.timeTonicApp.data.remote

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/createAppkey")
    suspend fun createAppKey(@Field("appname") appName: String): AppKeyResponse

    @FormUrlEncoded
    @POST("/createOauthkey")
    suspend fun createOAuthKey(
        @Field("login") login: String,
        @Field("pwd") password: String,
        @Field("appkey") appKey: String
    ): OAuthKeyResponse

    @FormUrlEncoded
    @POST("/createSesskey")
    suspend fun createSessionKey(
        @Field("o_u") oauthUser: String,
        @Field("oauthkey") oauthKey: String
    ): SessionKeyResponse

    @FormUrlEncoded
    @POST("/getAllBooks")
    suspend fun getAllBooks(
        @Field("o_u") oauthUser: String,
        @Field("sesskey") sessionKey: String
    ): Response<BooksResponse>
}
*/