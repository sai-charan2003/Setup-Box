package com.charan.setupBox.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(SetupBoxContent::class), version = 3,exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    abstract fun setupBoxRepo(): SetUpBoxContentRepo

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
                    .addMigrations(MIGRATION_2_3)
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


    }


}