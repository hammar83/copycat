package me.hammarstrom.copycatlibrary.models

import android.arch.persistence.room.Embedded

data class Request(
        val host: String?,
        val path: String?,
        val method: String,
        val url: String,
        @Embedded
        val headers: Header
)
