package com.unsa.pokeayuda.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import com.unsa.pokeayuda.domain.model.Theme


/** Elige el ColorScheme completo (todos los tokens explicitos) para un tema + modo. */
private fun colorSchemeFor(theme: Theme, darkTheme: Boolean): ColorScheme = when (theme) {
    Theme.OCEANO     -> if (darkTheme) OceanoDarkScheme     else OceanoLightScheme
    Theme.BOSQUE     -> if (darkTheme) BosqueDarkScheme     else BosqueLightScheme
    Theme.ATARDECER  -> if (darkTheme) AtardecerDarkScheme  else AtardecerLightScheme
    Theme.LAVANDA    -> if (darkTheme) LavandaDarkScheme    else LavandaLightScheme
    Theme.CORAL      -> if (darkTheme) CoralDarkScheme      else CoralLightScheme
    Theme.MEDIANOCHE -> if (darkTheme) MedianocheDarkScheme else MedianocheLightScheme
    Theme.ESMERALDA  -> if (darkTheme) EsmeraldaDarkScheme  else EsmeraldaLightScheme
    Theme.RUBI       -> if (darkTheme) RubiDarkScheme       else RubiLightScheme
    Theme.AMBAR      -> if (darkTheme) AmbarDarkScheme      else AmbarLightScheme
    Theme.CIELO      -> if (darkTheme) CieloDarkScheme      else CieloLightScheme
    Theme.ROSA       -> if (darkTheme) RosaDarkScheme       else RosaLightScheme
    Theme.GRAFITO    -> if (darkTheme) GrafitoDarkScheme    else GrafitoLightScheme
}

/** Elige la Typography completa (15 estilos) para un tema. No cambia entre claro/oscuro. */
private fun typographyFor(theme: Theme): Typography = when (theme) {
    Theme.OCEANO     -> OceanoTypography
    Theme.BOSQUE     -> BosqueTypography
    Theme.ATARDECER  -> AtardecerTypography
    Theme.LAVANDA    -> LavandaTypography
    Theme.CORAL      -> CoralTypography
    Theme.MEDIANOCHE -> MedianocheTypography
    Theme.ESMERALDA  -> EsmeraldaTypography
    Theme.RUBI       -> RubiTypography
    Theme.AMBAR      -> AmbarTypography
    Theme.CIELO      -> CieloTypography
    Theme.ROSA       -> RosaTypography
    Theme.GRAFITO    -> GrafitoTypography
}

@Composable
fun PokeayudaTheme(
    theme: Theme = Theme.Default,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = colorSchemeFor(theme, darkTheme)
    val typography = typographyFor(theme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}