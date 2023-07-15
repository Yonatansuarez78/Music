    package com.yonatan.music.Home.ui.configurations.Gallery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yonatan.music.Home.Home
import com.yonatan.music.Home.ui.configurations.ConfiguracionFragment
import com.yonatan.music.R

class GalleyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galley)
    }

    fun goHome(view: View) {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }
}