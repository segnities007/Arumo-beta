package com.segnities007.storage

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.segnities007.model.Storage
import com.segnities007.model.mvi.BaseViewModel
import com.segnities007.model.mvi.State
import com.segnities007.model.mvi.ViewEffect
import com.segnities007.model.mvi.ViewIntent
import com.segnities007.model.mvi.ViewState
import com.segnities007.model.route.Route
import com.segnities007.repository.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class StorageState(
    val label: String = "",
    val uri: Uri = Uri.EMPTY,
    val storages: List<Storage> = emptyList(),
    val state: State = State.Idle,
) : ViewState

sealed interface StorageIntent : ViewIntent {
    data object GetAllStorages : StorageIntent

    data class CreateStorage(
        val name: String,
        val description: String,
        val iconUri: Uri?,
    ) : StorageIntent

    data class DeleteStorage(
        val storage: Storage,
    ) : StorageIntent
}

sealed interface StorageEffect : ViewEffect {
    data class ShowToast(
        val message: String,
    ) : StorageEffect

    data class Navigate(
        val route: Route,
    ) : StorageEffect
}

class StorageViewModel :
    BaseViewModel<StorageState, StorageIntent, StorageEffect>(
        initialViewState = StorageState(),
    ),
    KoinComponent {
    private val storageRepository: StorageRepository by inject()

    public override fun handleIntent(intent: StorageIntent) {
        _state.value = state.value.copy(state = State.Loading)

        when (intent) {
            StorageIntent.GetAllStorages -> getAllStorages()
            is StorageIntent.DeleteStorage -> deleteStorage(intent)
            is StorageIntent.CreateStorage -> createStorage(intent)
        }
    }

    init {
        getAllStorages()
    }

    private fun getAllStorages() {
        viewModelScope.launch(Dispatchers.IO) {
            val storages = storageRepository.getStorages()
            _state.value = state.value.copy(storages = storages, state = State.Success)
            return@launch
        }
        _state.value = state.value.copy(state = State.Error("failed to get all storages"))
        _effect.tryEmit(StorageEffect.ShowToast("ストレージの取得に失敗しました"))
    }

    private fun deleteStorage(intent: StorageIntent.DeleteStorage) {
        viewModelScope.launch(Dispatchers.IO) {
            storageRepository.deleteStorage(intent.storage)
            _state.value = state.value.copy(state = State.Success)
            return@launch
        }
        _state.value = state.value.copy(state = State.Error("failed to delete storage"))
        _effect.tryEmit(StorageEffect.ShowToast("ストレージの削除に失敗しました"))
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun createStorage(intent: StorageIntent.CreateStorage) {
        viewModelScope.launch(Dispatchers.IO) {
            val newStorage = Storage(
                id = Uuid.random().toString(),
                name = intent.name,
                description = intent.description,
                iconUri = intent.iconUri,
            )
            storageRepository.createStorage(newStorage)
            _state.value = state.value.copy(state = State.Success)
            getAllStorages()
            return@launch
        }
        _state.value = state.value.copy(state = State.Error("failed to upsert storage"))
        _effect.tryEmit(StorageEffect.ShowToast("ストレージの更新に失敗しました"))
    }
}
