package com.adityakarang.sampahku.database

import android.content.Context
import androidx.room.Room


class DbClient private constructor(context: Context) {

    var appDatabase: AppDatabase

    companion object {
        private var mInstance: DbClient? = null
        
        fun getInstance(context: Context): DbClient? {
            if (mInstance == null) {
                mInstance = DbClient(context)
            }
            return mInstance
        }
    }

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "banksampah_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}