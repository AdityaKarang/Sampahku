package com.adityakarang.sampahku.view.role.admin.navigation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adityakarang.sampahku.databinding.FragmentAdminHomeBinding
import com.adityakarang.sampahku.view.role.admin.activities.InfomasiSampahActivity


class AdminHomeFragment : Fragment() {

    private var binding: FragmentAdminHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val AdminHomeViewModel =
            ViewModelProvider(this).get(AdminHomeViewModel::class.java)

        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        AdminHomeViewModel.text.observe(viewLifecycleOwner) {
        }
        return root


    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.tvInformasi.setOnClickListener {
            Intent(requireActivity(), InfomasiSampahActivity::class.java).apply {
                startActivity(this)
            }
        }

    }
}