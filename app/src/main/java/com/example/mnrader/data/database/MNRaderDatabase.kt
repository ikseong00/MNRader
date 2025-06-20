package com.example.mnrader.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mnrader.data.dao.LostAnimalDao
import com.example.mnrader.data.dao.ScrapDao
import com.example.mnrader.data.dao.UserDao
import com.example.mnrader.data.entity.LostAnimalEntity
import com.example.mnrader.data.entity.ScrapEntity
import com.example.mnrader.data.entity.UserEntity

@Database(
    entities = [LostAnimalEntity::class, ScrapEntity::class, UserEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MNRaderDatabase : RoomDatabase() {

    abstract fun lostAnimalDao(): LostAnimalDao
    abstract fun scrapDao(): ScrapDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MNRaderDatabase? = null

        fun getDatabase(context: Context): MNRaderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MNRaderDatabase::class.java,
                    "mnrader_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
} 