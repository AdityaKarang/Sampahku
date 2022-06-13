package com.adityakarang.sampahku.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adityakarang.sampahku.databinding.ActivityMainBinding
import com.adityakarang.sampahku.view.auth.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()

        binding.btnMasuk.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgWelcome, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title1 =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val title2 =
            ObjectAnimator.ofFloat(binding.titleTextView2, View.ALPHA, 1f).setDuration(500)
        val masuk = ObjectAnimator.ofFloat(binding.btnMasuk, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title1,
                title2,
                masuk
            )
            startDelay = 500
        }.start()
    }


}