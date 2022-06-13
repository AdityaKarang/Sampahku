package com.adityakarang.sampahku.model

class KategoriModel {

    var id: String = ""
    var kategori: String = ""
    var timestamp: Long = 0
    var uid: String = ""

    constructor()
    constructor(id: String, kategori: String, timestamp: Long, uid: String) {
        this.id = id
        this.kategori = kategori
        this.timestamp = timestamp
        this.uid = uid
    }
}