package com.yonatan.music.Register

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yonatan.music.DataClass.User
import com.yonatan.music.Home.Home
import com.yonatan.music.Login.Login
import com.yonatan.music.Login.Login.Companion.providerSession
import com.yonatan.music.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.properties.Delegates

class RegistroUser : AppCompatActivity() {


    private var name by Delegates.notNull<String>()
    private var telefono by Delegates.notNull<String>()
    private var fecha by Delegates.notNull<String>()
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()

    private lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etFecha: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_user)

        etName = findViewById(R.id.etName)
        etNumber = findViewById(R.id.etNumber)
        etFecha = findViewById(R.id.etFechaNacimiento)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()

        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        etFechaNacimiento.setOnClickListener { goCalendar() }
    }

    fun registro(view: View){
        goRegister()
    }


    private fun goRegister() {
        name = etName.text.toString()
        telefono = etNumber.text.toString()
        fecha = etFecha.text.toString()
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        if (name.isNotEmpty() && telefono.isNotEmpty() && fecha.isNotEmpty() && email.isNotEmpty() &&  password.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = task.result?.user?.uid
                        val dataRegister = SimpleDateFormat("dd/MM/yyyy").format(Date())
                        val db = FirebaseFirestore.getInstance()
                        val usuariosRef = db.collection("users")

                        val userData = User(
                            name = name,
                            phoneNumber = telefono,
                            usuario = email,
                            birthdate = fecha
                        )

                        if (userId != null) {
                            usuariosRef.document(userId).set(userData)
                                .addOnSuccessListener {
                                    goHome(email, providerSession)
                                }
                                .addOnFailureListener { exception ->
                                    showTopSnackbar("No pudimos crear tu usuario")
                                }
                        }
                    } else {
                        showTopSnackbar("Error de servidor")
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

    fun goCalendar() {
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { selection ->
            val selectedDateInMillis = selection as Long
            val selectedDate = Calendar.getInstance().apply {
                timeInMillis = selectedDateInMillis
                timeZone = TimeZone.getDefault()
            }
            val selectedDateString = SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time)
            etFechaNacimiento.setText(selectedDateString)
        }
        picker.show(supportFragmentManager, "DATE_PICKER_TAG")
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