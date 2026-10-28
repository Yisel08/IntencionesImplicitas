package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaNotasActivity : AppCompatActivity() {

    private val listaNotas = mutableListOf(
        Nota(
            titulo = "Bienvenido a Notas",
            contenido = "Esta es tu primera nota de ejemplo. Haz clic en el menú superior para agregar nuevas notas o presiona esta nota para verla y compartirla."
        ),
        Nota(
            titulo = "Lista de Compras",
            contenido = "1. Leche\n2. Pan\n3. Huevos\n4. Frutas frescas"
        )
    )

    private lateinit var adapter: NotasAdapter

    private val nuevaNotaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val nuevaNota = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra(EdicionNotaActivity.EXTRA_NOTA, Nota::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra(EdicionNotaActivity.EXTRA_NOTA) as? Nota
            }

            nuevaNota?.let {
                adapter.agregarNota(it)
            }
        }
    }

    private val verNotaLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val notaActualizada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra(VistaNotaActivity.EXTRA_NOTA, Nota::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra(VistaNotaActivity.EXTRA_NOTA) as? Nota
            }
            val posicion = result.data?.getIntExtra(VistaNotaActivity.EXTRA_POSICION, -1) ?: -1

            if (notaActualizada != null && posicion != -1) {
                adapter.actualizarNota(posicion, notaActualizada)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_notas)

        val toolbar = findViewById<Toolbar>(R.id.toolbarListaNotas)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvNotas = findViewById<RecyclerView>(R.id.rvNotas)
        adapter = NotasAdapter(listaNotas) { nota, posicion ->
            val intent = Intent(this, VistaNotaActivity::class.java).apply {
                putExtra(VistaNotaActivity.EXTRA_NOTA, nota)
                putExtra(VistaNotaActivity.EXTRA_POSICION, posicion)
            }
            verNotaLauncher.launch(intent)
        }

        rvNotas.layoutManager = LinearLayoutManager(this)
        rvNotas.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_notas, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_nueva_nota -> {
                val intent = Intent(this, EdicionNotaActivity::class.java)
                nuevaNotaLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
