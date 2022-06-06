package com.adityakarang.sampahku.view.role.admin.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adityakarang.sampahku.databinding.ActivityKategoriBinding
import com.adityakarang.sampahku.view.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class KategoriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKategoriBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.kategoribtn.setOnClickListener {
            validateData()
        }

    }

    private var kategori =""

    private fun validateData() {

        kategori = binding.kategoriET.text.toString().trim()

        if (kategori.isEmpty()){
            Toast.makeText(this, "Masukan Kategori", Toast.LENGTH_SHORT).show()
        }
        else{
            addKategori()
        }
    }

    private fun addKategori() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["kategori"] = kategori
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${auth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Kategori")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Berhasil Menambahkan kategori", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Gagal menyimpan ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }


}