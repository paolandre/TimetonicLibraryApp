package com.timeTonicApp.data.model
data class BookMessagesResponse(
    val status: String,
    val sstamp: Long,
    val b_c: String,
    val b_o: String,
    val bookMessages: List<Message>,
    val createdVNB: String,
    val req: String
)

data class Message(
    val smid: Long,
    val u_c: String,
    val created: Long,
    val lastModified: Long,
    val msg: String,
    val msgType: String,
    val msgMethod: String,
    val linkedRowId: String?,
    val linkedTabId: String?,
)