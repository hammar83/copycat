package me.hammarstrom.copycatlibrary.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.hammarstrom.copycatlibrary.models.CopycatRequest

@Database(entities = [CopycatRequest::class], version = 1, exportSchema = false)
abstract class CopycatDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "copycat.db"
        @Volatile private var INSTANCE: CopycatDatabase? = null

        fun getInstance(context: Context) =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        CopycatDatabase::class.java,
                        DB_NAME
                ).build()
    }

    abstract fun copycatRequestDao(): CopycatRequestDao

}
