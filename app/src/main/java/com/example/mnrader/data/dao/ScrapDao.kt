package com.example.mnrader.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.mnrader.data.entity.ScrapEntity

@Dao
interface ScrapDao {
    
    @Query("SELECT * FROM scraps WHERE userEmail = :userEmail AND isScrapped = 1 ORDER BY scrapTimestamp DESC")
    suspend fun getAllScrapsByUser(userEmail: String): List<ScrapEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScrap(scrap: ScrapEntity)
    
    @Query("DELETE FROM scraps WHERE animalId = :animalId AND userEmail = :userEmail")
    suspend fun deleteScrapByAnimalIdAndUser(animalId: String, userEmail: String)
    
    @Query("SELECT * FROM scraps WHERE animalId = :animalId AND userEmail = :userEmail")
    suspend fun getScrapByAnimalIdAndUser(animalId: String, userEmail: String): ScrapEntity?
    
    @Query("UPDATE scraps SET isScrapped = :isScrapped WHERE animalId = :animalId AND userEmail = :userEmail")
    suspend fun updateScrapStatus(animalId: String, userEmail: String, isScrapped: Boolean)
    
    @Query("SELECT EXISTS(SELECT 1 FROM scraps WHERE animalId = :animalId AND userEmail = :userEmail AND isScrapped = 1)")
    suspend fun isScrapExistForUser(animalId: String, userEmail: String): Boolean
} 