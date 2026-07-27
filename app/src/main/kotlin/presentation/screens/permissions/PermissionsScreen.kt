package presentation.screens.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dunihuliapps.myglidingassistant.R
import presentation.composables.rememberLocaleTextDirection

@Composable
fun PermissionsScreen(
    viewModel: PermissionsViewModel = hiltViewModel(),
    onNavigateToMain: () -> Unit,
    onFinish: () -> Unit,
) {
    val context = LocalContext.current
    val currentStep by viewModel.currentStep.collectAsState()
    val navigateToMain by viewModel.navigateToMain.collectAsState()
    val shouldFinish by viewModel.shouldFinish.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onStepResult(isGranted)
    }

    LaunchedEffect(Unit) {
        fun isGranted(permission: String) =
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

        viewModel.initPermissionChain(
            hasLocation = isGranted(Manifest.permission.ACCESS_FINE_LOCATION),
            hasNotifications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                isGranted(Manifest.permission.POST_NOTIFICATIONS) else true,
            hasStorage = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) else true
        )
    }

    LaunchedEffect(navigateToMain) {
        if (navigateToMain) onNavigateToMain()
    }

    LaunchedEffect(shouldFinish) {
        if (shouldFinish) onFinish()
    }

    currentStep?.let { step ->
        val (titleRes, messageRes) = when (step.type) {
            PermissionsViewModel.PermissionType.LOCATION ->
                R.string.permission_location_title to R.string.permission_location_message
            PermissionsViewModel.PermissionType.NOTIFICATIONS ->
                R.string.permission_notifications_title to R.string.permission_notifications_message
            PermissionsViewModel.PermissionType.STORAGE ->
                R.string.permission_storage_title to R.string.permission_storage_message
        }

        val textDirection = rememberLocaleTextDirection()

        AlertDialog(
            onDismissRequest = {},
            title = {
                CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                    Text(
                        stringResource(titleRes),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            text = {
                CompositionLocalProvider(LocalLayoutDirection provides textDirection) {
                    Text(
                        stringResource(messageRes),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { permissionLauncher.launch(step.permission) }) {
                    Text(stringResource(R.string.permission_allow))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onStepResult(false) }) {
                    Text(stringResource(R.string.permission_refuse))
                }
            }
        )
    }
}
