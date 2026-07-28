package presentation.screens.terms_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dunihuliapps.myglidingassistant.R
import presentation.composables.rememberLocaleTextDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(
    viewModel: TermsViewModel = hiltViewModel(),
    onAccepted: () -> Unit = {},
    readOnly: Boolean = false,
    onBack: () -> Unit = {},
) {
    val textDirection = rememberLocaleTextDirection()

    if (readOnly) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                            Text(stringResource(R.string.terms_title))
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
            CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                Text(
                    text = stringResource(R.string.terms_body, stringResource(R.string.app_name)),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
        return
    }

    val isChecked by viewModel.isChecked.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                Text(
                    text = stringResource(R.string.terms_title),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                Text(
                    text = stringResource(R.string.terms_body, stringResource(R.string.app_name)),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { viewModel.onCheckedChange(it) }
                )
                CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                    Text(
                        text = stringResource(R.string.terms_checkbox_label),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.acceptTerms()
                    onAccepted()
                },
                enabled = isChecked,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Text(stringResource(R.string.terms_accept_button))
            }
        }
    }
}
