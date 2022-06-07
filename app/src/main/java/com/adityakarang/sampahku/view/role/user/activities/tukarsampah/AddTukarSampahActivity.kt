package com.adityakarang.sampahku.view.role.user.activities.tukarsampah


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.ActivityTukarSampahAddBinding
import com.adityakarang.sampahku.utils.HelpFunction.rpFormat
import com.google.firebase.auth.FirebaseAuth

class AddTukarSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTukarSampahAddBinding
    lateinit var tukarsampahVM: TukarSampahViewModel
    lateinit var Nama: String
    lateinit var JenisSelect: String
    lateinit var HargaSelect: String
    lateinit var Jenis: Array<String>
    lateinit var Harga: Array<String>
    var hitungTotal = 0
    var hitungBerat = 0
    var hitungHarga = 0

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTukarSampahAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnTukar.setOnClickListener {
            addData()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setupInitLayout()
        setupInputData()

    }

    private fun addData() {

    }

    private fun setupInitLayout() {

        Jenis = resources.getStringArray(R.array.kategori_sampah)
        Harga = resources.getStringArray(R.array.harga_sampah)

        tukarsampahVM = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(TukarSampahViewModel::class.java)

        binding.apply {
        val array = ArrayAdapter(this@AddTukarSampahActivity, android.R.layout.simple_list_item_1, Jenis)
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jenisampahET.setAdapter(array)

        jenisampahET.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                JenisSelect = parent.getItemAtPosition(position).toString()
                HargaSelect = Harga[position]
                jenisampahET.setEnabled(true)
                hitungHarga = HargaSelect.toInt()
                if (inputberatET.getText().toString() != "") {
                    hitungBerat = inputberatET.getText().toString().toInt()
                    setupTotalPrice(hitungBerat)
                } else {
                    inputhargaET.setText(rpFormat(hitungHarga))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        inputberatET.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                inputberatET.removeTextChangedListener(this)
                if (editable.length > 0) {
                    hitungBerat = editable.toString().toInt()
                    setupTotalPrice(hitungBerat)
                } else {
                    inputhargaET.setText(rpFormat(hitungHarga))
                }
                inputberatET.addTextChangedListener(this)
            }
        })
    }
    }

    private fun setupTotalPrice(berat: Int) {
        binding.apply {
            hitungTotal = hitungHarga * berat
            inputhargaET.setText(rpFormat(hitungTotal))
        }
    }

    private fun setupInputData() {


        binding.apply {
        btnTukar.setOnClickListener { v: View? ->
            Nama = namaET.text.toString()
            if (Nama.isEmpty() or (Jenis.size == 0) or (hitungBerat == 0) or (hitungHarga == 0)) {
                Toast.makeText(
                    this@AddTukarSampahActivity,
                    "Mohon Lengkapi Data!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                tukarsampahVM.addData(
                    Nama,
                    JenisSelect,
                    hitungBerat,
                    hitungTotal
                )
                Toast.makeText(
                    this@AddTukarSampahActivity,
                    "Tukar Sampah Berhasil",
                    Toast.LENGTH_SHORT).show()
            }
        }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}