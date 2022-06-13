package com.adityakarang.sampahku.view.role.admin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.adapter.KategoriAdapter
import com.adityakarang.sampahku.databinding.ActivityInfomasiSampahBinding
import com.adityakarang.sampahku.model.KategoriModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class InfomasiSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfomasiSampahBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var kategoriArrayList: ArrayList<KategoriModel>
    private lateinit var adapter: KategoriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomasiSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        loadKategori()

        setAction()
    }

    private fun loadKategori() {

        kategoriArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Kategori")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                kategoriArrayList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(KategoriModel::class.java)

                    kategoriArrayList.add(model!!)
                }
                adapter = KategoriAdapter(this@InfomasiSampahActivity, kategoriArrayList)
                binding.listkategori.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setAction() {
        binding.apply {
            kategoriAdd.setOnClickListener {
                startActivity(
                    Intent(
                        this@InfomasiSampahActivity,
                        KategoriActivity::class.java
                    ).apply {
                        startActivity(this)
                    })
            }
        }
    }
}