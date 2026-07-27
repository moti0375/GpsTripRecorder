package presentation.screens.airfields.edit_airfield_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch

private val DEFAULT_MAP_CENTER = LatLng(31.5, 34.8) // Roughly the center of Israel
private const val DEFAULT_ZOOM = 7f
private const val SELECTED_ZOOM = 14f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAirfieldScreen(
    viewModel: EditAirfieldViewModel,
    onSaved: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val predictions by viewModel.predictions.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            state.latitude?.let { lat -> LatLng(lat, state.longitude ?: DEFAULT_MAP_CENTER.longitude) }
                ?: DEFAULT_MAP_CENTER,
            if (state.latitude != null) SELECTED_ZOOM else DEFAULT_ZOOM
        )
    }
    val markerState = rememberUpdatedMarkerState(
        position = state.latitude?.let { lat -> LatLng(lat, state.longitude ?: 0.0) } ?: DEFAULT_MAP_CENTER
    )

    LaunchedEffect(state.latitude, state.longitude) {
        val lat = state.latitude
        val lng = state.longitude
        if (lat != null && lng != null) {
            val position = LatLng(lat, lng)
            coroutineScope.launch {
                cameraPositionState.animate(
                    com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(position, SELECTED_ZOOM)
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (viewModel.isEditMode) "Edit Airfield" else "New Airfield") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = predictions.isNotEmpty(),
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryEditable)
                        .fillMaxWidth(),
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    singleLine = true,
                    label = { Text("Search for an airfield") },
                    placeholder = { Text("e.g. Megido airfield") }
                )
                ExposedDropdownMenu(
                    expanded = predictions.isNotEmpty(),
                    onDismissRequest = { }
                ) {
                    predictions.forEach { prediction ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(prediction.primaryText)
                                    if (prediction.secondaryText.isNotBlank()) {
                                        Text(
                                            text = prediction.secondaryText,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            },
                            onClick = { viewModel.onPredictionSelected(prediction) }
                        )
                    }
                }
            }

            Text(
                text = "Or tap the map to set the airfield location",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    viewModel.mapEventToState(
                        EditAirfieldEvent.OnLocationSelected(latLng.latitude, latLng.longitude)
                    )
                }
            ) {
                if (state.latitude != null) {
                    Marker(state = markerState)
                }
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = state.name ?: "",
                onValueChange = { viewModel.mapEventToState(EditAirfieldEvent.OnNameChange(it)) },
                singleLine = true,
                label = { Text("Name") }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.isHome,
                    onCheckedChange = { viewModel.mapEventToState(EditAirfieldEvent.OnHomeToggled(it)) }
                )
                Text("Set as home airfield")
            }

            Button(
                onClick = { viewModel.save(onSaved) },
                enabled = state.latitude != null && state.longitude != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(56.dp)
            ) {
                Text("Save Airfield")
            }
        }
    }
}
