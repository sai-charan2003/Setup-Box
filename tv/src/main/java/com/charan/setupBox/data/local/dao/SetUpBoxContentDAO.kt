package com.charan.setupBox.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.charan.setupBox.data.local.entity.SetupBoxContent
import kotlinx.coroutines.flow.Flow

@Dao
interface SetUpBoxContentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(setupBoxContent: SetupBoxContent)

    @Query("SELECT * FROM SETUPBOXCONTENT")
    fun getAllData(): Flow<List<SetupBoxContent>>

    @Query("SELECT * FROM SETUPBOXCONTENT")
    fun getAllDataNonLiveData(): List<SetupBoxContent>

    @Query("DELETE FROM SETUPBOXCONTENT WHERE id=:id")
    fun deleteById(id:Int)
    @Update
    fun update(setupBoxContent: SetupBoxContent)

    @Query("DELETE FROM SetupBoxContent")
    fun clearAllData()
}