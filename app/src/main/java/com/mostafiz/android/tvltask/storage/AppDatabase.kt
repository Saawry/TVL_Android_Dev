package com.mostafiz.android.tvltask.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mostafiz.android.tvltask.dao.TaskDao
import com.mostafiz.android.tvltask.models.TaskModel
import com.mostafiz.android.tvltask.utils.Constants.DBName
import com.mostafiz.android.tvltask.utils.Converter


@TypeConverters(Converter::class)
@Database(
    entities = [TaskModel::class],
    version =1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao


    companion object {

        private var instance: AppDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): AppDatabase? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DBName
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}