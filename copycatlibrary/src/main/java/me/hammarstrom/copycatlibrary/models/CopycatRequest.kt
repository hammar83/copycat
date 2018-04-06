package me.hammarstrom.copycatlibrary.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil

@Entity
data class CopycatRequest(
        @PrimaryKey(autoGenerate = false)
        val hash: String,
        @Embedded
        val request: Request,
        val body: String
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CopycatRequest>() {
            override fun areItemsTheSame(oldItem: CopycatRequest?, newItem: CopycatRequest?): Boolean {
                return oldItem?.hash == newItem?.hash
            }

            override fun areContentsTheSame(oldItem: CopycatRequest, newItem: CopycatRequest): Boolean {
                return oldItem == newItem
            }
        }
    }

}
