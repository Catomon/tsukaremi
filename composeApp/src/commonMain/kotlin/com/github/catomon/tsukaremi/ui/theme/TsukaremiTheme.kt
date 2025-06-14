package com.github.catomon.tsukaremi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import tsukaremi.composeapp.generated.resources.BadComic_Regular
import tsukaremi.composeapp.generated.resources.IndieFlower_Regular
import tsukaremi.composeapp.generated.resources.Res


val Seed = Color(0xFF724BBC)

val PrimaryLight = Color(0xFF5931A2)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFF724BBC)
val OnPrimaryContainerLight = Color(0xFFE7D8FF)
val SecondaryLight = Color(0xFF665784)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFDCC9FD)
val OnSecondaryContainerLight = Color(0xFF61527F)
val TertiaryLight = Color(0xFF674300)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFF865900)
val OnTertiaryContainerLight = Color(0xFFFFD8A5)
val ErrorLight = Color(0xFFBA1A1A)
val OnErrorLight = Color(0xFFFFFFFF)
val ErrorContainerLight = Color(0xFFFFDAD6)
val OnErrorContainerLight = Color(0xFF93000A)
val BackgroundLight = Color(0xFFFDF7FF)
val OnBackgroundLight = Color(0xFF21005D)
val SurfaceLight = Color(0xFFFDF7FF)
val OnSurfaceLight = Color(0xFF21005D)
val SurfaceVariantLight = Color(0xFFEEDBFF)
val OnSurfaceVariantLight = Color(0xFF6400B9)
val OutlineLight = Color(0xFF9B44FF)
val OutlineVariantLight = Color(0xFFD9B9FF)
val ScrimLight = Color(0xFF000000)
val InverseSurfaceLight = Color(0xFF390093)
val InverseOnSurfaceLight = Color(0xFFF5EEFF)
val InversePrimaryLight = Color(0xFFD3BBFF)
val SurfaceDimLight = Color(0xFFE0D4FF)
val SurfaceBrightLight = Color(0xFFFDF7FF)
val SurfaceContainerLowestLight = Color(0xFFFFFFFF)
val SurfaceContainerLowLight = Color(0xFFF8F1FF)
val SurfaceContainerLight = Color(0xFFF3EAFF)
val SurfaceContainerHighLight = Color(0xFFEEE4FF)
val SurfaceContainerHighestLight = Color(0xFFE8DDFF)

val PrimaryDark = Color(0xFFD3BBFF)
val OnPrimaryDark = Color(0xFF3E0A87)
val PrimaryContainerDark = Color(0xFF724BBC)
val OnPrimaryContainerDark = Color(0xFFE7D8FF)
val SecondaryDark = Color(0xFFD1BEF1)
val OnSecondaryDark = Color(0xFF372952)
val SecondaryContainerDark = Color(0xFF50426D)
val OnSecondaryContainerDark = Color(0xFFC2B0E3)
val TertiaryDark = Color(0xFFF8BC62)
val OnTertiaryDark = Color(0xFF442B00)
val TertiaryContainerDark = Color(0xFF865900)
val OnTertiaryContainerDark = Color(0xFFFFD8A5)
val ErrorDark = Color(0xFFFFB4AB)
val OnErrorDark = Color(0xFF690005)
val ErrorContainerDark = Color(0xFF93000A)
val OnErrorContainerDark = Color(0xFFFFDAD6)
val BackgroundDark = Color(0xFF180048)
val OnBackgroundDark = Color(0xFFE8DDFF)
val SurfaceDark = Color(0xFF180048)
val OnSurfaceDark = Color(0xFFE8DDFF)
val SurfaceVariantDark = Color(0xFF6400B9)
val OnSurfaceVariantDark = Color(0xFFD9B9FF)
val OutlineDark = Color(0xFFB070FF)
val OutlineVariantDark = Color(0xFF6400B9)
val ScrimDark = Color(0xFF000000)
val InverseSurfaceDark = Color(0xFFE8DDFF)
val InverseOnSurfaceDark = Color(0xFF390093)
val InversePrimaryDark = Color(0xFF6E47B8)
val SurfaceDimDark = Color(0xFF180048)
val SurfaceBrightDark = Color(0xFF4300AA)
val SurfaceContainerLowestDark = Color(0xFF12003B)
val SurfaceContainerLowDark = Color(0xFF21005D)
val SurfaceContainerDark = Color(0xFF260068)
val SurfaceContainerHighDark = Color(0xFF320083)
val SurfaceContainerHighestDark = Color(0xFF3E009F)

private val lightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    tertiary = TertiaryLight,
    onTertiary = OnTertiaryLight,
    tertiaryContainer = TertiaryContainerLight,
    onTertiaryContainer = OnTertiaryContainerLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    scrim = ScrimLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    inversePrimary = InversePrimaryLight,
    surfaceDim = SurfaceDimLight,
    surfaceBright = SurfaceBrightLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
)

//  open val behindBackground: Color,
//    open val text: Color,
//    open val textSecondary: Color,
//    open val background: Color,
//    open val forDisabledMostlyIdk: Color,
//    open val listItem: Color,
//    open val buttonIconSmall: Color = forDisabledMostlyIdk,
//    open val buttonIcon: Color = forDisabledMostlyIdk,
//    open val onBars: Color = forDisabledMostlyIdk,
//    open val backgroundTransparent: Color = background.copy(0.9f),
//    open val buttonIconTransparent: Color = buttonIcon.copy(0.5f),
//    open val thumbnailProgressIndicator: Color = background.copy(0.5f),
//    open val thinBorder: Color = background,
//    open val buttonIconSmallSelected: Color = Color.White,
    //    object Violet : KagaminColors(
//        name = "gami-kasa",
//        behindBackground = Color(0xffc09dff),
//        listItem = Color(0xcd9775d5), // Color(0xcb6c4141)
//        forDisabledMostlyIdk = Color(0xcb753cc9),
//        text = Color(0xFFFFFFFF),
//        textSecondary = Color(0xFFDECCFF),
//        background = Color(0xff6232a9),
//    ) {
//        override val buttonIconSmall: Color = Color(0xff916dd6)
//        override val buttonIcon: Color = Color(0xff9775d5)
//        override val thinBorder: Color = Color(0xff5522a2)
//    }

private val darkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = PrimaryContainerDark, //PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    background = Color(0xff6232a9),// Color(0xff000000), //Color(0xff6232a9),
    onBackground = OnBackgroundDark,
    surface = Color(0xff6232a9),
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    scrim = ScrimDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    inversePrimary = InversePrimaryDark,
    surfaceDim = Color(0xff6232a9),
    surfaceBright = SurfaceBrightDark,
    surfaceContainerLowest = Color(0xff6232a9),
    surfaceContainerLow = Color(0xff9775d5),
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
)

val fontFamily @Composable get() = FontFamily(Font(Res.font.BadComic_Regular))

val typography
    @Composable get() = run {
        val t = MaterialTheme.typography
        val fontFamily = fontFamily
        t.copy(
            displayLarge = t.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = t.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = t.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = t.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = t.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = t.headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = t.titleLarge.copy(fontFamily = fontFamily),
            titleMedium = t.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = t.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = t.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = t.bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = t.bodySmall.copy(fontFamily = fontFamily),
            labelLarge = t.labelLarge.copy(fontFamily = fontFamily),
            labelMedium = t.labelMedium.copy(fontFamily = fontFamily),
            labelSmall = t.labelSmall.copy(fontFamily = fontFamily)
        )
    }


@Composable
fun TsukaremiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content,
        typography = typography
    )
}
