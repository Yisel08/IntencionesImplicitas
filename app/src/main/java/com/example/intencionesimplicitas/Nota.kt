package com.example.intencionesimplicitas

import java.io.Serializable
import java.util.UUID

data class Nota(
    val id: String = UUID.randomUUID().toString(),
    var titulo: String,
    var contenido: String,
) : Serializable
