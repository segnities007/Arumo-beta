package com.segnities007.login

import com.segnities007.model.mvi.BaseMvi
import com.segnities007.model.mvi.BaseViewModel

data class LoginState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
)

class LoginViewModel:
    BaseViewModel<
            LoginState,
            LoginMvi.Intent,
            BaseMvi.Effect,
            >(initialViewState = LoginState()) {
    override fun handleIntent(intent: LoginMvi.Intent) {
        when(intent){
            else -> {}
        }
    }
}