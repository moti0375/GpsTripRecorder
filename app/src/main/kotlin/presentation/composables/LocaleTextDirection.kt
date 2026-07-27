package presentation.composables

import android.text.TextUtils
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.LayoutDirection

/**
 * The [LayoutDirection] the current locale's script reads in (e.g. Rtl for Hebrew), independent of
 * the app's own UI layout direction. The app itself stays LTR-mirrored (android:supportsRtl is not
 * set); this is only for scoping text alignment to the content's natural reading direction.
 */
@Composable
fun rememberLocaleTextDirection(): LayoutDirection {
    val locale = LocalConfiguration.current.locales[0]
    return if (TextUtils.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
}
