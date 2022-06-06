package com.adityakarang.sampahku.view.role.user.activities.tukarsampah


import android.content.Intent
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
import com.adityakarang.sampahku.utils.FunctionHelper.rupiahFormat
import com.adityakarang.sampahku.view.role.user.activities.pickup.PickupActivity
import com.adityakarang.sampahku.view.role.user.navigation.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth

class AddTukarSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTukarSampahAddBinding
    lateinit var tukarsampahVM: TukarSampahViewModel
    lateinit var Nama: String
    lateinit var KategoriSelect: String
    lateinit var HargaSelect: String
    lateinit var Kategori: Array<String>
    lateinit var Harga: Array<String>
    var countTotal = 0
    var countBerat = 0
    var countHarga = 0

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

        setupInitLayout()
        setupInputData()

    }

    private fun addData() {

    }

    private fun setupInitLayout() {

        Kategori = resources.getStringArray(R.array.kategori_sampah)
        Harga = resources.getStringArray(R.array.harga_perkilo)

        tukarsampahVM = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(TukarSampahViewModel::class.java)

        binding.apply {
        val arrayBahasa = ArrayAdapter(this@AddTukarSampahActivity, android.R.layout.simple_list_item_1, Kategori)
        arrayBahasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jenisampahET.setAdapter(arrayBahasa)

        jenisampahET.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                KategoriSelect = parent.getItemAtPosition(position).toString()
                HargaSelect = Harga[position]
                jenisampahET.setEnabled(true)
                countHarga = HargaSelect.toInt()
                if (inputberatET.getText().toString() != "") {
                    countBerat = inputberatET.getText().toString().toInt()
                    setupTotalPrice(countBerat)
                } else {
                    inputhargaET.setText(rupiahFormat(countHarga))
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
                    countBerat = editable.toString().toInt()
                    setupTotalPrice(countBerat)
                } else {
                    inputhargaET.setText(rupiahFormat(countHarga))
                }
                inputberatET.addTextChangedListener(this)
            }
        })
    }
    }

    private fun setupTotalPrice(berat: Int) {
        binding.apply {
            countTotal = countHarga * berat
            inputhargaET.setText(rupiahFormat(countTotal))
        }
    }

    private fun setupInputData() {
        binding.apply {
        btnTukar.setOnClickListener { v: View? ->
            Nama = namaET.text.toString()
            if (Nama.isEmpty() or (Kategori.size == 0) or (countBerat == 0) or (countHarga == 0)) {
                Toast.makeText(
                    this@AddTukarSampahActivity,
                    "Mohon Lengkapi Data!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                tukarsampahVM.addData(
                    Nama,
                    KategoriSelect,
                    countBerat,
                    countTotal
                )
                Toast.makeText(
                    this@AddTukarSampahActivity,
                    "Tukar Sampah Berhasil",
                    Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AddTukarSampahActivity, TukarSampahActivity::class.java))
                finish()
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