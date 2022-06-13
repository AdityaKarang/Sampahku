package com.adityakarang.sampahku.view.role.user.navigation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.adityakarang.sampahku.database.DbClient
import com.adityakarang.sampahku.model.TukarSampahModel
import com.adityakarang.sampahku.roomdb.DbDao

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var totalSaldo: LiveData<Int>
    var dataBank: LiveData<List<TukarSampahModel>>
    var databaseDao: DbDao?


    init {
        databaseDao = DbClient.getInstance(application)?.appDatabase?.dbDao()
        dataBank = databaseDao!!.getallSampah()
        totalSaldo = databaseDao!!.getsaldoSampah()
    }


}

