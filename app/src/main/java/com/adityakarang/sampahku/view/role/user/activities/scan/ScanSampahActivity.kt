package com.adityakarang.sampahku.view.role.user.activities.scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.databinding.ActivityScanSampahBinding

class ScanSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanSampahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }
}