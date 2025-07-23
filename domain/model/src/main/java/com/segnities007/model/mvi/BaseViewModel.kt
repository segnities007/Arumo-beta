package com.segnities007.model.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface State {
    data object Idle : State

    data object Loading : State

    data object Success : State

    data class Error(
        val message: String,
    ) : State
}

interface ViewState

interface ViewIntent

interface ViewEffect

abstract class BaseViewModel<
    State : ViewState,
    Intent : ViewIntent,
    Effect : ViewEffect,
>(
    initialViewState: State,
) : ViewModel() {
    protected val _state = MutableStateFlow(initialViewState)
    val state: StateFlow<State> = _state.asStateFlow()

    protected val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    protected abstract fun handleIntent(intent: Intent)

    protected fun handleEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
