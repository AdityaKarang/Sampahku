package com.adityakarang.sampahku.view.role.user.navigation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adityakarang.sampahku.databinding.FragmentHomeBinding
import com.adityakarang.sampahku.view.role.user.activities.informasisampah.InformasiActivity
import com.adityakarang.sampahku.view.role.user.activities.lokasi.LokasiActivity
import com.adityakarang.sampahku.view.role.user.activities.pickup.PickupActivity
import com.adityakarang.sampahku.view.role.user.activities.tukarsampah.TukarSampahActivity


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        homeViewModel.text.observe(viewLifecycleOwner) {
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.tvPickup.setOnClickListener {
            Intent(requireActivity(), PickupActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.tvLokasi.setOnClickListener {
            Intent(requireActivity(), LokasiActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.tvTukar.setOnClickListener {
            Intent(requireActivity(), TukarSampahActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.tvInformasi.setOnClickListener {
            Intent(requireActivity(), InformasiActivity::class.java).apply {
                startActivity(this)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}