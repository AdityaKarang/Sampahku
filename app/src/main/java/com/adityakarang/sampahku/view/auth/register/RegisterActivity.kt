package com.adityakarang.sampahku.view.auth.register

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
import com.adityakarang.sampahku.databinding.ActivityRegisterBinding
import com.adityakarang.sampahku.view.auth.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon Tunggu")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegister.setOnClickListener {
            validateData()
        }

        setAction()
        playAnimation()
    }


//

//
//            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                binding.emailEditText.error = "Email tidak valid"
//                binding.emailEditText.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (password.isEmpty() || password.length < 6){
//                binding.passwordEditText.error = "Password harus lebih dari 6 karakter"
//                binding.passwordEditText.requestFocus()
//                return@setOnClickListener
//            }
//
//            userRegister(email, password)
//        }
//
//        binding.login.setOnClickListener {
//            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java).apply {
//                startActivity(this)
//            })
//        }

    private var name = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        name = binding.nameEditText.text.toString().trim()
        email = binding.emailEditText.text.toString().trim()
        password = binding.passwordEditText.text.toString().trim()
        val confirmpass = binding.confirmPassEditText.text.toString().trim()

        if (name.isEmpty()) {
            binding.emailEditText.error = "Masukan nama terlebih dahulu"
            binding.emailEditText.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email...", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Masukan password terlebih dahulu", Toast.LENGTH_SHORT).show()
        } else if (confirmpass.isEmpty()) {
            Toast.makeText(this, "Konfirmasi Password terlebih dahulu", Toast.LENGTH_SHORT).show()
        } else if (password != confirmpass){
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
        }
        else{
            createUserAcc()
        }
    }

    private fun createUserAcc(){

        progressDialog.setMessage("Membuat akun...")
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                updateUser()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Gagal membuat akun ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun updateUser() {
        progressDialog.setMessage("Saving User")

        val timestamp = System.currentTimeMillis()

        val uid = auth.uid

        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["password"] = password
        hashMap["imgProfil"] = ""
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Berhasil Membuat akun", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this, "Gagal menyimpan user ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }

    private fun setAction() {
        binding.apply {
            login.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java).apply {
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
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditText =
            ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(500)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(500)
        val confirmpassTextView =
            ObjectAnimator.ofFloat(binding.confirmPassTextView, View.ALPHA, 1f).setDuration(500)
        val confirmpassEditText =
            ObjectAnimator.ofFloat(binding.confirmPassEditText, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val textviewAkun =
            ObjectAnimator.ofFloat(binding.akun, View.ALPHA, 1f).setDuration(500)
        val textviewLogin =
            ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                imgBackground,
                messageTextView,
                nameTextView,
                nameEditText,
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                confirmpassTextView,
                confirmpassEditText,
                login,
                textviewAkun,
                textviewLogin
            )
            startDelay = 500
        }.start()
    }
}