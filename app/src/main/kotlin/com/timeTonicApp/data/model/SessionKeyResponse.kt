package com.timeTonicApp.data.model

data class SessionKeyResponse(
    val status: String,
    val sesskey: String,
    val id: String,
    val restrictions: UserRestrictions,
    val appName: String
)

data class UserRestrictions(
    val carnet_code: String?,
    val carnet_owner: String?,
    val readonly: Boolean,
    val hideTables: Boolean,
    val hideMessages: Boolean,
    val hideEvents: Boolean,
    val internal: Boolean
)