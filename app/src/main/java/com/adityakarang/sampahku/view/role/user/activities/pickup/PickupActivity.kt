package com.adityakarang.sampahku.view.role.user.activities.pickup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adityakarang.sampahku.adapter.KategoriAdapter
import com.adityakarang.sampahku.adapter.PickupAdapter
import com.adityakarang.sampahku.databinding.ActivityPickupBinding
import com.adityakarang.sampahku.model.KategoriModel
import com.adityakarang.sampahku.model.PickupModel
import com.adityakarang.sampahku.view.role.admin.activities.KategoriActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PickupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickupBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var pickupArrayList: ArrayList<PickupModel>
    private lateinit var adapter: PickupAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        loadKategori()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setAction()
    }

    private fun loadKategori() {
            pickupArrayList = java.util.ArrayList()
            val ref = FirebaseDatabase.getInstance().getReference("Pickup")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    pickupArrayList.clear()
                    for (ds in snapshot.children){
                        val model = ds.getValue(PickupModel::class.java)

                        pickupArrayList.add(model!!)
                    }
                    adapter = PickupAdapter(this@PickupActivity, pickupArrayList)
                    binding!!.listPickup.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun setAction(){
        binding.apply {
            pickupAdd.setOnClickListener {
                startActivity(Intent(this@PickupActivity, AddPickupActivity::class.java).apply {
                    finish()
                })
            }
        }
    }
}