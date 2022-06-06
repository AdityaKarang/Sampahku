package com.adityakarang.sampahku.view.role.user.navigation.riwayat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adityakarang.sampahku.adapter.PickupAdapter
import com.adityakarang.sampahku.databinding.FragmentRiwayatBinding
import com.adityakarang.sampahku.model.PickupModel
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class RiwayatFragment : Fragment() {

    private var binding: FragmentRiwayatBinding? = null
    lateinit var auth: FirebaseAuth
    lateinit var adapter: PickupAdapter
    lateinit var pickupArrayList: ArrayList<PickupModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRiwayatBinding.inflate(
            inflater,
            container,
            false
        )

        auth = FirebaseAuth.getInstance()

        val root: View = binding!!.root
        return root
    }


}