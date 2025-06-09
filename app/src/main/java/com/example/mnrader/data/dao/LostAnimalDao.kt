package com.example.mnrader.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mnrader.data.entity.LostAnimalEntity

@Dao
interface LostAnimalDao {
    
    @Query("SELECT * FROM lost_animals ORDER BY id")
    suspend fun getAllLostAnimals(): List<LostAnimalEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animals: List<LostAnimalEntity>)
    
    @Query("DELETE FROM lost_animals")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM lost_animals")
    suspend fun getCount(): Int
} 