package com.clicc

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class dificilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dificil)
        fun juego_dificil() {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            var tiempo = 0
            var oportunities = false
            val sonido = MediaPlayer.create(this, R.raw.botones)
            val pref = PreferenceManager.getDefaultSharedPreferences(this)
            val col = pref.getString("colores", "")
            fun sonido(){
                val s = pref.getString("sonido", "activo")
                if (s == "activo"){
                    sonido.start()
                }else {

                }
            }
            val ani = AnimationUtils.loadAnimation(this, R.anim.numeros)
            val color = findViewById<TextView>(R.id.color)
            val puntuacion = findViewById<TextView>(R.id.puntuacion)
            val b_rojo = findViewById<AppCompatButton>(R.id.b_rojo)
            val b_azul = findViewById<AppCompatButton>(R.id.b_azul)
            val b_verde = findViewById<AppCompatButton>(R.id.b_verde)
            val b_amarillo = findViewById<AppCompatButton>(R.id.b_amarillo)
            val idiom = intent.extras?.getString("idioma").orEmpty()
            var time = 0
            val recor = intent.extras?.getString("puntos").orEmpty()
            val progreso = findViewById<LinearProgressIndicator>(R.id.progreso)
            val tapador = findViewById<View>(R.id.tapador)

            fun fallo() {
                finish()
                val i = recor.toInt()
                val p = puntuacion.text.toString()
                val i_2 = p.toInt()
                if (i_2 > i) {
                    val intent = Intent(this, pagina_inicioActivity::class.java)
                    intent.putExtra("valor", p)
                    intent.putExtra("time", time.toString())
                    startActivities(arrayOf(intent))
                } else {
                    startActivities(
                        arrayOf(
                            Intent(
                                this,
                                pagina_inicioActivity::class.java
                            )
                        )
                    )
                }
            }


            // contador
            var trabajo: Job? = null
            fun contador(valor:Int) {
                val tiempo = findViewById<TextView>(R.id.tiempo)
                progreso.max = valor
                trabajo = lifecycleScope.launch {
                    for (i in valor downTo 0) {
                        tiempo.text = i.toString()
                        progreso.progress = i
                        val p = progreso.progress
                        if (valor != 60) {
                            if (p <= 97) {
                                tapador.visibility = View.VISIBLE
                            } else {
                                tapador.visibility = View.INVISIBLE
                            }
                        }else {
                            if (p <= 98){
                                tapador.visibility = View.VISIBLE
                            }else {
                                tapador.visibility = View.INVISIBLE
                            }
                        }
                        if (i == 0) {
                            delay(1)
                            fallo()
                            Log.e("contador", "fallo")
                        }else{
                            time ++
                            delay(1000)
                        }
                    }
                }
            }
            // dialogo
            val builder = Dialog(this)
            val view = layoutInflater.inflate(R.layout.activity_dialogo, null)
            builder.setCancelable(false)
            builder.setContentView(view)
            val b_15 = view.findViewById<CheckBox>(R.id.t15)
            val b_30 = view.findViewById<CheckBox>(R.id.t30)
            val b_60 = view.findViewById<CheckBox>(R.id.t60)
            builder.show()
            fun boton (b: CheckBox, v:Int){
                b.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked){
                        tapador.visibility = View.INVISIBLE
                        tiempo += v
                        contador(v)
                        builder.dismiss()
                    }else{

                    }
                }

            }

            boton(b_15, 15)
            boton(b_30, 30)
            boton(b_60, 60)



            val texto_1 = findViewById<TextView>(R.id.texto_1)


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
                    colores =
                        listOf(R.color.azul_1, R.color.marron_1, R.color.naranja_1, R.color.rosa_1)
                    texto = listOf("Blue", "White", "Salmon", "Pink")
                    tex_1 = listOf("Azul", "Blanco", "Salmon", "Rosa")
                    t_m = mapOf(
                        "Azul" to "Blue",
                        "Blanco" to "White",
                        "Salmon" to "Salmon",
                        "Rosa" to "Pink"
                    )

                } else {
                    colores =
                        listOf(R.color.gris_2, R.color.rojo_2, R.color.amarillo_2, R.color.morado_2)
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

            // oportunidades
            fun oportinidades(){
                val builde = Dialog(this)
                var job: Job? = null
                val scop = CoroutineScope(Dispatchers.Main)
                fun contado(con: LinearProgressIndicator){
                    job = scop.launch {
                        for (i in con.progress..30){
                            if (i == 0){
                                builde.dismiss()
                                contador(tiempo)
                                scop.cancel()
                            }else if (i == 30){
                                builde.dismiss()
                                fallo()
                                Log.e("contado", "fallo")
                            }else {
                                con.progress = i
                                delay(150)
                            }
                        }
                    }
                }
                val vie = layoutInflater.inflate(R.layout.dialog_opor, null)

                @SuppressLint("MissingInflatedId")
                val presionar = vie.findViewById<View>(R.id.save)
                presionar.setBackgroundColor(Color.TRANSPARENT)
                val contador = vie.findViewById<LinearProgressIndicator>(R.id.contador)
                contador.trackColor = Color.TRANSPARENT
                contador.setBackgroundColor(Color.TRANSPARENT)
                builde.setContentView(vie)
                builde.setCancelable(false)
                builde.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                builde.show()

                contado(contador)
                presionar.setOnClickListener {
                    val valor = contador.progress - 1
                    job!!.cancel()
                    contador.progress = valor
                    contado(contador)
                }


            }

            fun ingles() {
                texto_1.text = "Score"
                fun princial() {
                    val c = colores.shuffled().take(1)[0]
                    val q = texto.shuffled().random()
                    val t_1 = q.replace("[", "")
                    val t_2 = t_1.replace("]", "")

                    color.setTextColor(ContextCompat.getColor(this, c))
                    color.text = t_2
                }
                princial()



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
                            puntuacion.startAnimation(ani)
                            princial()
                        } else {
                            if (oportunities){
                                fallo()
                            }else {
                                oportunities = true
                                trabajo!!.cancel()
                                oportinidades()
                            }

                        }
                    }
                }



                presionar(b_rojo)
                presionar(b_azul)
                presionar(b_verde)
                presionar(b_amarillo)


            }

            fun español() {
                texto_1.text = "Puntuacion"

                fun princial() {


                    val c = colores.shuffled().take(1)[0]
                    val q = tex_1.shuffled().random()
                    val t_1 = q.replace("[", "")
                    val t_2 = t_1.replace("]", "")

                    color.setTextColor(ContextCompat.getColor(this, c))
                    color.text = t_2
                }
                princial()


                fun presionar(boton: AppCompatButton) {
                    boton.setOnClickListener {
                        sonido()
                        val animacion = AnimationUtils.loadAnimation(applicationContext, R.anim.rescalado)
                        boton.startAnimation(animacion)
                        val t = color.text.toString()
                        val t_1 = t_m[t]
                        val b = boton.text.toString()
                        val p_s = puntuacion.text.toString()
                        var i = p_s.toInt()
                        if (t_1 == b) {
                            i += 1
                            puntuacion.text = i.toString()
                            puntuacion.startAnimation(ani)
                            princial()
                        } else {
                            if (oportunities){
                                fallo()
                            }else {
                                oportunities = true
                                trabajo!!.cancel()
                                oportinidades()
                            }
                        }


                    }
                }

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


            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )

        }
        juego_dificil()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}