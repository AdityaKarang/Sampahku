package com.adityakarang.sampahku.view.role.user.activities.informasisampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.databinding.ActivityInformasiBinding

class InformasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }
}