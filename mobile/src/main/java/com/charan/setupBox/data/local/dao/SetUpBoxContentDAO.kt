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

    @Query("SELECT * FROM setupBoxContent")
    fun getAllData(): Flow<List<SetupBoxContent>>

    @Query("SELECT * FROM setupBoxContent WHERE id=:id")
    fun getDataById(id:Int): SetupBoxContent

    @Update
    fun Update(setupBoxContent: SetupBoxContent)

    @Query("DELETE FROM setupBoxContent WHERE id=:id")
    fun deleteDataById(id:Int)
    @Query("SELECT * FROM SETUPBOXCONTENT")
    fun getAllDataNonLiveData(): List<SetupBoxContent>

    @Query("DELETE FROM SETUPBOXCONTENT WHERE id=:id")
    fun deleteById(id:Int)

    @Query("SELECT DISTINCT(app_Package) FROM setupBoxContent")
    fun selectDistinctAppPackage():List<String?>
    @Update
    fun update(setupBoxContent: SetupBoxContent)




}