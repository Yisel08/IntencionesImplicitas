package com.example.intencionesimplicitas

import java.io.Serializable

data class Producto(
    val nombre: String,
    val precio: Double = 0.0,
    val imagen: Int = R.drawable.ic_laptop,
) : Serializable
