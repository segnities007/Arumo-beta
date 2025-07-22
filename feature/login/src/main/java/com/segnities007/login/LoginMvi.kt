package com.segnities007.login

import com.segnities007.model.mvi.BaseMvi

private const val START_PAGE = 1

data class LoginState(
    val page: Int = START_PAGE,
    val name: String = "",
    val email: String = "",
    val password: String = "",
)

sealed interface LoginMvi : BaseMvi {
    sealed interface Intent : BaseMvi.Intent {
        data class SignIn(
            val email: String,
            val password: String,
        ) : Intent

        data class SignUp(
            val name: String,
            val email: String,
            val password: String,
        ) : Intent

        data class GoToNextPage(
            val nextPage: Int,
        ) : Intent
    }

    sealed interface Effect : BaseMvi.Effect {
        data class SnackBar(
            val message: String,
        ) : Effect

        data class Navigate(
            val route: String,
        ) : Effect
    }
}
