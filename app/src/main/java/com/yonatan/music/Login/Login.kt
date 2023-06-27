package com.yonatan.music.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.yonatan.music.Home.Home
import com.yonatan.music.Login.OlvidatePassword.OlvidatePassword
import com.yonatan.music.R
import com.yonatan.music.Register.RegistroUser
import kotlin.properties.Delegates


class Login : AppCompatActivity() {

    companion object{
        lateinit var useremail: String
        lateinit var providerSession: String
    }

    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()

    }

    fun login(view: View){
        loginUser()
    }

     fun registro(view: View){
        val intent = Intent(this, RegistroUser::class.java)
        startActivity(intent)
    }


    private fun loginUser() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        gohome(email, "email")
                    } else {
                        Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()
        }
    }


    private fun gohome(email: String, provider: String) {
        useremail = email
        providerSession = provider

        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    fun goOlvidatePassword(view: View) {
        val intent = Intent(this, OlvidatePassword::class.java)
        startActivity(intent)
    }
}