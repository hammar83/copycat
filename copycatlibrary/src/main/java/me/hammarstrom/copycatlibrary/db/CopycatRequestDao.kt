package me.hammarstrom.copycatlibrary.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.hammarstrom.copycatlibrary.models.CopycatRequest

@Dao
interface CopycatRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(request: CopycatRequest)

    @Query("SELECT * FROM copycatrequest")
    fun getAll(): LiveData<List<CopycatRequest>>

    @Query("DELETE FROM copycatrequest")
    fun deleteAll()

}
