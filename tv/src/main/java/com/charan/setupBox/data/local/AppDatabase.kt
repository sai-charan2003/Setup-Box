package com.charan.setupBox.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.charan.setupBox.data.local.dao.SetUpBoxContentDAO
import com.charan.setupBox.data.local.entity.SetupBoxContent

@Database(entities = arrayOf(SetupBoxContent::class), version = 4,exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    abstract fun setupBoxDAO(): SetUpBoxContentDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase?=null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                )
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .fallbackToDestructiveMigrationFrom().build()
                INSTANCE = instance
                instance
            }
        }
        private val MIGRATION_2_3= object : Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE setupBoxContent ADD COLUMN app_Package TEXT DEFAULT NULL")
            }

        }
        val MIGRATION_3_4 = object : Migration(3,4){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE setupBoxContent ADD COLUMN uuid TEXT DEFAULT NULL")
                db.execSQL("ALTER TABLE setupBoxContent ADD COLUMN email TEXT DEFAULT NULL")
            }

        }


    }


}