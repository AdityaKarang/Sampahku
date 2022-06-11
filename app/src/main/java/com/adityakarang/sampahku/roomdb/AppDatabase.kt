package com.adityakarang.sampahku.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adityakarang.sampahku.model.TukarSampahModel
import com.adityakarang.sampahku.roomdb.DbDao


@Database(entities = [TukarSampahModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dbDao(): DbDao?
}