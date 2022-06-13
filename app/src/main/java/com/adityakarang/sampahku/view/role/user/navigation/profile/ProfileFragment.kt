package com.adityakarang.sampahku.view.role.user.navigation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakarang.sampahku.R
import com.adityakarang.sampahku.databinding.FragmentProfileBinding
import com.adityakarang.sampahku.view.main.MainActivity
import com.adityakarang.sampahku.view.role.user.activities.editprofile.EditProfileActivity
import com.adityakarang.sampahku.view.role.user.activities.lokasi.MapsActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding!!.btnLogout.setOnClickListener {
            auth.signOut()
            Intent(requireActivity(), MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.btnUpdate.setOnClickListener {
            Intent(requireActivity(), EditProfileActivity::class.java).apply {
                startActivity(this)
            }
        }

        loadUser()

    }

    private fun loadUser() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(auth.uid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = "${snapshot.child("email").value}"
                    val name = "${snapshot.child("name").value}"
                    val imgProfile = "${snapshot.child("imgProfil").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val userType = "${snapshot.child("userType").value}"

                    binding!!.tvName.text = name
                    binding!!.tvEmail.text = email

                    try {
                        Glide.with(this@ProfileFragment).load(imgProfile)
                            .placeholder(R.drawable.ic_person).into(binding!!.tvProfile)
                    } catch (e: Exception) {

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}