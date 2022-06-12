package com.adityakarang.sampahku.view.role.user.activities.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.ActivityDashboardUserBinding
import com.adityakarang.sampahku.view.role.user.activities.scan.ScanSampahActivity
import com.adityakarang.sampahku.view.role.user.navigation.home.HomeFragment
import com.adityakarang.sampahku.view.role.user.navigation.profile.ProfileFragment
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
        binding.bottomNavigation.menu.getItem(1).isEnabled = false

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

}