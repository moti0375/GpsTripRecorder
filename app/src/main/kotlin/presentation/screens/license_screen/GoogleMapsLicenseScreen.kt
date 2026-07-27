package presentation.screens.license_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dunihuliapps.myglidingassistant.R
import presentation.composables.rememberLocaleTextDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsLicenseScreen(
    onBack: () -> Unit,
    onViewOpenSourceLicenses: () -> Unit,
) {
    val textDirection = rememberLocaleTextDirection()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                        Text(stringResource(R.string.google_maps_license_title))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                Text(
                    text = stringResource(R.string.google_maps_license_body),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                )
            }
            Button(
                onClick = onViewOpenSourceLicenses,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .navigationBarsPadding()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                    Text(stringResource(R.string.view_open_source_licenses_button))
                }
            }
        }
    }
}
