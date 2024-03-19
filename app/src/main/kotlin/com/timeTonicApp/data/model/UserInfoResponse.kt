package com.timeTonicApp.data.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    val status: String,
    val sstamp: Long,
    val userInfo: UserInfo,
    val createdVNB: String,
    val req: String
)

data class UserInfo(
    val firstTime: Boolean,
    val uimgStamp: Int,
    val nbBooks: Int,
    val licence: String,
    val companyAccountPlan: String,
    val u_c: String,
    val userPrefs: UserPrefs,
    val rights: UserRights,
    val colors: UserColors,
    val projectID: Long
)

data class UserPrefs(
    val licenceId: String,
    val openGrand: Boolean,
    val NotifMobile: String,
    val soundNotifications: Boolean,
    val syncWithHubic: Boolean,
    val globalAdd: Boolean,
    val globalCalendar: Boolean,
    val globalSearch: Boolean,
    val tz: String,
    val lang: String,
)

data class UserRights(
    val canCreateWorkspace: Boolean,
    val formPro: Boolean
)

data class UserColors(
    @SerializedName("defaut") val default: String,
    @SerializedName("bleu-violet") val blueViolet: String,
    @SerializedName("gray") val gray: String,
)
