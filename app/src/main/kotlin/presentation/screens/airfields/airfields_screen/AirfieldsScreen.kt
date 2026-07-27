package presentation.screens.airfields.airfields_screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dunihuliapps.myglidingassistant.data.model.Airfield
import presentation.composables.AirfieldListItem
import presentation.composables.EmptyAirfieldsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirfieldsScreen(
    viewModel: AirfieldsViewModel,
    onAddClick: (airfield: Airfield?) -> Unit,
    onBack: () -> Unit,
) {
    val airfields by viewModel.airfields.collectAsState()

    var selectedAirfieldForDelete by remember { mutableStateOf<Airfield?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog && selectedAirfieldForDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                selectedAirfieldForDelete = null
            },
            title = { Text("Delete Airfield") },
            text = { Text("Are you sure you want to delete ${selectedAirfieldForDelete?.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    selectedAirfieldForDelete?.let {
                        viewModel.deleteAirfield(it)
                    }
                    showDeleteDialog = false
                    selectedAirfieldForDelete = null
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    selectedAirfieldForDelete = null
                }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Airfields") },
                navigationIcon = {
                    if (selectedAirfieldForDelete == null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (selectedAirfieldForDelete != null) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                        IconButton(onClick = { selectedAirfieldForDelete = null }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                        }
                    } else {
                        IconButton(onClick = {
                            onAddClick(null)
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (airfields.isEmpty()) {
            Box(modifier = Modifier.padding(padding)) {
                EmptyAirfieldsContent {
                    onAddClick(null)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(airfields) { airfield ->
                    AirfieldListItem(
                        airfield,
                        onEditClick = {
                            if (selectedAirfieldForDelete == null) onAddClick(airfield)
                            else selectedAirfieldForDelete = null
                        },
                        onLongClick = {
                            selectedAirfieldForDelete = it
                        })
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}
