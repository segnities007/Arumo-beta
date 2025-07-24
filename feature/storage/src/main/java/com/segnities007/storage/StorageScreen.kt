package com.segnities007.storage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segnities007.model.Storage
import com.segnities007.model.route.Route
import com.segnities007.storage.component.BottomSheet
import com.segnities007.storage.component.RowCards
import com.segnities007.storage.component.Title
import com.segnities007.ui.theme.ArumobetaTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun StorageScreen(
    navigate: (Route) -> Unit,
    snackBar: (String) -> Unit,
) {
    val storageViewModel: StorageViewModel = koinViewModel()
    val state by storageViewModel.state.collectAsState()
    val effect by storageViewModel.effect.collectAsState(initial = null)

    LaunchedEffect(effect) {
        when (effect) {
            is StorageEffect.Navigate -> {
                navigate((effect as StorageEffect.Navigate).route)
            }
            is StorageEffect.ShowToast -> snackBar((effect as StorageEffect.ShowToast).message)
            null -> { /*Nothing*/ }
        }
    }

    StorageUi(
        storages = state.storages,
        onIntent = { storageViewModel.handleIntent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StorageUi(
    storages: List<Storage>,
    onIntent: (StorageIntent) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Title(Modifier.align(Alignment.TopCenter))
        RowCards(
            modifier = Modifier.align(Alignment.Center),
            storages = storages,
        )
        FloatingActionButton(
            modifier =
                Modifier
                    .padding(bottom = 64.dp)
                    .size(86.dp)
                    .align(Alignment.BottomCenter),
            elevation =
                FloatingActionButtonDefaults.elevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 2.dp,
                ),
            onClick = { showBottomSheet = true },
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
            )
        }
    }

    BottomSheet(
        showBottomSheet = showBottomSheet,
        onIntent = onIntent,
        onUpdateShowBottomSheet = { showBottomSheet = it },
    )
}

@Composable
@Preview(showBackground = true, apiLevel = 35)
private fun StorageScreenPreview() {
    ArumobetaTheme {
        StorageUi(
            storages = listOf(),
            onIntent = {},
        )
    }
}
