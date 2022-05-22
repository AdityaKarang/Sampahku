package com.adityakarang.sampahku.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.adityakarang.sampahku.MainActivity
import com.adityakarang.sampahku.databinding.ActivityLoginBinding
import com.adityakarang.sampahku.view.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

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

            btnLogin.setOnClickListener {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                if (email.isEmpty()){
                    binding.emailEditText.error = "Masukan email terlebih dahulu"
                    binding.emailEditText.requestFocus()
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.emailEditText.error = "Email tidak valid"
                    binding.emailEditText.requestFocus()
                    return@setOnClickListener
                }

                if (password.isEmpty() || password.length < 6){
                    binding.passwordEditText.error = "Password harus lebih dari 6 karakter"
                    binding.passwordEditText.requestFocus()
                    return@setOnClickListener
                }

                userLogin(email, password)
            }
        }
    }

    private fun userLogin(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginActivity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

//    override fun onStart() {
//        super.onStart()
//        if (auth.currentUser != null) {
//            Intent(this@LoginActivity, MainActivity::class.java).also {
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
//            }
//        }
//    }

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
        val messageTextView1 = ObjectAnimator.ofFloat(binding.messageTextView1, View.ALPHA, 1f).setDuration(500)
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
                messageTextView1,
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