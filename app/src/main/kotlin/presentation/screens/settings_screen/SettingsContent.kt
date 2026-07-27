package presentation.screens.settings_screen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dunihuliapps.myglidingassistant.R
import presentation.composables.rememberLocaleTextDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(viewModel: SettingsViewModel, onNavigateUp: () -> Unit) {
    val state by viewModel.state.collectAsState()

    val textDirection = rememberLocaleTextDirection()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                        Text(stringResource(R.string.AppSettings))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
        ) {
            item {
                PreferenceCategory(title = stringResource(R.string.unites_category))
                PreferenceListItem(
                    title = stringResource(R.string.distance_units_pref_title),
                    currentValue = state.distanceUnits,
                    entries = stringArrayResource(R.array.distance_units_entries).toList(),
                    values = stringArrayResource(R.array.distance_units_values).toList(),
                    onValueSelected = { viewModel.updatePreference("distance_units", it) }
                )
                PreferenceListItem(
                    title = stringResource(R.string.speed_units_pref_title),
                    currentValue = state.speedUnits,
                    entries = stringArrayResource(R.array.speed_units_entries).toList(),
                    values = stringArrayResource(R.array.speed_units_values).toList(),
                    onValueSelected = { viewModel.updatePreference("speed_units", it) }
                )
                PreferenceListItem(
                    title = stringResource(R.string.AltitudePrefTitle),
                    currentValue = state.altitudeUnits,
                    entries = stringArrayResource(R.array.altitude_units_entries).toList(),
                    values = stringArrayResource(R.array.altitude_units_values).toList(),
                    onValueSelected = { viewModel.updatePreference("altitudeUnits", it) }
                )

                PreferenceCategory(title = stringResource(R.string.MapCategory))
                PreferenceListItem(
                    title = stringResource(R.string.LineColorTitle),
                    currentValue = state.lineColor,
                    entries = stringArrayResource(R.array.line_color_entries).toList(),
                    values = stringArrayResource(R.array.line_color_values).toList(),
                    onValueSelected = { viewModel.updatePreference("LineColor", it) }
                )
                PreferenceListItem(
                    title = stringResource(R.string.LineWidthTitle),
                    currentValue = state.lineWidth,
                    entries = stringArrayResource(R.array.line_width_entries).toList(),
                    values = stringArrayResource(R.array.line_width_values).toList(),
                    onValueSelected = { viewModel.updatePreference("lineWidth", it) }
                )
                PreferenceListItem(
                    title = stringResource(R.string.ZoomTitle),
                    currentValue = state.zoom,
                    entries = stringArrayResource(R.array.zoom_entries).toList(),
                    values = stringArrayResource(R.array.zoom_values).toList(),
                    onValueSelected = { viewModel.updatePreference("zoom", it) }
                )

                PreferenceCategory(title = stringResource(R.string.SavingOptionsCategory))
                PreferenceListItem(
                    title = stringResource(R.string.AutoSaveTitle),
                    currentValue = state.autoSave,
                    entries = stringArrayResource(R.array.auto_save_entries).toList(),
                    values = stringArrayResource(R.array.auto_save_values).toList(),
                    onValueSelected = { viewModel.updatePreference("AutoSavePrefKey", it) }
                )
            }
        }
    }
}

@Composable
private fun PreferenceCategory(title: String) {
    CompositionLocalProvider(LocalLayoutDirection provides rememberLocaleTextDirection()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 4.dp)
        )
    }
}

@Composable
private fun PreferenceListItem(
    title: String,
    currentValue: String,
    entries: List<String>,
    values: List<String>,
    onValueSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val textDirection = rememberLocaleTextDirection()

    val currentLabel = entries.getOrElse(values.indexOf(currentValue)) { currentValue }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = currentLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(start = 16.dp))

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                    Text(title)
                }
            },
            text = {
                Column {
                    entries.forEachIndexed { index, entry ->
                        val value = values.getOrElse(index) { entry }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onValueSelected(value)
                                    showDialog = false
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentValue == value,
                                onClick = {
                                    onValueSelected(value)
                                    showDialog = false
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                                Text(entry, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                        Text(stringResource(R.string.Cancel))
                    }
                }
            }
        )
    }
}
