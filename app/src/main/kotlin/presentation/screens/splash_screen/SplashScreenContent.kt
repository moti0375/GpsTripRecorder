package presentation.screens.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dunihuliapps.myglidingassistant.R
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 3500L

@Composable
fun SplashScreenContent(
    onNavigateToMain: () -> Unit,
) {
    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)
        onNavigateToMain()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.gliding_assistant_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}
