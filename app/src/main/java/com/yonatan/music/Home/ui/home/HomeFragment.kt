package com.yonatan.music.Home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yonatan.music.Login.Login.Companion.useremail
import com.yonatan.music.Home.ui.configurations.Perfil.Perfil
import com.yonatan.music.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
    : View { _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mAuth = FirebaseAuth.getInstance()
        Toast.makeText(requireContext(), "Hola $useremail", Toast.LENGTH_SHORT).show()
        binding.tvNameUser.setOnClickListener { goProfile() }
        binding.ivPerson.setOnClickListener { goProfile() }
        getCurrentUser()
        mostrarDatosUsuario("GEVyXEaow3eEUp84QugLjL9kXR82")
        return root

    }

    fun getCurrentUser() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val currentUser = firebaseAuth.currentUser
        val textView = binding.tvName

        if (currentUser != null) {
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
                        textView.text = "$userName"
                    } else {
                        textView.text = "Usuario no encontrado"
                    }
                }
                .addOnFailureListener { exception ->
                    textView.text = "Error al obtener datos de usuario: ${exception.message}"
                }
        } else {
            textView.text = "No hay usuario actual"
        }
    }


    private fun goProfile() {
        val intent = Intent(requireContext(), Perfil::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





/*esto funciona para traer los datos de ese usuario pasandole la id*/
    fun mostrarDatosUsuario(userId: String) {
        val firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userData = documentSnapshot.data
                    println("Datos del usuario:")
                    for ((key, value) in userData!!) {
                        println("$key: $value")
                    }
                } else {
                    println("Usuario no encontrado")
                }
            }
            .addOnFailureListener { exception ->
                println("Error al obtener datos de usuario: ${exception.message}")
            }
    }
}