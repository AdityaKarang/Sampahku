package com.adityakarang.sampahku.view.role.admin.dashboard

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.ActivityDashboardAdminBinding
import com.adityakarang.sampahku.view.main.MainActivity
import com.adityakarang.sampahku.view.role.admin.navigation.home.AdminHomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardAdminBinding
    private lateinit var auth: FirebaseAuth


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{item ->
        when (item.itemId){
            R.id.navigation_home_admin ->{
                moveToFragment(AdminHomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera_admin ->{
                moveToFragment(AdminHomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications_admin ->{
                moveToFragment(AdminHomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile_admin ->{
                moveToFragment(AdminHomeFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        userCheck()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            userCheck()
        }

        val navView : BottomNavigationView = findViewById(R.id.navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(AdminHomeFragment())

    }
    private fun userCheck() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            val email = firebaseUser.email

            binding.tvEmail.text = email
        }
    }


}