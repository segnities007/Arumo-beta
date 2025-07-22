package com.segnities007.login

import androidx.lifecycle.viewModelScope
import com.segnities007.model.User
import com.segnities007.model.mvi.BaseMvi
import com.segnities007.model.mvi.BaseViewModel
import com.segnities007.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.ByteString.Companion.encodeUtf8
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class LoginViewModel :
    BaseViewModel<
        LoginState,
        LoginMvi.Intent,
        BaseMvi.Effect,
    >(initialViewState = LoginState()),
    KoinComponent {
    private val userRepository: UserRepository by inject()

    override fun handleIntent(intent: LoginMvi.Intent) {


        when (intent) {
            is LoginMvi.Intent.GoToNextPage -> goToNextPage(intent)
            is LoginMvi.Intent.SignIn -> signIn(intent)
            is LoginMvi.Intent.SignUp -> signUp(intent)
        }
    }

    private fun goToNextPage(intent: LoginMvi.Intent.GoToNextPage) {
        val currentState = state.value
        if (currentState !is BaseMvi.State.Success) return

        _state.value = BaseMvi.State.Success(currentState.data.copy(page = intent.nextPage))
    }

    private fun signIn(intent: LoginMvi.Intent.SignIn) {
        val currentState = state.value
        if (currentState !is BaseMvi.State.Success) return
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun signUp(intent: LoginMvi.Intent.SignUp) {
        val currentState = state.value
        if (currentState !is BaseMvi.State.Success) return

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
        }
    }
}
