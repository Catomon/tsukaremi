package com.github.catomon.tsukaremi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val Seed = Color(0xFF850CFF)

val PrimaryLightReducedContrast = Color(0xFF9444FF)
val OnPrimaryLightReducedContrast = Color(0xFFFFFBFF)
val PrimaryContainerLightReducedContrast = Color(0xFFE5D0FF)
val OnPrimaryContainerLightReducedContrast = Color(0xFF994EFF)
val SecondaryLightReducedContrast = Color(0xFF8A5DCB)
val OnSecondaryLightReducedContrast = Color(0xFFFFFBFF)
val SecondaryContainerLightReducedContrast = Color(0xFFE5D0FF)
val OnSecondaryContainerLightReducedContrast = Color(0xFF8F63D1)
val TertiaryLightReducedContrast = Color(0xFF867400)
val OnTertiaryLightReducedContrast = Color(0xFFFFFBFF)
val TertiaryContainerLightReducedContrast = Color(0xFFF8D800)
val OnTertiaryContainerLightReducedContrast = Color(0xFF8C7A00)
val ErrorLightReducedContrast = Color(0xFFE8121C)
val OnErrorLightReducedContrast = Color(0xFFFFFBFF)
val ErrorContainerLightReducedContrast = Color(0xFFFFCDC7)
val OnErrorContainerLightReducedContrast = Color(0xFFF01C21)
val BackgroundLightReducedContrast = Color(0xFFFFF7FF)
val OnBackgroundLightReducedContrast = Color(0xFF968D9C)
val SurfaceLightReducedContrast = Color(0xFFFFF7FF)
val OnSurfaceLightReducedContrast = Color(0xFF645C6B)
val SurfaceVariantLightReducedContrast = Color(0xFFEADEF6)
val OnSurfaceVariantLightReducedContrast = Color(0xFF81778C)
val OutlineLightReducedContrast = Color(0xFFB9AEC4)
val OutlineVariantLightReducedContrast = Color(0xFFE1D4EC)
val ScrimLightReducedContrast = Color(0xFF000000)
val InverseSurfaceLightReducedContrast = Color(0xFF342E3B)
val InverseOnSurfaceLightReducedContrast = Color(0xFF9E95A5)
val InversePrimaryLightReducedContrast = Color(0xFF984DFF)
val SurfaceDimLightReducedContrast = Color(0xFFE1D6E7)
val SurfaceBrightLightReducedContrast = Color(0xFFFFF7FF)
val SurfaceContainerLowestLightReducedContrast = Color(0xFFFFFFFF)
val SurfaceContainerLowLightReducedContrast = Color(0xFFFAF0FF)
val SurfaceContainerLightReducedContrast = Color(0xFFF5EAFB)
val SurfaceContainerHighLightReducedContrast = Color(0xFFEFE4F6)
val SurfaceContainerHighestLightReducedContrast = Color(0xFFE9DFF0)

val PrimaryDarkReducedContrast = Color(0xFFAC73FF)
val OnPrimaryDarkReducedContrast = Color(0xFF3B0078)
val PrimaryContainerDarkReducedContrast = Color(0xFF50009F)
val OnPrimaryContainerDarkReducedContrast = Color(0xFFA15FFF)
val SecondaryDarkReducedContrast = Color(0xFFA77AEA)
val OnSecondaryDarkReducedContrast = Color(0xFF3B0078)
val SecondaryContainerDarkReducedContrast = Color(0xFF4C1B8B)
val OnSecondaryContainerDarkReducedContrast = Color(0xFF9A6DDC)
val TertiaryDarkReducedContrast = Color(0xFFA69000)
val OnTertiaryDarkReducedContrast = Color(0xFF312A00)
val TertiaryContainerDarkReducedContrast = Color(0xFF433A00)
val OnTertiaryContainerDarkReducedContrast = Color(0xFF978400)
val ErrorDarkReducedContrast = Color(0xFFFF5449)
val OnErrorDarkReducedContrast = Color(0xFF5C0004)
val ErrorContainerDarkReducedContrast = Color(0xFF7B0007)
val OnErrorContainerDarkReducedContrast = Color(0xFFFF2A2A)
val BackgroundDarkReducedContrast = Color(0xFF16111D)
val OnBackgroundDarkReducedContrast = Color(0xFF675F6E)
val SurfaceDarkReducedContrast = Color(0xFF16111D)
val OnSurfaceDarkReducedContrast = Color(0xFFA99FB0)
val SurfaceVariantDarkReducedContrast = Color(0xFF4C4356)
val OnSurfaceVariantDarkReducedContrast = Color(0xFF897F94)
val OutlineDarkReducedContrast = Color(0xFF595064)
val OutlineVariantDarkReducedContrast = Color(0xFF3F3749)
val ScrimDarkReducedContrast = Color(0xFF000000)
val InverseSurfaceDarkReducedContrast = Color(0xFFE9DFF0)
val InverseOnSurfaceDarkReducedContrast = Color(0xFF696170)
val InversePrimaryDarkReducedContrast = Color(0xFF9E59FF)
val SurfaceDimDarkReducedContrast = Color(0xFF16111D)
val SurfaceBrightDarkReducedContrast = Color(0xFF3D3744)
val SurfaceContainerLowestDarkReducedContrast = Color(0xFF110C17)
val SurfaceContainerLowDarkReducedContrast = Color(0xFF1F1925)
val SurfaceContainerDarkReducedContrast = Color(0xFF231D29)
val SurfaceContainerHighDarkReducedContrast = Color(0xFF2D2834)
val SurfaceContainerHighestDarkReducedContrast = Color(0xFF38323F)

private val reducedContrastLightColorScheme = lightColorScheme(
    primary = PrimaryLightReducedContrast,
    onPrimary = OnPrimaryLightReducedContrast,
    primaryContainer = PrimaryContainerLightReducedContrast,
    onPrimaryContainer = OnPrimaryContainerLightReducedContrast,
    secondary = SecondaryLightReducedContrast,
    onSecondary = OnSecondaryLightReducedContrast,
    secondaryContainer = SecondaryContainerLightReducedContrast,
    onSecondaryContainer = OnSecondaryContainerLightReducedContrast,
    tertiary = TertiaryLightReducedContrast,
    onTertiary = OnTertiaryLightReducedContrast,
    tertiaryContainer = TertiaryContainerLightReducedContrast,
    onTertiaryContainer = OnTertiaryContainerLightReducedContrast,
    error = ErrorLightReducedContrast,
    onError = OnErrorLightReducedContrast,
    errorContainer = ErrorContainerLightReducedContrast,
    onErrorContainer = OnErrorContainerLightReducedContrast,
    background = BackgroundLightReducedContrast,
    onBackground = OnBackgroundLightReducedContrast,
    surface = SurfaceLightReducedContrast,
    onSurface = OnSurfaceLightReducedContrast,
    surfaceVariant = SurfaceVariantLightReducedContrast,
    onSurfaceVariant = OnSurfaceVariantLightReducedContrast,
    outline = OutlineLightReducedContrast,
    outlineVariant = OutlineVariantLightReducedContrast,
    scrim = ScrimLightReducedContrast,
    inverseSurface = InverseSurfaceLightReducedContrast,
    inverseOnSurface = InverseOnSurfaceLightReducedContrast,
    inversePrimary = InversePrimaryLightReducedContrast,
    surfaceDim = SurfaceDimLightReducedContrast,
    surfaceBright = SurfaceBrightLightReducedContrast,
    surfaceContainerLowest = SurfaceContainerLowestLightReducedContrast,
    surfaceContainerLow = SurfaceContainerLowLightReducedContrast,
    surfaceContainer = SurfaceContainerLightReducedContrast,
    surfaceContainerHigh = SurfaceContainerHighLightReducedContrast,
    surfaceContainerHighest = SurfaceContainerHighestLightReducedContrast,
)

private val reducedContrastDarkColorScheme = darkColorScheme(
    primary = PrimaryDarkReducedContrast,
    onPrimary = OnPrimaryDarkReducedContrast,
    primaryContainer = PrimaryContainerDarkReducedContrast,
    onPrimaryContainer = OnPrimaryContainerDarkReducedContrast,
    secondary = SecondaryDarkReducedContrast,
    onSecondary = OnSecondaryDarkReducedContrast,
    secondaryContainer = SecondaryContainerDarkReducedContrast,
    onSecondaryContainer = OnSecondaryContainerDarkReducedContrast,
    tertiary = TertiaryDarkReducedContrast,
    onTertiary = OnTertiaryDarkReducedContrast,
    tertiaryContainer = TertiaryContainerDarkReducedContrast,
    onTertiaryContainer = OnTertiaryContainerDarkReducedContrast,
    error = ErrorDarkReducedContrast,
    onError = OnErrorDarkReducedContrast,
    errorContainer = ErrorContainerDarkReducedContrast,
    onErrorContainer = OnErrorContainerDarkReducedContrast,
    background = BackgroundDarkReducedContrast,
    onBackground = OnBackgroundDarkReducedContrast,
    surface = SurfaceDarkReducedContrast,
    onSurface = OnSurfaceDarkReducedContrast,
    surfaceVariant = SurfaceVariantDarkReducedContrast,
    onSurfaceVariant = OnSurfaceVariantDarkReducedContrast,
    outline = OutlineDarkReducedContrast,
    outlineVariant = OutlineVariantDarkReducedContrast,
    scrim = ScrimDarkReducedContrast,
    inverseSurface = InverseSurfaceDarkReducedContrast,
    inverseOnSurface = InverseOnSurfaceDarkReducedContrast,
    inversePrimary = InversePrimaryDarkReducedContrast,
    surfaceDim = SurfaceDimDarkReducedContrast,
    surfaceBright = SurfaceBrightDarkReducedContrast,
    surfaceContainerLowest = SurfaceContainerLowestDarkReducedContrast,
    surfaceContainerLow = SurfaceContainerLowDarkReducedContrast,
    surfaceContainer = SurfaceContainerDarkReducedContrast,
    surfaceContainerHigh = SurfaceContainerHighDarkReducedContrast,
    surfaceContainerHighest = SurfaceContainerHighestDarkReducedContrast,
)

@Composable
fun TsukaremiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> reducedContrastDarkColorScheme
        else -> reducedContrastLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
    )
}
