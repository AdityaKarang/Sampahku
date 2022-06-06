package com.adityakarang.sampahku.view.role.user.activities.tukarsampah

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityakarang.sampahku.databinding.ActivityTukarSampahBinding
import com.adityakarang.sampahku.model.TukarSampahModel
import java.util.ArrayList

class TukarSampahActivity : AppCompatActivity(), TukarSampahAdapter.TukarSampahAdapterCallback {

    var tukarSampahModelList: MutableList<TukarSampahModel> = ArrayList()
    lateinit var riwayatAdapter: TukarSampahAdapter
    lateinit var tukarsampahVM: TukarSampahViewModel

    private lateinit var binding: ActivityTukarSampahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTukarSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setAction()
        setInitLayout()
        setViewModel()
    }

    private fun setInitLayout() {
        riwayatAdapter = TukarSampahAdapter(this, tukarSampahModelList, this)
        binding.listTukar.setHasFixedSize(true)
        binding.listTukar.setLayoutManager(LinearLayoutManager(this))
        binding.listTukar.setAdapter(riwayatAdapter)

    }

    private fun setViewModel() {
        tukarsampahVM = ViewModelProviders.of(this).get(TukarSampahViewModel::class.java)

        tukarsampahVM.dataBank.observe(this) { tukarSampahModels: List<TukarSampahModel> ->
            if (tukarSampahModels.isEmpty()) {
                binding.listTukar.visibility = View.GONE
            } else {
                binding.listTukar.visibility = View.VISIBLE
            }
            riwayatAdapter.setDataAdapter(tukarSampahModels)
        }
    }

    override fun onDelete(tukarSampahModel: TukarSampahModel?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Hapus riwayat ini?")
        alertDialogBuilder.setPositiveButton("Ya, Hapus") { dialogInterface: DialogInterface?, i: Int ->
            val uid = tukarSampahModel!!.uid
            tukarsampahVM.deleteDataById(uid)
            Toast.makeText(this@TukarSampahActivity, "Data yang dipilih sudah dihapus", Toast.LENGTH_SHORT).show()
        }

        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

    private fun setAction(){
        binding.apply {
            pickupAdd.setOnClickListener {
                startActivity(Intent(this@TukarSampahActivity, AddTukarSampahActivity::class.java).apply {
                    startActivity(this)
                })
            }
        }
    }
}