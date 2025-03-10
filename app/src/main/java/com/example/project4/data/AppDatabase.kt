package com.example.project4.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class, ProfileEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Auto reset DB kalau versi beda
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
