package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EdicionNotaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTA = "extra_nota"
        const val EXTRA_POSICION = "extra_posicion"
    }

    private lateinit var etTitulo: EditText
    private lateinit var etContenido: EditText
    private var notaExistente: Nota? = null
    private var posicion: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edicion_nota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etTitulo = findViewById(R.id.etTitulo)
        etContenido = findViewById(R.id.etContenido)
        val btnGuardarNota = findViewById<Button>(R.id.btnGuardarNota)

        posicion = intent.getIntExtra(EXTRA_POSICION, -1)
        notaExistente = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_NOTA, Nota::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_NOTA) as? Nota
        }

        notaExistente?.let {
            etTitulo.setText(it.titulo)
            etContenido.setText(it.contenido)
        }

        btnGuardarNota.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val contenido = etContenido.text.toString().trim()

            if (titulo.isEmpty()) {
                Toast.makeText(this, "Ingresa un título para la nota", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notaResultado = notaExistente?.apply {
                this.titulo = titulo
                this.contenido = contenido
            } ?: Nota(titulo = titulo, contenido = contenido)

            val resultIntent = Intent().apply {
                putExtra(EXTRA_NOTA, notaResultado)
                putExtra(EXTRA_POSICION, posicion)
            }

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
