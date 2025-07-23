package com.segnities007.model.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable object Home : Route

    @Serializable object Search : Route

    @Serializable object Dashboard : Route

    @Serializable object Setting : Route

    @Serializable object Login : Route

    @Serializable object Storage : Route
}
