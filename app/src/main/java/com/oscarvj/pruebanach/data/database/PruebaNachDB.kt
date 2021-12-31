package com.oscarvj.pruebanach.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oscarvj.pruebanach.constants.Constants
import com.oscarvj.pruebanach.data.database.dao.MovieDao
import com.oscarvj.pruebanach.data.database.model.MovieEntity

@Database(entities = [MovieEntity::class], version = Constants.DB_VERSION, exportSchema = false)
abstract class PruebaNachDB : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}