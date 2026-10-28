package com.example.intencionesimplicitas

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LISTA_PRODUCTOS = "extra_lista_productos"
        const val EXTRA_PRODUCTO_SELECCIONADO = "extra_producto_seleccionado"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @Suppress("UNCHECKED_CAST")
        val productos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_LISTA_PRODUCTOS, ArrayList::class.java) as? List<Producto> ?: emptyList()
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_LISTA_PRODUCTOS) as? List<Producto> ?: emptyList()
        }

        val rvProductos = findViewById<RecyclerView>(R.id.rvProductosLista)
        rvProductos.layoutManager = LinearLayoutManager(this)
        rvProductos.adapter = ProductoPrecioAdapter(productos) { productoSeleccionado ->
            val resultIntent = Intent().apply {
                putExtra(EXTRA_PRODUCTO_SELECCIONADO, productoSeleccionado)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
