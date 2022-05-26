package com.adityakarang.sampahku.view.role.user.activities.lokasi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.databinding.ActivityLokasiBinding

class LokasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLokasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }
}