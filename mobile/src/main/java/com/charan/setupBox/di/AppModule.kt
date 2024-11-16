package com.charan.setupBox.di

import android.content.Context
import com.charan.setupBox.data.local.AppDatabase
import com.charan.setupBox.data.local.dao.SetUpBoxContentDAO
import com.charan.setupBox.data.repository.SetUpBoxRepo
import com.charan.setupBox.data.repository.SupabaseRepo
import com.charan.setupBox.data.repository.impl.SetUpBoxRepoImp
import com.charan.setupBox.data.repository.impl.SupabaseRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    @Provides
    @Singleton
    fun provideSetUpBoxDAO(database: AppDatabase) : SetUpBoxContentDAO {
        return database.setupBoxRepo()
    }

    @Provides
    @Singleton
    fun provideSetupBoxRepo(
        dao: SetUpBoxContentDAO
    ) : SetUpBoxRepo {
        return SetUpBoxRepoImp(dao)
    }

    @Provides
    @Singleton
    fun provideSupabaseRepo(
        setUpBoxRepo: SetUpBoxRepo
    ) : SupabaseRepo {
        return SupabaseRepoImp(setUpBoxRepo)
    }
}