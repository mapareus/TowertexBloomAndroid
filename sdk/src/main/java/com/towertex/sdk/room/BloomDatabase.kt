package com.towertex.sdk.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.towertex.sdk.room.model.UserInput
import com.towertex.sdk.room.service.BloomDao

@Database(
    entities = [
        UserInput::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BloomDatabase: RoomDatabase() {
    abstract val dao: BloomDao

    companion object {
        private const val DATABASE_NAME = "bloomDatabase"

        fun buildDatabase(context: Context): BloomDatabase = Room
            .databaseBuilder(context, BloomDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}