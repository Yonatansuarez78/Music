package com.yonatan.music.Home.ui.configurations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yonatan.music.Login.Login
import com.yonatan.music.Home.ui.configurations.Perfil.Perfil
import com.yonatan.music.R
import com.yonatan.music.databinding.FragmentConfiguracionBinding


class ConfiguracionFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    private var _binding: FragmentConfiguracionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfiguracionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.tvSigup.setOnClickListener { tvSigup() }
        binding.goProfile.setOnClickListener { goProfile() }
        binding.goShare.setOnClickListener { goShare() }

        mAuth = FirebaseAuth.getInstance()
        setData()
        return root


    }

    fun setData() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val currentUser = firebaseAuth.currentUser
        val tvNamePrincipal = binding.tvNamePrincipal

        val usuarioActual = mAuth.currentUser
        val correoElectronico = usuarioActual?.email
        val tvCorreoElectronico = binding.tvCorreoElectronico

        if (currentUser != null && correoElectronico != null) {
            //obtenemos y seteamos el correo
            tvCorreoElectronico.text = correoElectronico
            // Obtener el ID del usuario actual
            val userId = currentUser.uid

            // Obtener los datos del usuario desde Firestore
            firebaseFirestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Obtener el dato específico del usuario y mostrarlo en el TextView
                        val userName = documentSnapshot.getString("name")
                        tvNamePrincipal.text = "$userName"
                    } else {
                        tvNamePrincipal.text = "Usuario no encontrado"
                    }
                }
                .addOnFailureListener { exception ->
                    tvNamePrincipal.text = "Error al obtener datos de usuario: ${exception.message}"
                }
        } else {
            tvCorreoElectronico.text = "Correo electrónico no disponible"
            tvNamePrincipal.text = "No hay usuario actual"
        }
    }

    /*---------------------------------------------------------------------------------------------------------*/

    private fun tvSigup() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
    }

    private fun goProfile()  {
        val intent = Intent(requireContext(), Perfil::class.java)
        startActivity(intent)
    }
    private fun goShare() {
        val texto = "¡Hola! Estoy compartiendo este texto desde mi aplicación."

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, texto)

        val chooser = Intent.createChooser(intent, "Compartir")

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(requireContext(), "Error al compartir", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}