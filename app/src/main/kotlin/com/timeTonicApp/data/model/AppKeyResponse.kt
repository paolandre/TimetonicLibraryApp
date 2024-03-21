package com.timeTonicApp.data.model

data class AppKeyResponse(
    val status: String,
    val appkey: String,
    val id: String,
    val createdVNB: String,
    val req: String
)

data class AuthKeyResponse(
    val status: String,
    val oauthkey: String,
    val id: String,
    val o_u: String,
    val createdVNB: String,
    val req: String
)

data class CreateSessKeyResponse(
    val status: String,
    val sesskey: String,
    val id: String,
    val restrictions: Restrictions
)

data class Restrictions(
    val carnet_code: String?,
    val carnet_owner: String?,
    val readonly: Boolean,
    val hideTables: Boolean,
    val hideMessages: Boolean,
    val hideEvents: Boolean,
    val internal: Boolean
)
