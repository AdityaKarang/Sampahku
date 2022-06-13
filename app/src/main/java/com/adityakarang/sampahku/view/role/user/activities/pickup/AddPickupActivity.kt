package com.adityakarang.sampahku.view.role.user.activities.pickup

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adityakarang.sampahku.databinding.ActivityPickupAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AddPickupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickupAddBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickupAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnPickup.setOnClickListener {
            validateData()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private var nama = ""
    private var jenis = ""
    private var berat = ""
    private var alamat = ""

    private fun validateData() {
        nama = binding.namaET.text.toString().trim()
        jenis = binding.jenisampahET.text.toString().trim()
        berat = binding.inputberatET.text.toString().trim()
        alamat = binding.inputalamatET.text.toString().trim()

        if (nama.isEmpty()) {
            Toast.makeText(this, "Masukan nama", Toast.LENGTH_SHORT).show()
        }
        if (jenis.isEmpty()) {
            Toast.makeText(this, "Masukan jenis sampah", Toast.LENGTH_SHORT).show()
        }
        if (berat.isEmpty()) {
            Toast.makeText(this, "Masukan berat sampah", Toast.LENGTH_SHORT).show()
        }
        if (alamat.isEmpty()) {
            Toast.makeText(this, "Masukan alamat", Toast.LENGTH_SHORT).show()
        } else {
            addData()
        }
    }

    private fun addData() {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["nama"] = nama
        hashMap["jenis"] = jenis
        hashMap["berat"] = berat
        hashMap["alamat"] = alamat
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${auth.uid}"

        val ref = FirebaseDatabase.getInstance().getReference("Pickup")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Pickup Berhasil Mohon Ditunggu", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AddPickupActivity, PickupActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Gagal menyimpan ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

}