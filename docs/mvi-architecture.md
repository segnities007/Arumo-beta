---
layout: default
title: MVI Architecture
---

# MVI Architecture

## 概要

Arumo-betaでは、MVI（Model-View-Intent）アーキテクチャパターンを採用しています。このパターンにより、UIの状態管理とユーザーインタラクションを明確に分離し、テスト可能で保守性の高いコードを実現しています。

## MVIの基本概念

MVIは以下の3つの主要なコンポーネントで構成されています：

- **Model**: アプリケーションの状態を表す
- **View**: UIの表示とユーザーインタラクションを処理
- **Intent**: ユーザーアクションを表す

## BaseViewModel

MVIアーキテクチャの基盤となるベースクラスです。

```kotlin
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
```

### インターフェース

```kotlin
interface ViewState
interface ViewIntent
interface ViewEffect
```

### 状態管理

```kotlin
sealed interface State {
    data object Idle : State
    data object Loading : State
    data object Success : State
    data class Error(val message: String) : State
}
```

## 実装例

### HomeViewModel

ホーム画面のViewModel実装例です。

```kotlin
data class HomeState(
    val items: List<Item> = emptyList(),
    val state: State = State.Idle,
): ViewState

sealed interface HomeIntent: ViewIntent {
    data class GetRecentlyItems(val id: Int): HomeIntent
}

sealed interface HomeEffect : ViewEffect {
    data class ShowToast(val message: String) : HomeEffect
    data class Navigate(val route: Route) : HomeEffect
}

class HomeViewModel(
    private val itemRepository: ItemRepository
): BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialViewState = HomeState()
), KoinComponent {
    
    override fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.GetRecentlyItems -> getRecentlyItems(intent)
        }
    }

    private fun getRecentlyItems(intent: HomeIntent.GetRecentlyItems) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(state = State.Loading)
            try {
                val items = itemRepository.getRecentlyItemsFromId(intent.id)
                _state.value = state.value.copy(
                    items = items,
                    state = State.Success
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    state = State.Error(e.message ?: "Unknown error")
                )
            }
        }
    }
}
```

### LoginViewModel

ログイン画面のViewModel実装例です。

```kotlin
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

    data class GoToNextPage(val nextPage: Int) : LoginIntent
}

sealed interface LoginEffect : ViewEffect {
    data class SnackBar(val message: String) : LoginEffect
    data class Navigation(val route: Route) : LoginEffect
}

class LoginViewModel :
    BaseViewModel<LoginState, LoginIntent, LoginEffect>(initialViewState = LoginState()),
    KoinComponent {
    
    private val userRepository: UserRepository by inject()

    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.SignUp -> signUp(intent)
            is LoginIntent.SignIn -> signIn(intent)
            is LoginIntent.GoToNextPage -> goToNextPage(intent)
        }
    }

    private fun signUp(intent: LoginIntent.SignUp) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(state = State.Loading)
            try {
                val user = User(
                    id = Uuid.randomUUID().toString(),
                    name = intent.name,
                    email = intent.email,
                    password = intent.password.hashCode().toString(),
                    iconUri = intent.uri
                )
                userRepository.upsertUser(user)
                _state.value = state.value.copy(state = State.Success)
                handleEffect(LoginEffect.Navigation(Route.Home))
            } catch (e: Exception) {
                _state.value = state.value.copy(state = State.Error(e.message ?: "Sign up failed"))
                handleEffect(LoginEffect.SnackBar("サインアップに失敗しました"))
            }
        }
    }

    private fun signIn(intent: LoginIntent.SignIn) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(state = State.Loading)
            try {
                val users = userRepository.getUsers()
                val user = users.find { it.email == intent.email && it.password == intent.password.hashCode().toString() }
                if (user != null) {
                    _state.value = state.value.copy(state = State.Success)
                    handleEffect(LoginEffect.Navigation(Route.Home))
                } else {
                    _state.value = state.value.copy(state = State.Error("Invalid credentials"))
                    handleEffect(LoginEffect.SnackBar("メールアドレスまたはパスワードが正しくありません"))
                }
            } catch (e: Exception) {
                _state.value = state.value.copy(state = State.Error(e.message ?: "Sign in failed"))
                handleEffect(LoginEffect.SnackBar("サインインに失敗しました"))
            }
        }
    }

    private fun goToNextPage(intent: LoginIntent.GoToNextPage) {
        _state.value = state.value.copy(page = intent.nextPage)
    }
}
```

## UIでの使用

### Composeでの実装

```kotlin
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.GetRecentlyItems(0))
    }
    
    LaunchedEffect(effect) {
        effect?.let { effect ->
            when (effect) {
                is HomeEffect.ShowToast -> {
                    // Toast表示
                }
                is HomeEffect.Navigate -> {
                    // 画面遷移
                }
            }
        }
    }
    
    when (state.state) {
        is State.Idle -> {
            // 初期表示
        }
        is State.Loading -> {
            CircularProgressIndicator()
        }
        is State.Success -> {
            LazyColumn {
                items(state.items) { item ->
                    ItemCard(item = item)
                }
            }
        }
        is State.Error -> {
            Text(text = state.state.message)
        }
    }
}
```

## ベストプラクティス

### 1. 状態の設計

```kotlin
// 良い例：明確で具体的な状態
data class ItemListState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: ConsumableCategory? = null,
) : ViewState

// 悪い例：曖昧な状態
data class ItemListState(
    val data: Any? = null,
    val loading: Boolean = false,
) : ViewState
```

### 2. Intentの設計

```kotlin
// 良い例：具体的で明確なIntent
sealed interface ItemListIntent : ViewIntent {
    data class LoadItems(val category: ConsumableCategory?) : ItemListIntent
    data class SelectItem(val item: Item) : ItemListIntent
    data class DeleteItem(val item: Item) : ItemListIntent
    data object Refresh : ItemListIntent
}

// 悪い例：曖昧なIntent
sealed interface ItemListIntent : ViewIntent {
    data class Action(val type: String, val data: Any?) : ItemListIntent
}
```

### 3. Effectの設計

```kotlin
// 良い例：具体的な副作用
sealed interface ItemListEffect : ViewEffect {
    data class ShowSnackBar(val message: String) : ItemListEffect
    data class NavigateToDetail(val itemId: Int) : ItemListEffect
    data class ShowDeleteDialog(val item: Item) : ItemListEffect
}

// 悪い例：曖昧な副作用
sealed interface ItemListEffect : ViewEffect {
    data class ShowMessage(val message: String, val type: String) : ItemListEffect
}
```

## テスト

### ViewModelのテスト

```kotlin
@Test
fun `when LoadItems intent is sent, items are loaded`() = runTest {
    // Given
    val items = listOf(Item(name = "Test Item"))
    coEvery { itemRepository.getItems() } returns flowOf(items)
    
    // When
    viewModel.handleIntent(ItemListIntent.LoadItems(null))
    
    // Then
    val state = viewModel.state.value
    assertEquals(items, state.items)
    assertEquals(State.Success, state.state)
}
```

### Effectのテスト

```kotlin
@Test
fun `when item is selected, navigate effect is emitted`() = runTest {
    // Given
    val item = Item(id = 1, name = "Test Item")
    
    // When
    viewModel.handleIntent(ItemListIntent.SelectItem(item))
    
    // Then
    val effect = viewModel.effect.first()
    assertTrue(effect is ItemListEffect.NavigateToDetail)
    assertEquals(1, (effect as ItemListEffect.NavigateToDetail).itemId)
}
```

## 注意事項

1. **単方向データフロー**: Intent → ViewModel → State → View の単方向フローを維持する
2. **不変性**: Stateは不変オブジェクトとして設計する
3. **副作用の分離**: Effectを使用して副作用を明確に分離する
4. **テスト可能性**: 各コンポーネントを独立してテストできるように設計する
5. **型安全性**: Kotlinの型システムを活用して型安全性を確保する 