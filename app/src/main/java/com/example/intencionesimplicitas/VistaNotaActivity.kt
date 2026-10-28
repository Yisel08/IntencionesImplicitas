package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VistaNotaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTA = "extra_nota"
        const val EXTRA_POSICION = "extra_posicion"
    }

    private lateinit var tvVistaTitulo: TextView
    private lateinit var tvVistaContenido: TextView
    private var nota: Nota? = null
    private var posicion: Int = -1
    private var notaModificada: Boolean = false

    private val editarNotaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val notaEditada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra(EdicionNotaActivity.EXTRA_NOTA, Nota::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra(EdicionNotaActivity.EXTRA_NOTA) as? Nota
            }

            notaEditada?.let {
                nota = it
                tvVistaTitulo.text = it.titulo
                tvVistaContenido.text = it.contenido
                notaModificada = true

                val resultIntent = Intent().apply {
                    putExtra(EXTRA_NOTA, it)
                    putExtra(EXTRA_POSICION, posicion)
                }
                setResult(RESULT_OK, resultIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vista_nota)

        val toolbar = findViewById<Toolbar>(R.id.toolbarVistaNota)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvVistaTitulo = findViewById(R.id.tvVistaTitulo)
        tvVistaContenido = findViewById(R.id.tvVistaContenido)

        posicion = intent.getIntExtra(EXTRA_POSICION, -1)
        nota = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_NOTA, Nota::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_NOTA) as? Nota
        }

        nota?.let {
            tvVistaTitulo.text = it.titulo
            tvVistaContenido.text = it.contenido
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vista_nota, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_editar -> {
                nota?.let {
                    val intent = Intent(this, EdicionNotaActivity::class.java).apply {
                        putExtra(EdicionNotaActivity.EXTRA_NOTA, it)
                        putExtra(EdicionNotaActivity.EXTRA_POSICION, posicion)
                    }
                    editarNotaLauncher.launch(intent)
                }
                true
            }
            R.id.action_compartir -> {
                nota?.let {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, it.titulo)
                        putExtra(Intent.EXTRA_TEXT, "${it.titulo}\n\n${it.contenido}")
                    }
                    startActivity(Intent.createChooser(shareIntent, "Compartir nota"))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
