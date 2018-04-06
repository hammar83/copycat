package me.hammarstrom.copycatlibrary.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class CopycatRequest(
        @PrimaryKey(autoGenerate = false)
        val hash: String,
        @Embedded
        val request: Request,
        val body: String
)
