package com.adityakarang.sampahku.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adityakarang.sampahku.model.TukarSampahModel


@Dao
interface DbDao {

    @Query("SELECT * FROM tbl_tukarsampah")
    fun getallSampah(): LiveData<List<TukarSampahModel>>

    @Query("SELECT SUM(harga) FROM tbl_tukarsampah")
    fun getsaldoSampah(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertdataSampah(modelDatabases: TukarSampahModel)

    @Query("DELETE FROM tbl_tukarsampah WHERE uid= :uid")
    fun singledataDLT(uid: Int)
}