package com.adityakarang.sampahku.model

class PickupModel {
    var id: String = ""
    var nama: String = ""
    var jenis: String = ""
    var berat: String = ""
    var alamat: String = ""
    var timestamp: Long = 0
    var uid: String = ""

    constructor()
    constructor(
        id: String,
        nama: String,
        jenis: String,
        berat: String,
        alamat: String,
        timestamp: Long,
        uid: String
    ) {
        this.id = id
        this.nama = nama
        this.jenis = jenis
        this.berat = berat
        this.alamat = alamat
        this.timestamp = timestamp
        this.uid = uid
    }
}