package com.adityakarang.sampahku.view.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.adityakarang.sampahku.databinding.ActivityLoginBinding
import com.adityakarang.sampahku.view.role.user.activities.dashboard.UserDashboardActivity
import com.adityakarang.sampahku.view.auth.register.RegisterActivity
import com.adityakarang.sampahku.view.role.admin.dashboard.AdminDashboardActivity
import com.adityakarang.sampahku.view.role.user.navigation.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.btnLogin.setOnClickListener {
            validateData()
        }


        setAction()
//        setView()
        playAnimation()
    }



//    private fun setView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }

    private var email = ""
    private var password = ""

    private fun validateData() {
        email = binding.emailEditText.text.toString().trim()
        password = binding.passwordEditText.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Masukan password terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
        else{
            userLogin()
        }
    }

    private fun userLogin(){

        progressDialog.setMessage("Login..")
        progressDialog.show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                userCheck()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "login gagal ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun userCheck() {

        progressDialog.setMessage("checking user...")

        val firebaseUser = auth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()

                    val userType = snapshot.child("userType").value
                    if (userType == "user"){
                        startActivity(Intent(this@LoginActivity, UserDashboardActivity::class.java))
                        finish()
                    }
                    else if(userType == "admin"){
                        startActivity(Intent(this@LoginActivity, AdminDashboardActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
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


//    private fun setAction() {

//            }
//
//            btnLogin.setOnClickListener {
//                val email = binding.emailEditText.text.toString()
//                val password = binding.passwordEditText.text.toString()
//
//                if (email.isEmpty()){
//                    binding.emailEditText.error = "Masukan email terlebih dahulu"
//                    binding.emailEditText.requestFocus()
//                    return@setOnClickListener
//                }
//
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    binding.emailEditText.error = "Email tidak valid"
//                    binding.emailEditText.requestFocus()
//                    return@setOnClickListener
//                }
//
//                if (password.isEmpty() || password.length < 6){
//                    binding.passwordEditText.error = "Password harus lebih dari 6 karakter"
//                    binding.passwordEditText.requestFocus()
//                    return@setOnClickListener
//                }
//
//                userLogin(email, password)
//            }
//        }
//    }
//
//    private fun userLogin(email: String, password: String) {
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this){
//                if (it.isSuccessful){
//                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
//                    Intent(this@LoginActivity, MainActivity::class.java).also {
//                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(it)
//                    }
//                }else{
//                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

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