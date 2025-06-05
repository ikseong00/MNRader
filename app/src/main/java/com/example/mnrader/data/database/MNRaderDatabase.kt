package com.example.mnrader.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mnrader.data.dao.LostAnimalDao
import com.example.mnrader.data.entity.LostAnimalEntity

@Database(
    entities = [LostAnimalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MNRaderDatabase : RoomDatabase() {
    
    abstract fun lostAnimalDao(): LostAnimalDao
    
    companion object {
        @Volatile
        private var INSTANCE: MNRaderDatabase? = null
        
        fun getDatabase(context: Context): MNRaderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MNRaderDatabase::class.java,
                    "mnrader_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 