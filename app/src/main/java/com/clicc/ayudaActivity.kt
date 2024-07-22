package com.clicc

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class ayudaActivity : AppCompatActivity() {
    private val url = "https://www.google.com/"
    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ayuda)

        val view = findViewById<WebView>(R.id.view)
        val retroceso = findViewById<ShapeableImageView>(R.id.retroceso)
        view.webChromeClient = object : WebChromeClient(){}
        view.webViewClient = object : WebViewClient(){}
        view.settings.javaScriptEnabled = true
        view.loadUrl(url)

        retroceso.setOnClickListener {
            startActivities(arrayOf(Intent(this, pagina_inicioActivity::class.java)))
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}