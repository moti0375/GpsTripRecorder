package presentation.composables

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dunihuliapps.myglidingassistant.data.model.Airfield
import java.util.Locale

@Composable
fun AirfieldListItem(
    airfield: Airfield,
    onEditClick: (Airfield) -> Unit,
    onLongClick: (Airfield) -> Unit
) {
    ListItem(
        modifier = Modifier.combinedClickable(
            onClick = { onEditClick(airfield) },
            onLongClick = { onLongClick(airfield) }
        ),
        leadingContent = {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(
                    imageVector = if (airfield.isHome) Icons.Default.Home else Icons.Default.Flag,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp)
                )
            }
        },
        headlineContent = {
            Text(
                text = airfield.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            Text(
                text = String.format(Locale.US, "%.5f, %.5f", airfield.latitude, airfield.longitude),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingContent = {
            IconButton(onClick = { onEditClick(airfield) }) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Edit Airfield",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    )
}
