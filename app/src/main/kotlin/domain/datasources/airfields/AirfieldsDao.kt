package com.dunihuliapps.myglidingassistant.domain.datasources.airfields

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dunihuliapps.myglidingassistant.data.model.Airfield
import kotlinx.coroutines.flow.Flow

@Dao
interface AirfieldsDao {
    @Query("SELECT * FROM airfields ORDER BY id DESC")
    fun getAllAirfields(): Flow<List<Airfield>>

    @Query("SELECT * FROM airfields WHERE id = :airfieldId LIMIT 1")
    suspend fun findById(airfieldId: Long): Airfield?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(airfield: Airfield): Long

    @Delete
    suspend fun delete(airfield: Airfield): Int

    @Update
    suspend fun update(airfield: Airfield): Int

    @Query("UPDATE airfields SET isHome = 0 WHERE id != :exceptId")
    suspend fun clearHomeFlag(exceptId: Long = -1)

    @Transaction
    suspend fun insertAsHome(airfield: Airfield): Long {
        clearHomeFlag()
        return insert(airfield)
    }

    @Transaction
    suspend fun updateAsHome(airfield: Airfield): Int {
        clearHomeFlag(exceptId = airfield.id)
        return update(airfield)
    }
}
