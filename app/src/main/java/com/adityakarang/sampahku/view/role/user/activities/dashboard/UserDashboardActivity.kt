package com.adityakarang.sampahku.view.role.user.activities.dashboard

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.ActivityDashboardUserBinding
import com.adityakarang.sampahku.view.auth.login.LoginActivity
import com.adityakarang.sampahku.view.main.MainActivity
import com.adityakarang.sampahku.view.role.user.activities.scan.ScanSampahActivity
import com.adityakarang.sampahku.view.role.user.navigation.home.HomeFragment
import com.adityakarang.sampahku.view.role.user.navigation.notifications.NotificationsFragment
import com.adityakarang.sampahku.view.role.user.navigation.profile.ProfileFragment
import com.adityakarang.sampahku.view.role.user.navigation.riwayat.RiwayatFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class UserDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding
    private lateinit var auth: FirebaseAuth

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{ item ->
        when (item.itemId){
            R.id.navigation_home ->{
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications ->{
                moveToFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_riwayat ->{
                moveToFragment(RiwayatFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile ->{
                moveToFragment(ProfileFragment())
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
        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(2).isEnabled = false
//        userCheck()

//        binding.btnLogout.setOnClickListener {
//            auth.signOut()
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }

        val navView : BottomNavigationView = findViewById(R.id.bottomNavigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(HomeFragment())

        setAction()


    }

    private fun setAction(){
        binding.apply {
            floating.setOnClickListener {
                startActivity(Intent(this@UserDashboardActivity, ScanSampahActivity::class.java).apply {
                    startActivity(this)
                })
            }
        }
    }
//    private fun userCheck() {
//        val firebaseUser = auth.currentUser
//        if (firebaseUser == null){
//          binding.tvEmail.text = "Not Logged in"
//        }
//        else{
//            val email = firebaseUser.email
//
//            binding.tvEmail.text = email
//        }
//    }
}