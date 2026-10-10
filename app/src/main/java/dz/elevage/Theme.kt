package dz.elevage

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// 2026 baseline: Material3 dynamic color, dark mode, seed fallback (pasture green).
enum class ThemeMode { SYSTEM, LIGHT, DARK }

private val SeedLight = lightColorScheme(
    primary = Color(0xFF1B5E20),
    secondary = Color(0xFF558B2F),
)
private val SeedDark = darkColorScheme(
    primary = Color(0xFFA5D6A7),
    secondary = Color(0xFFC5E1A5),
)

@Composable
fun ElevageTheme(mode: ThemeMode, content: @Composable () -> Unit) {
    val dark = when (mode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val scheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (dark) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        dark -> SeedDark
        else -> SeedLight
    }
    MaterialTheme(colorScheme = scheme, content = content)
}
