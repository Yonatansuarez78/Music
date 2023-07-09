package com.yonatan.music.Home.ui.configurations.Perfil

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yonatan.music.DataClass.User
import com.yonatan.music.Home.Home
import com.yonatan.music.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone


class Perfil : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        mAuth = FirebaseAuth.getInstance()
        setDataUser()
        setDataUsers()

        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        etFechaNacimiento.setOnClickListener { goCalendar() }
    }


    fun goUpdateData(view: View) {
        val etName = findViewById<EditText>(R.id.etName)
        val etNumberTelefono = findViewById<EditText>(R.id.etNumberTelefono)
        val textEmailAddress = findViewById<EditText>(R.id.textEmailAddress)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)

        val firestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = firestore.collection("users").document(userId)

            val userData = User(
                name = etName.text.toString(),
                phoneNumber = etNumberTelefono.text.toString(),
                usuario = textEmailAddress.text.toString(),
                birthdate = etFechaNacimiento.text.toString()
            )

            userRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    goHome(view)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Error al actualizar. Intente nuevamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    fun setDataUser() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val currentUser = firebaseAuth.currentUser

        val usuarioActual = mAuth.currentUser
        val correoElectronico = usuarioActual?.email
        val tvCorreoElectronico = findViewById<TextView>(R.id.tvCorreoElectronico)
        val textEmailAddress = findViewById<TextView>(R.id.textEmailAddress)

        val tvNamePrincipal = findViewById<TextView>(R.id.tvNamePrincipal)

        if (currentUser != null && correoElectronico != null) {
            //obtenemos y seteamos el correo
            tvCorreoElectronico.text = correoElectronico
            textEmailAddress.text = correoElectronico

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


    private fun setDataUsers() {
        val etName = findViewById<EditText>(R.id.etName)
        val etNumberTelefono = findViewById<EditText>(R.id.etNumberTelefono)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)

        val firestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Obtener el dato específico del usuario y mostrarlo en el TextView
                        val userName = documentSnapshot.getString("name")
                        val userNumber = documentSnapshot.getString("phoneNumber")
                        val userFecha = documentSnapshot.getString("birthdate")

                        etName.setText(userName)
                        etNumberTelefono.setText(userNumber)
                        etFechaNacimiento.setText(userFecha)
                    }
            }
        }
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



        fun goHome(view: View) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }



