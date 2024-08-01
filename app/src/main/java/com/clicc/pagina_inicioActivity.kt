package com.clicc

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.clicc.R.drawable
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class pagina_inicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_inicio)
        // terminar los colores

        @SuppressLint("ResourceType", "ResourceAsColor", "MissingPermission")
        fun juego() {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            val mensaje = "VRI[ayudaActivity]      com.clicc"


            // botones
            val boton_1 = ContextCompat.getDrawable(this, R.drawable.bordes_r)
            val boton_2 = ContextCompat.getDrawable(this, R.drawable.bordes_az)
            val boton_3 = ContextCompat.getDrawable(this, R.drawable.bordes_v)
            val boton_4 = ContextCompat.getDrawable(this, R.drawable.bordes_am)
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val col = pref.getString("colores", "")
            val sound = findViewById<ShapeableImageView>(R.id.sound)
            val record_h = findViewById<TextView>(R.id.record_h)
            // colores
            fun cambio(col: String) {
                Log.d("fallo", "funcion ejecutada")
                if (col == "Color: 1") {
                    val rojo = Color.parseColor("#d30000")
                    boton_1!!.setTint(rojo)
                    val verde = Color.parseColor("#03d300")
                    boton_3!!.setTint(verde)
                    val azul = Color.parseColor("#0015d3")
                    boton_2!!.setTint(azul)
                    val amarillo = Color.parseColor("#d1d100")
                    boton_4!!.setTint(amarillo)
                } else if (col == "Color: 2") {
                    val azul_1 = Color.parseColor("#5e9fa3")
                    boton_1!!.setTint(azul_1)
                    val blanco_1 = Color.parseColor("#dcd1b4")
                    boton_2!!.setTint(blanco_1)
                    val salmon_1 = Color.parseColor("#fab87f")
                    boton_3!!.setTint(salmon_1)
                    val rosa_1 = Color.parseColor("#b05574")
                    boton_4!!.setTint(rosa_1)
                } else {
                    val gris_2 = Color.parseColor("#8b8b70")
                    boton_1!!.setTint(gris_2)
                    val rojo_2 = Color.parseColor("#e84a5f")
                    boton_2!!.setTint(rojo_2)
                    val amarillo_2 = Color.parseColor("#ecb163")
                    boton_3!!.setTint(amarillo_2)
                    val morado_2 = Color.parseColor("#473469")
                    boton_4!!.setTint(morado_2)
                }
            }
            val colores = findViewById<Spinner>(R.id.colores)
            colores.visibility = View.INVISIBLE
            colores.isEnabled = false
            val lista = mutableListOf("Color: 1", "Color: 2", "Color: 3")
            val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
            colores.adapter = adaptador
            val lisener = object : OnItemSelectedListener, AdapterView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    TODO("Not yet implemented")
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val e = colores.getItemAtPosition(position).toString()
                    pref.edit().putString("colores", e).apply()
                    cambio(e)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
            colores.onItemSelectedListener = lisener
            if (col!!.isEmpty()){
                colores.setSelection(0)
                val c = colores.getItemAtPosition(0).toString()
                cambio(c)
            }else{
                val posicion = lista.indexOf(col)
                colores.setSelection(posicion)

                cambio(col)
            }

            val view = findViewById<View>(R.id.view)
            val facil = findViewById<AppCompatButton>(R.id.facil)
            val dificil = findViewById<AppCompatButton>(R.id.dificil)
            val crazy = findViewById<AppCompatButton>(R.id.crazy)
            val texto = findViewById<TextView>(R.id.texto)
            val restart = findViewById<ShapeableImageView>(R.id.restart)

            val s_2 = findViewById<Switch>(R.id.s_2)
            val menu = findViewById<ShapeableImageView>(R.id.menu)
            var son = "activo"
            var v_menu = "tapado"
            val tiempo = findViewById<TextView>(R.id.tiempo)
            val time = intent.extras?.getString("time").orEmpty()
            if (time.isEmpty()) {
                val t = pref.getString("time", "0")
                if (t == "0") {
                    tiempo.visibility = View.INVISIBLE
                } else {
                    tiempo.visibility = View.VISIBLE
                    tiempo.text = "/$t" + "s"
                }
            }else {
                tiempo.visibility = View.VISIBLE
                tiempo.text = "/$time" + "s"
                pref.edit().putString("time", time).apply()
            }
            val idioma = pref.getString("idioma", "en")



            // sonido preference
            val s = pref.getString("sonido", "activado")

            if (s == "activo"){
                son = "activo"
                sound.setImageResource(drawable.sonido_activado)
            }else {
                son = "desactivado"
                sound.setImageResource(drawable.sonido_inactivo)
            }
            menu.setOnClickListener {
                if (v_menu == "tapado"){
                    val animacion = AnimationUtils.loadAnimation(this, R.anim.translacion)
                    val animacion_sonido = AnimationUtils.loadAnimation(this, R.anim.transicion_sonido)
                    val animacion_colores = AnimationUtils.loadAnimation(this, R.anim.transicion_colores)
                    animacion.setAnimationListener(object:Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                            s_2.isEnabled = false
                            menu.isEnabled = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            s_2.isEnabled = true
                            menu.isEnabled = true
                            s_2.clearAnimation()
                            s_2.y = 295f
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                    s_2.startAnimation(animacion)
                    animacion_sonido.setAnimationListener(object:Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                            sound.isEnabled = false
                            menu.isEnabled = false
                            view.visibility = View.INVISIBLE
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            sound.clearAnimation()
                            menu.isEnabled = true
                            sound.isEnabled = true
                            sound.x = 700f
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                            TODO("Not yet implemented")
                        }
                    })
                    sound.startAnimation(animacion_sonido)
                    // animacion de colores
                    animacion_colores.setAnimationListener(object : Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                            colores.isEnabled = false
                            colores.visibility = View.VISIBLE

                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            colores.clearAnimation()
                            colores.isEnabled = true
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                            TODO("Not yet implemented")
                        }
                    })
                    colores.startAnimation(animacion_colores)
                    v_menu = "sacado"
                }else{
                    val animacion_2 = AnimationUtils.loadAnimation(this, R.anim.transicion_2)
                    val animacion_sonido_2 = AnimationUtils.loadAnimation(this, R.anim.transicion_sonido_2)
                    val animacion_colroes_2 = AnimationUtils.loadAnimation(this, R.anim.transicion_colores_2)
                    animacion_2.setAnimationListener(object:Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                            s_2.isEnabled = false
                            menu.isEnabled = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            s_2.isEnabled = true
                            menu.isEnabled = true
                            s_2.clearAnimation()
                            s_2.y = 171f
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                        }
                    })
                    s_2.startAnimation(animacion_2)
                    animacion_sonido_2.setAnimationListener(object:Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                            sound.isEnabled = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            sound.clearAnimation()
                            sound.isEnabled = true
                            sound.x = 915f
                            view.visibility = View.VISIBLE

                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                            TODO("Not yet implemented")
                        }
                    })
                    sound.startAnimation(animacion_sonido_2)
                    animacion_colroes_2.setAnimationListener(object:Animation.AnimationListener{
                        override fun onAnimationStart(animation: Animation?) {
                            colores.isEnabled = false
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            colores.clearAnimation()
                            colores.visibility = View.INVISIBLE
                        }

                        override fun onAnimationRepeat(animation: Animation?) {
                            TODO("Not yet implemented")
                        }
                    })
                    colores.startAnimation(animacion_colroes_2)
                    v_menu = "tapado"
                }
            }


            //botones
            val animacion = AnimationUtils.loadAnimation(applicationContext, R.anim.rescalado)


            sound.setOnClickListener {
                if (son == "activo"){
                    son = "desactivado"
                    sound.setImageResource(drawable.sonido_inactivo)
                    pref.edit().putString("sonido", "desactivado").apply()
                }else {
                    son = "activo"
                    sound.setImageResource(drawable.sonido_activado)
                    pref.edit().putString("sonido", "activo").apply()
                }
            }

            val sonido = MediaPlayer.create(this, R.raw.botones)
            fun sonido(so: String){
                if (so == "activo"){
                    sonido.start()
                }else {

                }
            }
            facil.setOnClickListener {
                sonido(son)
                facil.startAnimation(animacion)
                val intent = Intent(this, MainActivity::class.java)
                if (s_2.isChecked) {
                    intent.putExtra("idioma", "es")
                } else {
                    intent.putExtra("idioma", "en")
                }
                startActivities(arrayOf(intent))
            }

            dificil.setOnClickListener {
                sonido(son)
                dificil.startAnimation(animacion)
                val recor = record_h.text.toString()
                val intent = Intent(this, dificilActivity::class.java)
                intent.putExtra("puntos", recor)
                if (s_2.isChecked) {
                    intent.putExtra("idioma", "es")
                } else {
                    intent.putExtra("idioma", "en")
                }
                startActivities(arrayOf(intent))
            }
            crazy.setOnClickListener {
                sonido(son)
                crazy.startAnimation(animacion)
                val intent = Intent(this, crazyActivity::class.java)
                if (s_2.isChecked) {
                    intent.putExtra("idioma", "es")
                } else {
                    intent.putExtra("idioma", "en")
                }
                startActivities(arrayOf(intent))
            }

            val valor = intent.extras?.getString("valor").orEmpty()
            if (valor.isEmpty()) {

            } else {
                pref.edit().putString("puntaje", valor).apply()
            }
            // switch
            if (idioma!!.isEmpty()){
            }else {
                if (idioma == "es") {
                    s_2.isChecked = true
                    s_2.text = "ES"
                    facil.text = "Facil"
                    dificil.text = "Dificil"
                    crazy.text = "Loco"
                    texto.text = "Puntuacion: "
                } else {
                    s_2.isChecked = false
                    s_2.text = "EN"
                    facil.text = "Easy"
                    dificil.text = "Hard"
                    crazy.text = "Crazy"
                    texto.text = "Score: "
                }
            }
            s_2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (s_2.isChecked) {
                    s_2.text = "ES"
                    facil.text = "Facil"
                    dificil.text = "Dificil"
                    texto.text = "Puntuacion: "
                    crazy.text = "Loco"
                    pref.edit().putString("idioma", "es").apply()
                } else {
                    s_2.text = "EN"
                    facil.text = "Easy"
                    dificil.text = "Hard"
                    texto.text = "Score: "
                    crazy.text = "Crazy"
                    pref.edit().putString("idioma", "en").apply()
                }
            }




            // puntos
            val ani = AnimationUtils.loadAnimation(this, R.anim.reiniciar)
            restart.setOnClickListener {
                restart.startAnimation(ani)
                animacion.setAnimationListener(object:Animation.AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {
                        restart.isEnabled = false
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        restart.isEnabled = true
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        TODO("Not yet implemented")
                    }
                })
                record_h.text = "0"
                pref.edit().putString("puntaje", "0").apply()
                restart.visibility = View.INVISIBLE
                tiempo.visibility = View.INVISIBLE
                pref.edit().remove("time").apply()
            }

            val p = pref.getString("puntaje", "0")
            record_h.text = p


            // dialogo
            var contador = 0
            fun dialogo() {
                val builder = AlertDialog.Builder(this)
                builder
                    .setTitle("Information")
                    .setMessage("This app is made entirely by a single individual, so I apologize for any errors it may have.")
                    .setPositiveButton("Accept") { _, _ ->
                        contador += 1
                        pref.edit().putInt("numero", contador).apply()
                    }
                    .setNegativeButton("Decline") { _, _ ->

                    }
                    .show()
            }
            val c = pref.getInt("numero", 0)
            contador += c
            if (contador == 0) {
                dialogo()
            } else {

            }

            val r = record_h.text.toString().toInt()
            if (r == 0){
                restart.visibility = View.INVISIBLE
            }else{
                restart.visibility = View.VISIBLE
            }
        }
        juego()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}