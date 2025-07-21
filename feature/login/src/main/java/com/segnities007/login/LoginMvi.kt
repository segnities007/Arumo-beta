package com.segnities007.login

import com.segnities007.model.mvi.BaseMvi

sealed interface LoginMvi: BaseMvi {
    sealed interface Intent: BaseMvi.Intent {

    }
    sealed interface Effect: BaseMvi.Effect {

    }
}