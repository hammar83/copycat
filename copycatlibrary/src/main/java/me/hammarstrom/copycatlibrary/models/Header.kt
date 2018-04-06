package me.hammarstrom.copycatlibrary.models

import com.google.gson.annotations.SerializedName

data class Header(
        @SerializedName("Accept-Charset")
        val acceptCharset: String?,
        @SerializedName("Authorization")
        val authorization: String?,
        @SerializedName("User-Agent")
        val userAgent: String?
)
