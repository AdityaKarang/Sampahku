package com.adityakarang.sampahku.view.role.user.navigation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.adityakarang.sampahku.database.DbClient
import com.adityakarang.sampahku.model.TukarSampahModel
import com.adityakarang.sampahku.roomdb.DatabaseDao

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var totalSaldo: LiveData<Int>
    var dataBank: LiveData<List<TukarSampahModel>>
    var databaseDao: DatabaseDao?



    init {
        databaseDao = DbClient.getInstance(application)?.appDatabase?.databaseDao()
        dataBank = databaseDao!!.getallSampah()
        totalSaldo = databaseDao!!.getsaldoSampah()
    }


}

