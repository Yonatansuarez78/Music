package com.yonatan.music.Login.OlvidatePassword

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
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
                        Toast.makeText(this, "Email enviado a: $e", Toast.LENGTH_LONG).show()
                        home()
                    } else {
                        showTopSnackbar("No hay usuarios registrados con ese correo")
                    }
                }
        } else {
            showTopSnackbar("Ingresa un correo electronico")
        }
    }

    private fun showTopSnackbar(message: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "",
            Snackbar.LENGTH_LONG
        )

        snackbar.view.setBackgroundColor(Color.DKGRAY)

        val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        snackbar.view.layoutParams = params

        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val textView = TextView(this)
        textView.text = message
        textView.setTextColor(Color.WHITE)
        textView.gravity = Gravity.CENTER
        snackbarLayout.addView(textView, 0)

        snackbar.show()
    }

    fun home(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }





}