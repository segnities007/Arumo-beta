package com.segnities007.home

import com.segnities007.model.Item
import com.segnities007.model.mvi.BaseViewModel
import com.segnities007.model.mvi.State
import com.segnities007.model.mvi.ViewEffect
import com.segnities007.model.mvi.ViewIntent
import com.segnities007.model.mvi.ViewState
import com.segnities007.model.route.Route
import com.segnities007.repository.ItemRepository
import org.koin.core.component.KoinComponent

data class HomeState(
    val items: List<Item> = emptyList(),
    val state: State = State.Idle,
): ViewState

sealed interface HomeIntent: ViewIntent {
    data object GetRecentlyItems: HomeIntent
    data object GetFewItems: HomeIntent
}

sealed interface HomeEffect : ViewEffect {
    data class ShowToast(
        val message: String,
    ) : HomeEffect
    data class Navigate(
        val route: Route,
    ) : HomeEffect
}

class HomeViewModel(
    private val itemRepository: ItemRepository
): BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialViewState = HomeState()
), KoinComponent{
    override fun handleIntent(intent: HomeIntent) {
        when(intent){
            HomeIntent.GetFewItems -> getFewItems()
            HomeIntent.GetRecentlyItems -> getRecentlyItems()

        }
    }

    private fun getFewItems() {
        //TODO
    }

    private fun getRecentlyItems() {
        //TODO
    }
}