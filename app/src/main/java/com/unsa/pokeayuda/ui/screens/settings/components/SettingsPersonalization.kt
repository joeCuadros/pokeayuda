package com.unsa.pokeayuda.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.unsa.pokeayuda.domain.model.Theme
import com.unsa.pokeayuda.utils.constants.GenerationConstants

private const val MIN_SYNC_DAYS = 1
private const val MAX_SYNC_DAYS = 60

@Composable
fun SettingsPersonalization(
    generation: String,
    theme: Theme,
    syncDays: Int,
    onGenerationChange: (String) -> Unit,
    onThemeChange: (Theme) -> Unit,
    onSyncDaysChange: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            SettingsPickerField(
                label = "Generación",
                icon = Icons.Default.Layers,
                selectedText = generation,
                options = GenerationConstants.getGenerationNames(),
                optionText = { it },
                onOptionSelected = onGenerationChange
            )

            SettingsPickerField(
                label = "Tema",
                icon = Icons.Default.Palette,
                selectedText = theme.displayName,
                options = Theme.entries,
                optionText = { it.displayName },
                onOptionSelected = onThemeChange
            )

            Column {
                Text(
                    text = "Días de sincronización",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilledTonalIconButton(
                            enabled = syncDays > MIN_SYNC_DAYS,
                            onClick = { onSyncDaysChange(syncDays - 1) }
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Menos días")
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$syncDays",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (syncDays == 1) "día" else "días",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        FilledTonalIconButton(
                            enabled = syncDays < MAX_SYNC_DAYS,
                            onClick = { onSyncDaysChange(syncDays + 1) }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Más días")
                        }
                    }
                }
            }
        }
    }
}