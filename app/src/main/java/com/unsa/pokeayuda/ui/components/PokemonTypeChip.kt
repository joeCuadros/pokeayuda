package com.unsa.pokeayuda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.utils.PokemonTypeStyles

@Composable
fun PokemonTypeChip(
    type: String,
    modifier: Modifier = Modifier
) {
    val style = PokemonTypeStyles.get(type)

    Text(
        text = style.displayName,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = style.textColor,
        modifier = modifier
            .border(
                width = 1.dp,
                color = style.borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = style.backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}