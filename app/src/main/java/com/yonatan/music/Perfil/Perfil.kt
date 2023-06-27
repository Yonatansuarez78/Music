package com.yonatan.music.Perfil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.yonatan.music.Home.Home
import com.yonatan.music.Home.ui.home.HomeFragment
import com.yonatan.music.R
import com.yonatan.music.Register.RegistroUser
import com.yonatan.music.Register.RegistroUser.userId.idUsuario

class Perfil : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        mAuth = FirebaseAuth.getInstance()
        updatePrfil()

    }

    private fun updatePrfil() {
        val idUsuario = idUsuario //compain object que contiene la id de usuario
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")
        val documentRef = db.collection("users").document("ys246372@gmail.com")

        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.data
                    Log.e("data", data.toString())
                    val userEmail = data?.get("user") as? String
                    if (userEmail != null) {
                        val tvCorreoElectronico = findViewById<TextView>(R.id.tvCorreoElectronico)
                        tvCorreoElectronico.text = userEmail
                    }


                } else {
                    Toast.makeText(this, "El documento no existe", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error de servidor", Toast.LENGTH_SHORT).show()
            }
        }



    fun goHome(view: View) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun goUpdateData(view: View) {
        Toast.makeText(this, "ACTUALIZAR", Toast.LENGTH_SHORT).show()
    }

}