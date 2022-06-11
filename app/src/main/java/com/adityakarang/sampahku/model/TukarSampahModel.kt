package com.adityakarang.sampahku.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tbl_tukarsampah")
@Parcelize
data class TukarSampahModel(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,

    @ColumnInfo(name = "nama_pengguna")
    var namaPengguna: String,

    @ColumnInfo(name = "jenis_sampah")
    var jenisSampah: String,

    @ColumnInfo(name = "berat")
    var berat: Int = 0,

    @ColumnInfo(name = "harga")
    var harga: Int = 0,




) : Parcelable
