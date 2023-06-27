package com.yonatan.music

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.yonatan.music.Home.Home
import com.yonatan.music.Login.Login

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

    }

    fun go(view: View) {
        val intent = Intent(view.context, Login::class.java)
        view.context.startActivity(intent)
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            goHome(currentUser.email.toString() , currentUser.providerId)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

    private fun goHome(email: String, provider:String) {

        Login.useremail = email
        Login.providerSession = provider

        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

}