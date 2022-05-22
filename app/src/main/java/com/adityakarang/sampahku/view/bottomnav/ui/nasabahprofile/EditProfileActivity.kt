package com.adityakarang.sampahku.view.bottomnav.ui.nasabahprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adityakarang.sampahku.view.bottomnav.ui.home.EditProfileViewModel
import com.adityakarang.sampahku.databinding.ActivityProfileNasabahBinding

class EditProfileActivity : Fragment() {

    private var _binding: ActivityProfileNasabahBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(EditProfileViewModel::class.java)

        _binding = ActivityProfileNasabahBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView3
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}