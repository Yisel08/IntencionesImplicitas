package com.example.intencionesimplicitas

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalleLugarActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOMBRE = "extra_nombre"
        const val EXTRA_DESCRIPCION = "extra_descripcion"
        const val EXTRA_IMAGEN = "extra_imagen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_lugar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = intent.getStringExtra(EXTRA_NOMBRE) ?: ""
        val descripcion = intent.getStringExtra(EXTRA_DESCRIPCION) ?: ""
        val imagen = intent.getIntExtra(EXTRA_IMAGEN, 0)

        val imgDetalle = findViewById<ImageView>(R.id.imgDetalleLugar)
        val tvNombre = findViewById<TextView>(R.id.tvDetalleNombre)
        val tvDescripcion = findViewById<TextView>(R.id.tvDetalleDescripcion)

        tvNombre.text = nombre
        tvDescripcion.text = descripcion
        if (imagen != 0) {
            imgDetalle.setImageResource(imagen)
        }
    }
}
