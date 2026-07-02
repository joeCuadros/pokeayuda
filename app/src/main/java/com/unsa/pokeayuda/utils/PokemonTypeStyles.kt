package com.unsa.pokeayuda.utils

import androidx.compose.ui.graphics.Color
import com.unsa.pokeayuda.ui.theme.PokemonTypeStyle

object PokemonTypeStyles {
    private val types = mapOf(
        "normal" to PokemonTypeStyle(
            displayName = "Normal",
            textColor = Color(0xFF5F6368),
            backgroundColor = Color(0xFFF5F5F5),
            borderColor = Color(0xFF9E9E9E)
        ),
        "fighting" to PokemonTypeStyle(
            displayName = "Lucha",
            textColor = Color(0xFF8D2E1F),
            backgroundColor = Color(0xFFFFE5E0),
            borderColor = Color(0xFFC03028)
        ),
        "flying" to PokemonTypeStyle(
            displayName = "Volador",
            textColor = Color(0xFF4A5EBB),
            backgroundColor = Color(0xFFE8EEFF),
            borderColor = Color(0xFF6D8EEB)
        ),
        "poison" to PokemonTypeStyle(
            displayName = "Veneno",
            textColor = Color(0xFF6A1B9A),
            backgroundColor = Color(0xFFF3E5F5),
            borderColor = Color(0xFF9C27B0)
        ),
        "ground" to PokemonTypeStyle(
            displayName = "Tierra",
            textColor = Color(0xFF8D6E63),
            backgroundColor = Color(0xFFF5ECE3),
            borderColor = Color(0xFFD2B48C)
        ),
        "rock" to PokemonTypeStyle(
            displayName = "Roca",
            textColor = Color(0xFF6D4C41),
            backgroundColor = Color(0xFFEDE7E3),
            borderColor = Color(0xFFA1887F)
        ),
        "bug" to PokemonTypeStyle(
            displayName = "Bicho",
            textColor = Color(0xFF557A0A),
            backgroundColor = Color(0xFFF1F8E9),
            borderColor = Color(0xFF8BC34A)
        ),
        "ghost" to PokemonTypeStyle(
            displayName = "Fantasma",
            textColor = Color(0xFF4527A0),
            backgroundColor = Color(0xFFEDE7F6),
            borderColor = Color(0xFF7E57C2)
        ),
        "steel" to PokemonTypeStyle(
            displayName = "Acero",
            textColor = Color(0xFF546E7A),
            backgroundColor = Color(0xFFECEFF1),
            borderColor = Color(0xFF90A4AE)
        ),
        "fire" to PokemonTypeStyle(
            displayName = "Fuego",
            textColor = Color(0xFFBF360C),
            backgroundColor = Color(0xFFFFEBE5),
            borderColor = Color(0xFFFF7043)
        ),
        "water" to PokemonTypeStyle(
            displayName = "Agua",
            textColor = Color(0xFF1565C0),
            backgroundColor = Color(0xFFE3F2FD),
            borderColor = Color(0xFF42A5F5)
        ),
        "grass" to PokemonTypeStyle(
            displayName = "Planta",
            textColor = Color(0xFF2E7D32),
            backgroundColor = Color(0xFFE8F5E9),
            borderColor = Color(0xFF66BB6A)
        ),
        "electric" to PokemonTypeStyle(
            displayName = "Eléctrico",
            textColor = Color(0xFF8A6D00),
            backgroundColor = Color(0xFFFFF8E1),
            borderColor = Color(0xFFFFCA28)
        ),
        "psychic" to PokemonTypeStyle(
            displayName = "Psíquico",
            textColor = Color(0xFFC2185B),
            backgroundColor = Color(0xFFFCE4EC),
            borderColor = Color(0xFFEC407A)
        ),
        "ice" to PokemonTypeStyle(
            displayName = "Hielo",
            textColor = Color(0xFF00838F),
            backgroundColor = Color(0xFFE0F7FA),
            borderColor = Color(0xFF4DD0E1)
        ),
        "dragon" to PokemonTypeStyle(
            displayName = "Dragón",
            textColor = Color(0xFF283593),
            backgroundColor = Color(0xFFE8EAF6),
            borderColor = Color(0xFF5C6BC0)
        ),
        "dark" to PokemonTypeStyle(
            displayName = "Siniestro",
            textColor = Color(0xFF3E2723),
            backgroundColor = Color(0xFFEFEBE9),
            borderColor = Color(0xFF6D4C41)
        ),
        "fairy" to PokemonTypeStyle(
            displayName = "Hada",
            textColor = Color(0xFFAD1457),
            backgroundColor = Color(0xFFFCE4EC),
            borderColor = Color(0xFFF48FB1)
        ),
        "stellar" to PokemonTypeStyle(
            displayName = "Estelar",
            textColor = Color(0xFF512DA8),
            backgroundColor = Color(0xFFF3E5F5),
            borderColor = Color(0xFF9575CD)
        ),
        "unknown" to PokemonTypeStyle(
            displayName = "Desconocido",
            textColor = Color(0xFF616161),
            backgroundColor = Color(0xFFF5F5F5),
            borderColor = Color(0xFFBDBDBD)
        ),
        "shadow" to PokemonTypeStyle(
            displayName = "Sombra",
            textColor = Color(0xFF212121),
            backgroundColor = Color(0xFFE0E0E0),
            borderColor = Color(0xFF757575)
        )
    )

    fun get(type: String): PokemonTypeStyle {
        return types[type.lowercase()] ?: PokemonTypeStyle(
            displayName = type.replaceFirstChar { it.uppercase() },
            textColor = Color.Black,
            backgroundColor = Color(0xFFF5F5F5),
            borderColor = Color.Gray
        )
    }
}