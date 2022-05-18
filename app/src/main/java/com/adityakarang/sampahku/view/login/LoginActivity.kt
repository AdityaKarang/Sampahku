package com.adityakarang.sampahku.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.adityakarang.sampahku.databinding.ActivityLoginBinding
import com.adityakarang.sampahku.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
        setAction()
        playAnimation()
    }

    private fun setView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setAction() {
        binding.apply {
            Signup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java).apply {
                    startActivity(this)
                })
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val imgBackground =
            ObjectAnimator.ofFloat(binding.imgBackground, View.ALPHA, 1f).setDuration(500)
        val messageTextView =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val copyrightTextView =
            ObjectAnimator.ofFloat(binding.copyrightTextView2, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.Signup, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                imgBackground,
                messageTextView,
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                copyrightTextView,
                register,
                login
            )
            startDelay = 500
        }.start()
    }
}