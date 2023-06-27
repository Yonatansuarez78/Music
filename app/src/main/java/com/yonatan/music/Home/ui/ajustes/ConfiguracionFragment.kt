package com.yonatan.music.Home.ui.ajustes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.yonatan.music.Login.Login
import com.yonatan.music.Perfil.Perfil
import com.yonatan.music.databinding.FragmentConfiguracionBinding


class ConfiguracionFragment : Fragment() {

    private var _binding: FragmentConfiguracionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfiguracionBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.tvSigup.setOnClickListener { tvSigup() }
        binding.goProfile.setOnClickListener { goProfile() }
        return root


    }

    private fun tvSigup() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
    }

    private fun goProfile()  {
        val intent = Intent(requireContext(), Perfil::class.java)
        startActivity(intent) }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}