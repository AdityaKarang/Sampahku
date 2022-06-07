package com.adityakarang.sampahku.view.role.user.activities.tukarsampah

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.adityakarang.sampahku.database.DbClient.Companion.getInstance
import com.adityakarang.sampahku.model.TukarSampahModel
import com.adityakarang.sampahku.roomdb.DatabaseDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers


class TukarSampahViewModel(application: Application) : AndroidViewModel(application) {

    var totalSaldo: LiveData<Int>
    var dataBank: LiveData<List<TukarSampahModel>>
    var databaseDao: DatabaseDao?

    fun addData(
        nama_pengguna: String,
        jenis_sampah: String,
        berat: Int,
        harga: Int,
    ) {
        Completable.fromAction {
            val modelDatabase = TukarSampahModel(
            namaPengguna = nama_pengguna, jenisSampah = jenis_sampah, berat = berat, harga = harga
            )
            databaseDao?.insertdataSampah(modelDatabase)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun deleteDataById(uid: Int) {
        Completable.fromAction {
            databaseDao?.singledataDLT(uid)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
        dataBank = databaseDao!!.getallSampah()
        totalSaldo = databaseDao!!.getsaldoSampah()
    }

}