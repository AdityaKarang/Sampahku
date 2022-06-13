package com.adityakarang.sampahku.view.role.user.navigation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adityakarang.sampahku.databinding.FragmentHomeBinding
import com.adityakarang.sampahku.utils.HelpFunction
import com.adityakarang.sampahku.view.role.user.activities.lokasi.MapsActivity
import com.adityakarang.sampahku.view.role.user.activities.pickup.PickupActivity
import com.adityakarang.sampahku.view.role.user.activities.tukarsampah.TukarSampahActivity


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val homeVM by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeVM.totalSaldo.observe(viewLifecycleOwner) { integer ->
            if (integer == null) {
                val jumlahSaldo = 0
                val initSaldo = HelpFunction.rpFormat(jumlahSaldo)
                binding!!.tvPoint.text = initSaldo
            } else {
                val initSaldo = HelpFunction.rpFormat(integer)
                binding!!.tvPoint.text = initSaldo
            }
        }

        binding!!.tvPickup.setOnClickListener {
            Intent(requireActivity(), PickupActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.tvLokasi.setOnClickListener {
            Intent(requireActivity(), MapsActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.tvTukar.setOnClickListener {
            Intent(requireActivity(), TukarSampahActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding!!.tukar.setOnClickListener {
            Intent(requireActivity(), TukarSampahActivity::class.java).apply {
                startActivity(this)
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}