package com.yonatan.music.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yonatan.music.Home.Home
import com.yonatan.music.Login.Login
import com.yonatan.music.Login.Login.Companion.providerSession
import com.yonatan.music.R
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.properties.Delegates

class RegistroUser : AppCompatActivity() {


    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_user)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()
    }

    fun registro(view: View){
        goRegister()
    }


 /*   private fun goRegister() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        val dataRegister = SimpleDateFormat("dd/MM/yyyy").format(Date())
                        val db = FirebaseFirestore.getInstance()
                        val usuariosRef = db.collection("users")

                        val usuario = hashMapOf(
                            "user" to email,
                            "id" to userId,
                            "dataRegister" to dataRegister
                        )

                        usuariosRef.document(email).set(usuario)
                            .addOnSuccessListener {
                                goHome(email, providerSession)
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(this, "No se pudo crear tu usuario", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Error de servidor", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()
        }
    }*/
 private fun goRegister() {
     email = etEmail.text.toString()
     password = etPassword.text.toString()

     if (email.isNotEmpty() && password.isNotEmpty()) {
         FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener { task ->
                 if (task.isSuccessful) {
                     val userId = task.result?.user?.uid
                     val dataRegister = SimpleDateFormat("dd/MM/yyyy").format(Date())
                     val db = FirebaseFirestore.getInstance()
                     val usuariosRef = db.collection("users")

                     val usuario = hashMapOf(
                         "user" to email,
                         "dataRegister" to dataRegister
                     )

                     if (userId != null) {
                         usuariosRef.document(userId).set(usuario)
                             .addOnSuccessListener {
                                 goHome(email, providerSession)
                             }
                             .addOnFailureListener { exception ->
                                 Toast.makeText(this, "No se pudo crear tu usuario", Toast.LENGTH_SHORT).show()
                             }
                     }
                 } else {
                     Toast.makeText(this, "Error de servidor", Toast.LENGTH_SHORT).show()
                 }
             }
     } else {
         Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()
     }
 }





    private fun goHome(email: String, providerSession: String){
        Login.useremail = email
        Login.providerSession = providerSession

        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun goHome(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}