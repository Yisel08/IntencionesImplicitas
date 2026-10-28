package com.example.intencionesimplicitas

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLlamar = findViewById<Button>(R.id.btnLlamar)
        val btnEnviarCorreo = findViewById<Button>(R.id.btnEnviarCorreo)
        val btnAbrirMapa = findViewById<Button>(R.id.btnAbrirMapa)
        val btnCompartirTexto = findViewById<Button>(R.id.btnCompartirTexto)
        val btnAbrirWeb = findViewById<Button>(R.id.btnAbrirWeb)

        // 1. Llamar a un número
        btnLlamar.setOnClickListener {
            val intentLlamar = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+573001234567")
            }
            lanzarIntentSeguro(intentLlamar, "No se encontró aplicación para realizar llamadas")
        }

        // 2. Enviar un correo
        btnEnviarCorreo.setOnClickListener {
            val intentCorreo = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:contacto@ejemplo.com")
                putExtra(Intent.EXTRA_SUBJECT, "Consulta desde App Intenciones Implícitas")
                putExtra(Intent.EXTRA_TEXT, "Hola, me comunico a través de la aplicación Android.")
            }
            lanzarIntentSeguro(intentCorreo, "No se encontró cliente de correo electrónico")
        }

        // 3. Abrir una dirección en Google Maps
        btnAbrirMapa.setOnClickListener {
            val uriMapa = Uri.parse("geo:4.60971,-74.08175?q=4.60971,-74.08175(Bogotá, Colombia)")
            val intentMapa = Intent(Intent.ACTION_VIEW, uriMapa)
            lanzarIntentSeguro(intentMapa, "No se encontró aplicación de mapas")
        }

        // 4. Compartir un texto
        btnCompartirTexto.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "¡Hola! Te comparto un mensaje desde mi aplicación Android.")
                type = "text/plain"
            }
            val chooser = Intent.createChooser(sendIntent, "Compartir mediante...")
            startActivity(chooser)
        }

        // 5. Abrir una página web
        btnAbrirWeb.setOnClickListener {
            val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"))
            lanzarIntentSeguro(intentWeb, "No se encontró navegador web")
        }
    }

    private fun lanzarIntentSeguro(intent: Intent, mensajeError: String) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show()
        }
    }
}
