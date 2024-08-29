package com.clicc

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // actualizar en la Google Play console debido a que he hecho el guardado por punto en easy

            fun juego_facil() {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                val sonido = MediaPlayer.create(this, R.raw.botones)
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                val col = pref.getString("colores", "")
                fun sonido (){
                    val s = pref.getString("sonido", "activo")
                    if (s == "activo"){
                        sonido.start()
                    }else {

                    }
                }
                val animacion_b = AnimationUtils.loadAnimation(this, R.anim.numeros)
                val color = findViewById<TextView>(R.id.color)
                val puntuacion = findViewById<TextView>(R.id.puntuacion)
                val b_rojo = findViewById<AppCompatButton>(R.id.b_rojo)
                val b_azul = findViewById<AppCompatButton>(R.id.b_azul)
                val b_verde = findViewById<AppCompatButton>(R.id.b_verde)
                val b_amarillo = findViewById<AppCompatButton>(R.id.b_amarillo)
                val record = findViewById<TextView>(R.id.record)
                val reiniciar = findViewById<ShapeableImageView>(R.id.reiniciar)
                val idiom = intent.extras?.getString("idioma").orEmpty()
                val fondo = findViewById<ConstraintLayout>(R.id.main)

                val texto_1 = findViewById<TextView>(R.id.texto_1)
                val texto_2 = findViewById<TextView>(R.id.texto_2)



                // colores

                fun pintado(lista: List<String>, lista_colores: List<Int>){

                    for ( i in 0..4){
                        if (i == 1){
                            b_rojo.text = lista[0]
                            b_rojo.setTextColor(ContextCompat.getColor(this, lista_colores[0]))
                        }else if (i == 2){
                            b_azul.text = lista[1]
                            b_azul.setTextColor(ContextCompat.getColor(this, lista_colores[1]))
                        }else if (i == 3){
                            b_verde.text = lista[2]
                            b_verde.setTextColor(ContextCompat.getColor(this, lista_colores[2]))
                        }else {
                            b_amarillo.text = lista[3]
                            b_amarillo.setTextColor(ContextCompat.getColor(this, lista_colores[3]))
                        }
                    }

                }
                var colores = listOf(R.color.Red, R.color.Blue, R.color.Green, R.color.Yellow)
                var texto = listOf("Red", "Blue", "Green", "Yellow")
                var tex_1 = listOf("Rojo", "Azul", "Verde", "Amarillo")
                var t_m = mapOf("Rojo" to "Red", "Azul" to "Blue", "Verde" to "Green", "Amarillo" to "Yellow")
                if (col!!.isEmpty()){
                    colores = listOf(R.color.Red, R.color.Blue, R.color.Green, R.color.Yellow)
                    texto = listOf("Red", "Blue", "Green", "Yellow")
                    t_m = mapOf(
                        "Rojo" to "Red",
                        "Azul" to "Blue",
                        "Verde" to "Green",
                        "Amarillo" to "Yellow"
                    )
                    tex_1 = listOf("Rojo", "Azul", "Verde", "Amarillo")
                }else {
                    if (col == "Color: 1") {
                        colores = listOf(R.color.Red, R.color.Blue, R.color.Green, R.color.Yellow)
                        texto = listOf("Red", "Blue", "Green", "Yellow")
                        t_m = mapOf(
                            "Rojo" to "Red",
                            "Azul" to "Blue",
                            "Verde" to "Green",
                            "Amarillo" to "Yellow"
                        )
                        tex_1 = listOf("Rojo", "Azul", "Verde", "Amarillo")
                    } else if (col == "Color: 2") {
                        colores = listOf(
                            R.color.azul_1,
                            R.color.marron_1,
                            R.color.naranja_1,
                            R.color.rosa_1
                        )
                        texto = listOf("Blue", "White", "Salmon", "Pink")
                        tex_1 = listOf("Azul", "Blanco", "Salmon", "Rosa")
                        t_m = mapOf(
                            "Azul" to "Blue",
                            "Blanco" to "White",
                            "Salmon" to "Salmon",
                            "Rosa" to "Pink"
                        )

                    } else {
                        colores = listOf(
                            R.color.gris_2,
                            R.color.rojo_2,
                            R.color.amarillo_2,
                            R.color.morado_2
                        )
                        texto = listOf("Grey", "Red", "Yellow", "Purple")
                        tex_1 = listOf("Gris", "Rojo", "Amarillo", "Morado")
                        t_m = mapOf(
                            "Gris" to "Grey",
                            "Rojo" to "Red",
                            "Amarillo" to "Yellow",
                            "Morado" to "Purple"
                        )
                    }
                }

                pintado(texto, colores)

                fun ingles() {
                    texto_1.text = "Score"
                    texto_2.text = "Record"
                    fun princial() {
                        val c = colores.shuffled().take(1)[0]
                        val q = texto.shuffled().random()
                        val t_1 = q.replace("[", "")
                        val t_2 = t_1.replace("]", "")

                        color.setTextColor(ContextCompat.getColor(this, c))
                        color.text = t_2
                    }
                    princial()
                    val ani = AnimationUtils.loadAnimation(this, R.anim.reiniciar)
                    reiniciar.setOnClickListener {
                        ani.setAnimationListener(object:Animation.AnimationListener{
                            override fun onAnimationStart(animation: Animation?) {
                                reiniciar.isEnabled = false
                            }

                            override fun onAnimationEnd(animation: Animation?) {
                                reiniciar.isEnabled = true
                            }

                            override fun onAnimationRepeat(animation: Animation?) {
                                TODO("Not yet implemented")
                            }
                        })
                        reiniciar.startAnimation(ani)
                        record.text = "0"
                        puntuacion.text = "0"
                        pref.edit().putString("p_1", "0").apply()
                        pref.edit().putString("puntuacion", "0").apply()
                        princial()
                        reiniciar.visibility = View.INVISIBLE
                    }


                    fun presionar(boton: AppCompatButton) {
                        boton.setOnClickListener {
                            sonido()
                            val animacion = AnimationUtils.loadAnimation(applicationContext, R.anim.rescalado)
                            boton.startAnimation(animacion)
                            val t = color.text.toString()
                            val b = boton.text.toString()
                            val p_s = puntuacion.text.toString()
                            var i = p_s.toInt()

                            if (t == b) {
                                i += 1
                                puntuacion.text = i.toString()
                                puntuacion.startAnimation(animacion_b)
                                princial()
                                pref.edit().putString("p_1", i.toString()).apply()
                                reiniciar.visibility = View.VISIBLE
                            } else {
                                val animacion = AnimationUtils.loadAnimation(applicationContext, R.anim.rotacion)
                                animacion.setAnimationListener(object:Animation.AnimationListener{
                                    override fun onAnimationStart(animation: Animation?) {
                                        boton.isEnabled = false
                                    }

                                    override fun onAnimationEnd(animation: Animation?) {
                                        boton.isEnabled = true
                                    }

                                    override fun onAnimationRepeat(animation: Animation?) {

                                    }
                                })
                                boton.startAnimation(animacion)

                                pref.edit().putString("p_1", "0").apply()
                                princial()
                                val r = record.text.toString()
                                if (r.isEmpty()) {
                                    val s = puntuacion.text.toString()
                                    record.text = s
                                } else {

                                    val i = r.toInt()
                                    val p_s = puntuacion.text.toString()
                                    var p_i = p_s.toInt()
                                    if (p_i > i) {
                                        record.text = p_s
                                        record.startAnimation(animacion_b)
                                        val resta = p_i - i
                                        Toast.makeText(
                                            this,
                                            "You have surpassed your record with: $resta",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        pref.edit().putString("puntuacion", p_s).apply()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "You have not surpassed your score",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    p_i = 0
                                    puntuacion.text = p_i.toString()
                                }

                            }
                        }
                    }

                    val guardado = pref.getString("puntuacion", "0")
                    record.text = guardado.toString()

                    val p = pref.getString("p_1", "0")
                    puntuacion.text = p

                    presionar(b_rojo)
                    presionar(b_azul)
                    presionar(b_verde)
                    presionar(b_amarillo)
                }

                fun español() {
                    texto_1.text = "Puntuacion"
                    texto_2.text = "Record"


                    fun princial() {

                        val c = colores.shuffled().take(1)[0]
                        val q = tex_1.shuffled().random()
                        val t_1 = q.replace("[", "")
                        val t_2 = t_1.replace("]", "")

                        color.setTextColor(ContextCompat.getColor(this, c))
                        color.text = t_2
                    }
                    princial()
                    val ani = AnimationUtils.loadAnimation(this, R.anim.reiniciar)
                    reiniciar.setOnClickListener {
                        ani.setAnimationListener(object:Animation.AnimationListener{
                            override fun onAnimationStart(animation: Animation?) {
                                reiniciar.isEnabled = false
                            }

                            override fun onAnimationEnd(animation: Animation?) {
                                reiniciar.isEnabled = true
                            }

                            override fun onAnimationRepeat(animation: Animation?) {
                                TODO("Not yet implemented")
                            }
                        })
                        reiniciar.startAnimation(ani)
                        record.text = "0"
                        puntuacion.text = "0"
                        pref.edit().putString("p_1", "0").apply()
                        pref.edit().putString("puntuacion", "0").apply()
                        princial()
                        reiniciar.visibility = View.INVISIBLE
                    }

                    fun presionar(boton: AppCompatButton) {
                        boton.setOnClickListener {
                            sonido()
                            val animacion = AnimationUtils.loadAnimation(applicationContext, R.anim.rescalado)
                            animacion.setAnimationListener(object:Animation.AnimationListener{
                                override fun onAnimationStart(animation: Animation?) {
                                    boton.isEnabled = false
                                }

                                override fun onAnimationEnd(animation: Animation?) {
                                    boton.isEnabled = true
                                }

                                override fun onAnimationRepeat(animation: Animation?) {
                                }
                            })
                            boton.startAnimation(animacion)

                            val t = color.text.toString()
                            val t_1 = t_m[t]
                            val b = boton.text.toString()
                            val p_s = puntuacion.text.toString()
                            var i = p_s.toInt()

                            if (t_1 == b) {
                                i += 1
                                puntuacion.text = i.toString()
                                puntuacion.startAnimation(animacion_b)
                                princial()
                                pref.edit().putString("p_1", i.toString()).apply()
                                reiniciar.visibility = View.VISIBLE
                            } else {
                                val animacion = AnimationUtils.loadAnimation(applicationContext, R.anim.rotacion)
                                animacion.setAnimationListener(object:Animation.AnimationListener{
                                    override fun onAnimationStart(animation: Animation?) {
                                        boton.isEnabled = false
                                    }

                                    override fun onAnimationEnd(animation: Animation?) {
                                        boton.isEnabled = true
                                    }

                                    override fun onAnimationRepeat(animation: Animation?) {
                                        TODO("Not yet implemented")
                                    }
                                })
                                boton.startAnimation(animacion)
                                pref.edit().putString("p_1", "0").apply()
                                princial()
                                val r = record.text.toString()
                                if (r.isEmpty()) {
                                    val s = puntuacion.text.toString()
                                    record.text = s
                                } else {
                                    val i = r.toInt()
                                    val p_s = puntuacion.text.toString()
                                    var p_i = p_s.toInt()
                                    if (p_i > i) {
                                        record.text = p_s
                                        val resta = p_i - i
                                        Toast.makeText(
                                            this,
                                            "Has superado tu record con: $resta",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        pref.edit().putString("puntuacion", p_s).apply()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "No has superado tu puntuacion",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    p_i = 0
                                    puntuacion.text = p_i.toString()
                                }

                            }
                        }
                    }

                    val guardado = pref.getString("puntuacion", "0")
                    record.text = guardado.toString()

                    val p = pref.getString("p_1", "0")
                    puntuacion.text = p
                    presionar(b_rojo)
                    presionar(b_azul)
                    presionar(b_verde)
                    presionar(b_amarillo)
                }

                if (idiom.isEmpty()){

                }else{
                    if (idiom == "es"){
                        español()
                    }else{
                        ingles()
                    }
                }



                val p = pref.getString("p_1", "0")
                puntuacion.text = p

                val pun = puntuacion.text.toString().toInt()
                val r = record.text.toString().toInt()

                if (pun == 0 && r == 0){
                    reiniciar.visibility = View.INVISIBLE
                }else{
                    reiniciar.visibility = View.VISIBLE
                }

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                )
            }
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.activity_horizontal)
                juego_facil()
            } else {
                setContentView(R.layout.activity_main)
                juego_facil()
            }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}