package com.charan.setupBox.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.charan.setupBox.data.local.dao.SetUpBoxContentDAO
import com.charan.setupBox.data.local.entity.SetupBoxContent

@Database(entities = arrayOf(SetupBoxContent::class), version = 3,exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    abstract fun setupBoxRepo(): SetUpBoxContentDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase?=null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MobileAppDatabase"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)

                    .fallbackToDestructiveMigrationFrom().build()
                INSTANCE = instance
                instance
            }
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create new table with nullable id
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS setupBoxContent_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT NULL,
                channelLink TEXT DEFAULT NULL,
                channelName TEXT DEFAULT NULL,
                channelPhoto TEXT DEFAULT NULL,
                Category TEXT DEFAULT NULL,
                app_Package TEXT DEFAULT NULL
            )
        """)

                // Copy data from old table to new table
                database.execSQL("""
            INSERT INTO setupBoxContent_new (id, channelLink, channelName, channelPhoto, Category, app_Package)
            SELECT id, channelLink, channelName, channelPhoto, Category, app_Package FROM setupBoxContent
        """)

                // Remove the old table
                database.execSQL("DROP TABLE setupBoxContent")

                // Rename new table to old table name
                database.execSQL("ALTER TABLE setupBoxContent_new RENAME TO setupBoxContent")
            }
        }
        val MIGRATION_2_3 = object : Migration(2,3){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE setupBoxContent ADD COLUMN uuid TEXT DEFAULT NULL")
                db.execSQL("ALTER TABLE setupBoxContent ADD COLUMN email TEXT DEFAULT NULL")
            }

        }



    }


}