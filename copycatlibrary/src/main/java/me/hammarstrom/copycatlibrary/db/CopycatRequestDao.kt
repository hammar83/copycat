package me.hammarstrom.copycatlibrary.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import me.hammarstrom.copycatlibrary.models.CopycatRequest

@Dao
interface CopycatRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(request: CopycatRequest)

}
