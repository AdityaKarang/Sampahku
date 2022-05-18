package com.adityakarang.sampahku.view.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}