package com.oscarvj.pruebanach.di.module

import android.content.Context
import androidx.room.Room
import com.oscarvj.pruebanach.data.database.PruebaNachDB
import com.oscarvj.pruebanach.data.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): PruebaNachDB{
        return Room.databaseBuilder(
            appContext,
            PruebaNachDB::class.java,
            "PruebaNachDB"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideGsDataDao(gsDataBase: PruebaNachDB): MovieDao {
        return gsDataBase.movieDao()
    }
}