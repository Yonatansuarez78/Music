package com.yonatan.music.Login.OlvidatePassword

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.yonatan.music.Login.Login
import com.yonatan.music.R
import com.yonatan.music.Register.RegistroUser

class OlvidatePassword : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvidate_password)

        etEmail = findViewById(R.id.etUpdatePassword)
        mAuth = FirebaseAuth.getInstance()
        val btnGoLogin = findViewById<ConstraintLayout>(R.id.btnGoLogin)
        btnGoLogin.setOnClickListener { home() }
    }

    fun updatePassword(view: View) {
        var e = etEmail.text.toString()
        if (!TextUtils.isEmpty(e)){
            mAuth.sendPasswordResetEmail(e)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Email enviado a $e", Toast.LENGTH_LONG).show()
                        home()
                    } else {
                        Toast.makeText(this, "No hay usuarios registrados con ese correo", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Ingresa un correo", Toast.LENGTH_SHORT).show()
        }
    }

    fun home(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }





}