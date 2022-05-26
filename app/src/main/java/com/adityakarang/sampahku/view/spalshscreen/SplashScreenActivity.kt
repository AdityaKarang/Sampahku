package com.adityakarang.sampahku.view.spalshscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.view.main.MainActivity
import com.adityakarang.sampahku.view.role.admin.dashboard.AdminDashboardActivity
import com.adityakarang.sampahku.view.role.user.activities.dashboard.UserDashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            userCheck()
        }, 1000)
    }

    private fun userCheck() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            val ref = FirebaseDatabase.getInstance().getReference("Users")
            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val userType = snapshot.child("userType").value
                        if (userType == "user") {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    UserDashboardActivity::class.java
                                )
                            )
                            finish()
                        } else if (userType == "admin") {
                            startActivity(
                                Intent(
                                    this@SplashScreenActivity,
                                    AdminDashboardActivity::class.java
                                )
                            )
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }
    }
}
