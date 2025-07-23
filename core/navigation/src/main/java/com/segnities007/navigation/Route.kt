package com.segnities007.navigation

sealed interface Route {
    object Home: Route
    object Search: Route
    object Dashboard: Route
    object Setting: Route
    object Login: Route
    object Storage: Route
}