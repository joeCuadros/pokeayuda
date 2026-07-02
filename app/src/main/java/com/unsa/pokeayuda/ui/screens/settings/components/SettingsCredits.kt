package com.unsa.pokeayuda.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp

@Composable
fun SettingsCredits() {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            CreditRow(
                icon = Icons.Default.Cloud,
                label = "Datos desde",
                value = "https://pokeapi.co/api/v2/",
                isLink = true,
                onClick = { uriHandler.openUri("https://pokeapi.co/api/v2/") }
            )
            CreditRow(
                icon = Icons.Outlined.Person,
                label = "Desarrollado por",
                value = "Joe Cuadros"
            )
            CreditRow(
                icon = Icons.Default.Code,
                label = "Versión",
                value = "01.00 - 2026"
            )
        }
    }
}