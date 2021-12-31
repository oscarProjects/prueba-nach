package com.oscarvj.pruebanach.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies")
class MovieEntity(
    @ColumnInfo(name = "originalTitle") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "posterPath") val posterPath: String,
    @ColumnInfo(name = "releaseDate") val releaseDate: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMovie")
    var idMovie = 0
}