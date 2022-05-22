package com.adityakarang.sampahku.view.spalshscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.adityakarang.sampahku.MainActivity
import com.adityakarang.sampahku.R

class SplashScreenActivity : AppCompatActivity()
{ override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)

    var handle = Handler()
    handle.postDelayed({
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }, 3000)
}
}