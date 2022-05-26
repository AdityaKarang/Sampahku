package com.adityakarang.sampahku.view.role.user.activities.tukarsampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.databinding.ActivityTukarSampahBinding

class TukarSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTukarSampahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTukarSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }
}