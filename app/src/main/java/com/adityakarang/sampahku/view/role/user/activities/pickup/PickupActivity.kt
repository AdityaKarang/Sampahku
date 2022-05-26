package com.adityakarang.sampahku.view.role.user.activities.pickup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.databinding.ActivityPickupBinding

class PickupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

    }
}