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

data class BooksResponse(
    val status: String,
    val sstamp: Long,
    val allBooks: BooksInfo
)

data class BooksInfo(
    val nbBooks: Int,
    val nbContacts: Int,
    val contacts: List<Contact>,
    val books: List<Book>
)

data class Contact(
    val u_c: String,
    val lastName: String,
    val firstName: String,
    val sstamp: Long,
    val isConfirmed: Boolean
)

data class Book(
    val invited: Boolean,
    val accepted: Boolean,
    val archived: Boolean,
    val notificationWeb: String,
    val notificationAudio: String,
    val showFpOnOpen: Boolean,
    val sstamp: Long,
    val del: Boolean,
    val hideMessage: String,
    val hideBookMembers: String,
    val description: String?,
    val defaultTemplate: String,
    val isDownloadable: Boolean,
    val canDisableSync: Boolean,
    val b_c: String,
    val b_o: String,
    val cluster: String,
    val tags: String?,
    val langs: String?,
    val contact_u_c: String?,
    val nbNotRead: Int,
    val nbMembers: Int,
    val members: List<Member>,
    val fpForm: FpForm?,
    val lastMsg: LastMsg?,
    val nbMsgs: Int,
    val userPrefs: UserPrefs,
    val ownerPrefs: OwnerPrefs,
    val sbid: Int,
    val lastMsgRead: Long,
    val lastMedia: Long?,
    val favorite: Boolean,
    val order: Int
)

data class Member(
    val u_c: String,
    val invite: String,
    val right: Int,
    val access: Int,
    val hideMessage: String,
    val hideBookMembers: String,
    val apiRight: String
)

data class FpForm(
    val fpid: Int,
    val name: String,
    val lastModified: Long
)

data class LastMsg(
    val smid: Long,
    val uuid: String,
    val sstamp: Long,
)
data class UserPrefs(
    val maxMsgsOffline: Int,
    val syncWithHubic: Boolean,
    val notificationEnabled: String,
    val uCoverLetOwnerDecide: Boolean,
    val uCoverColor: String,
    val uCoverUseLastImg: Boolean,
    val uCoverImg: String?,
    val uCoverType: String,
    val inGlobalSearch: Boolean,
    val inGlobalTasks: Boolean,
    val notifyEmailCopy: Boolean,
    val notifySmsCopy: Boolean,
    val notifyMobile: Boolean,
    val notifyWhenMsgInArchivedBook: Boolean
)

data class OwnerPrefs(
    val fpAutoExport: Boolean,
    val oCoverColor: String,
    val oCoverUseLastImg: Boolean,
    val oCoverImg: String?,
    val oCoverType: String,
    val authorizeMemberBroadcast: Boolean,
    val acceptExternalMsg: Boolean,
    val title: String,
    val notifyMobileConfidential: Boolean
)
