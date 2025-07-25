package com.segnities007.login

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.segnities007.model.User
import com.segnities007.model.mvi.BaseViewModel
import com.segnities007.model.mvi.State
import com.segnities007.model.mvi.ViewEffect
import com.segnities007.model.mvi.ViewIntent
import com.segnities007.model.mvi.ViewState
import com.segnities007.model.route.Route
import com.segnities007.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.ByteString.Companion.encodeUtf8
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class LoginState(
    val page: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val state: State = State.Idle,
) : ViewState

sealed interface LoginIntent : ViewIntent {
    data class SignUp(
        val name: String,
        val email: String,
        val password: String,
        val uri: Uri?,
    ) : LoginIntent

    data class SignIn(
        val email: String,
        val password: String,
    ) : LoginIntent

    data class GoToNextPage(
        val nextPage: Int,
    ) : LoginIntent
}

sealed interface LoginEffect : ViewEffect {
    data class SnackBar(
        val message: String,
    ) : LoginEffect

    data class Navigation(
        val route: Route,
    ) : LoginEffect
}

class LoginViewModel :
    BaseViewModel<LoginState, LoginIntent, LoginEffect>(initialViewState = LoginState()),
    KoinComponent {
    private val userRepository: UserRepository by inject()

    public override fun handleIntent(intent: LoginIntent) {
        if (state.value.state !is State.Idle) return
        _state.value = state.value.copy(state = State.Loading)

        when (intent) {
            is LoginIntent.SignUp -> signUp(intent)
            is LoginIntent.SignIn -> signIn(intent)
            is LoginIntent.GoToNextPage -> goToNextPage(intent)
        }
    }

    private fun goToNextPage(intent: LoginIntent.GoToNextPage) {
        _state.value =
            state.value.copy(
                page = intent.nextPage,
                state = State.Success,
            )
        _state.value = state.value.copy(state = State.Idle)
    }

    private fun signIn(intent: LoginIntent.SignIn) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUsers().map {
                if (it.email == intent.email &&
                    it.password ==
                    intent.password
                        .encodeUtf8()
                        .sha256()
                        .hex()
                ) {
                    _effect.emit(LoginEffect.Navigation(Route.Storage))
                }
            }
            _state.value = state.value.copy(state = State.Error("failed to sign in"))
            _effect.emit(LoginEffect.SnackBar("ログインに失敗しました"))
            _state.value = state.value.copy(state = State.Idle)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun signUp(intent: LoginIntent.SignUp) {
        val user =
            User(
                id = Uuid.random().toString(),
                name = intent.name,
                email = intent.email,
                password =
                    intent.password
                        .encodeUtf8()
                        .sha256()
                        .hex(),
            )

        viewModelScope.launch(Dispatchers.IO) {
            userRepository.upsertUser(user)
            _state.value = state.value.copy(state = State.Success)
            _effect.emit(LoginEffect.Navigation(Route.Storage))
            _state.value = state.value.copy(state = State.Idle)
        }
    }
}
