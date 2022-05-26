package com.adityakarang.sampahku.view.role.admin.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adityakarang.sampahku.databinding.FragmentAdminHomeBinding


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
}