package com.yonatan.music.Login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.yonatan.music.Home.Home
import com.yonatan.music.Login.OlvidatePassword.OlvidatePassword
import com.yonatan.music.MainActivity
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
                        showTopSnackbar("Error al iniciar sesion")
                    }
                }
        } else {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()
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

    fun goMain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}