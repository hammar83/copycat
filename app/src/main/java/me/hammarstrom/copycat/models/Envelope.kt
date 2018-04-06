package me.hammarstrom.copycat.models

import com.google.gson.annotations.SerializedName

data class Envelope(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("articles")
    val articles: List<Article>
)
