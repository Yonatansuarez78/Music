package com.yonatan.music.Home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yonatan.music.Login.Login.Companion.useremail
import com.yonatan.music.Perfil.Perfil
import com.yonatan.music.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
    /*    Toast.makeText(requireContext(), "Hola $useremail", Toast.LENGTH_SHORT).show()*/
        binding.tvNameUser.setOnClickListener { goProfile() }
        binding.ivPerson.setOnClickListener { goProfile() }

        return root


    }

    private fun goProfile() {
        val intent = Intent(requireContext(), Perfil::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}