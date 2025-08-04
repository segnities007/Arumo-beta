package com.segnities007.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import com.segnities007.model.Storage
import com.segnities007.model.User
import com.segnities007.model.mvi.BaseViewModel
import com.segnities007.model.mvi.State
import com.segnities007.model.mvi.ViewEffect
import com.segnities007.model.mvi.ViewIntent
import com.segnities007.model.mvi.ViewState
import com.segnities007.model.route.Route
import com.segnities007.repository.StorageRepository
import com.segnities007.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.ExperimentalTime


data class NavigationState @OptIn(ExperimentalTime::class) constructor(
    val user: User = User(),
    val storage: Storage = Storage(),
    val topBar: @Composable () -> Unit = {},
    val bottomBar: @Composable () -> Unit = {},
    val fab: @Composable () -> Unit = {},
    val state: State = State.Idle,
): ViewState

sealed interface NavigationIntent: ViewIntent{
    data object Init: NavigationIntent
    data class SetTopBar(val topBar: @Composable () -> Unit): NavigationIntent
    data class SetBottomBar(val bottomBar: @Composable () -> Unit): NavigationIntent
    data class SetFab(val fab: @Composable () -> Unit): NavigationIntent
}

sealed interface NavigationEffect: ViewEffect{
    data class Navigate(val route: Route): NavigationEffect
    data class ShowToast(val message: String): NavigationEffect
}

class NavigationViewModel(
    private val userRepository: UserRepository,
    private val storageRepository: StorageRepository,
): BaseViewModel<NavigationState, NavigationIntent, NavigationEffect>(
    initialViewState = NavigationState()), KoinComponent {

    public override fun handleIntent(intent: NavigationIntent) {
        _state.value = state.value.copy(state = State.Loading)
        when(intent){
            NavigationIntent.Init -> init()
            is NavigationIntent.SetBottomBar -> setBottomBar(intent.bottomBar)
            is NavigationIntent.SetFab -> setFab(intent.fab)
            is NavigationIntent.SetTopBar -> setTopBar(intent.topBar)
        }
    }

    private fun setTopBar(topBar: @Composable () -> Unit){
        _state.value = state.value.copy(topBar = topBar)
    }

    private fun setBottomBar(bottomBar: @Composable () -> Unit){
        _state.value = state.value.copy(bottomBar = bottomBar)
    }

    private fun setFab(fab: @Composable () -> Unit){
        _state.value = state.value.copy(fab = fab)
    }

    private suspend fun getSavedUser(){
            val userId = userRepository.getSavedUserId()
            val user = userRepository.getUserById(userId)
            if(user != null){
                _state.value = state.value.copy(user = user, state = State.Success)
            }else{
                _state.value = state.value.copy(state = State.Error("User not found"))
            }
    }


    private suspend fun getSavedStorage(){
            val storageId = storageRepository.getSavedStorageId()
            val storage = storageRepository.getStorageById(storageId)
            if(storage != null){
                _state.value = state.value.copy(storage = storage, state = State.Success)
            }else{
                _state.value = state.value.copy(state = State.Error("Storage not found"))
            }
    }

    private fun init(){
        navigateUserAndStorageExisting()
    }

    private fun navigateUserAndStorageExisting(){
        _state.value = state.value.copy(state = State.Loading)
        viewModelScope.launch(Dispatchers.IO){
            getSavedUser()
            getSavedStorage()
            if(state.value.user.id.isNotEmpty() && state.value.storage.id.isNotEmpty()){
                _state.value = state.value.copy(state = State.Success)
                _effect.emit(NavigationEffect.Navigate(Route.Home))
            }else if(state.value.user.id.isNotEmpty()){
                _state.value = state.value.copy(state = State.Error("Storage not found"))
                _effect.emit(NavigationEffect.Navigate(Route.Storage))
            }else{
                _state.value = state.value.copy(state = State.Error("User or Storage not found"))
            }
        }
    }
}