package com.adityakarang.sampahku.roomdb

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adityakarang.sampahku.model.TukarSampahModel

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM tbl_banksampah")
    fun getallSampah(): LiveData<List<TukarSampahModel>>

    @Query("SELECT SUM(harga) FROM tbl_banksampah")
    fun getsaldoSampah(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertdataSampah(modelDatabases: TukarSampahModel)

    @Query("DELETE FROM tbl_banksampah WHERE uid= :uid")
    fun singledataDLT(uid: Int)

}