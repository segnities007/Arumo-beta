---
layout: default
title: Navigation
---

# Navigation

## 概要

Arumo-betaでは、Jetpack Compose Navigationを使用して画面遷移を管理しています。このドキュメントでは、ナビゲーションシステムの設計と実装について説明します。

## Route定義

アプリケーション内のすべての画面は`Route`インターフェースで定義されています。

```kotlin
@Serializable
sealed interface Route {
    @Serializable object Home : Route
    @Serializable object Search : Route
    @Serializable object Dashboard : Route
    @Serializable object Setting : Route
    @Serializable object Login : Route
    @Serializable object Storage : Route
}
```

### 画面一覧

| 画面 | Route | 説明 |
|------|-------|------|
| ホーム | `Route.Home` | メイン画面、アイテム一覧表示 |
| 検索 | `Route.Search` | アイテム検索画面 |
| ダッシュボード | `Route.Dashboard` | 統計・分析画面 |
| 設定 | `Route.Setting` | アプリ設定画面 |
| ログイン | `Route.Login` | ユーザー認証画面 |
| ストレージ | `Route.Storage` | ストレージ管理画面 |

## NavigationViewModel

ナビゲーション状態を管理するViewModelです。

```kotlin
data class NavigationState @OptIn(ExperimentalTime::class) constructor(
    val user: User = User(),
    val storage: Storage = Storage(),
    val topBar: @Composable () -> Unit = {},
    val bottomBar: @Composable () -> Unit = {},
    val fab: @Composable () -> Unit = {},
    val state: State = State.Idle,
): ViewState

sealed interface NavigationIntent: ViewIntent{
    data object Init: NavigationIntent
    data class SetTopBar(val topBar: @Composable () -> Unit): NavigationIntent
    data class SetBottomBar(val bottomBar: @Composable () -> Unit): NavigationIntent
    data class SetFab(val fab: @Composable () -> Unit): NavigationIntent
}

sealed interface NavigationEffect: ViewEffect{
    data class Navigate(val route: Route): NavigationEffect
    data class ShowToast(val message: String): NavigationEffect
}
```

## Navigation実装

### メインNavigation

```kotlin
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val navigationViewModel: NavigationViewModel = koinViewModel()
    val state by navigationViewModel.state.collectAsState()

    val setTopBar: (@Composable () -> Unit) -> Unit = {
        navigationViewModel.handleIntent(NavigationIntent.SetTopBar(it))
    }
    val setBottomBar: (@Composable () -> Unit) -> Unit = {
        navigationViewModel.handleIntent(NavigationIntent.SetBottomBar(it))
    }
    val setFab: (@Composable () -> Unit) -> Unit = {
        navigationViewModel.handleIntent(NavigationIntent.SetFab(it))
    }

    LaunchedEffect(Unit) {
        navigationViewModel.handleIntent(NavigationIntent.Init)
    }

    LaunchedEffect(Unit){
        navigationViewModel.effect.collect{ effect ->
            when(effect){
                is NavigationEffect.Navigate -> {
                    navController.navigate(effect.route)
                }
                is NavigationEffect.ShowToast -> {
                    //TODO: Toast表示
                }
            }
        }
    }

    NavUi(
        topBar = state.topBar,
        bottomBar = state.bottomBar,
        fab = state.fab,
    ){
        NavHost(navController = navController, startDestination = Route.Login) {
            composable<Route.Login> {
                LoginScreen(
                    navigate = { navController.navigate(it) },
                    snackBar = { /*TODO*/ },
                )
            }
            composable<Route.Storage> {
                StorageScreen(
                    navigate = { navController.navigate(it) },
                    snackBar = { /*TODO*/ },
                )
            }
            composable<Route.Home> {
                HomeScreen(
                    setTopBar = setTopBar,
                    setBottomBar = setBottomBar,
                    setFab = setFab,
                )
            }
            composable<Route.Search> {
                // SearchScreen実装予定
            }
            composable<Route.Dashboard> {
                // DashboardScreen実装予定
            }
            composable<Route.Setting> {
                // SettingScreen実装予定
            }
        }
    }
}
```

### NavUiコンポーネント

```kotlin
@Composable
private fun NavUi(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = fab,
    ){
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(backgroundBrush)
                    .padding(it),
        ) {
            content()
        }
    }
}
```

## 画面遷移の実装

### 基本的な画面遷移

```kotlin
// 画面遷移の実行
navController.navigate(Route.Home)

// 戻る
navController.popBackStack()

// 特定の画面まで戻る
navController.popBackStack(Route.Login, inclusive = false)
```

### パラメータ付き画面遷移

```kotlin
// パラメータ付きRouteの定義
@Serializable
sealed interface Route {
    @Serializable object Home : Route
    @Serializable object Login : Route
    @Serializable data class ItemDetail(val itemId: Int) : Route
    @Serializable data class StorageDetail(val storageId: String) : Route
}

// パラメータ付き画面遷移
navController.navigate(Route.ItemDetail(itemId = 1))
navController.navigate(Route.StorageDetail(storageId = "storage_001"))
```

### 画面遷移のアニメーション

```kotlin
composable<Route.Home>(
    enterTransition = {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(300)
        )
    },
    exitTransition = {
        slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300)
        )
    }
) {
    HomeScreen()
}
```

## 画面別ナビゲーション実装

### LoginScreen

```kotlin
@Composable
fun LoginScreen(
    navigate: (Route) -> Unit,
    snackBar: (String) -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginEffect.Navigation -> {
                    navigate(effect.route)
                }
                is LoginEffect.SnackBar -> {
                    snackBar(effect.message)
                }
            }
        }
    }
    
    // UI実装
    Column {
        // ログインフォーム
        Button(
            onClick = {
                viewModel.handleIntent(LoginIntent.SignIn(
                    email = state.email,
                    password = state.password
                ))
            }
        ) {
            Text("ログイン")
        }
    }
}
```

### HomeScreen

```kotlin
@Composable
fun HomeScreen(
    setTopBar: (@Composable () -> Unit) -> Unit,
    setBottomBar: (@Composable () -> Unit) -> Unit,
    setFab: (@Composable () -> Unit) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        // TopBar設定
        setTopBar {
            TopAppBar(
                title = { Text("ホーム") },
                actions = {
                    IconButton(onClick = { /* 設定画面へ */ }) {
                        Icon(Icons.Default.Settings, "設定")
                    }
                }
            )
        }
        
        // BottomBar設定
        setBottomBar {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Home, "ホーム") },
                    label = { Text("ホーム") },
                    selected = true,
                    onClick = { }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Search, "検索") },
                    label = { Text("検索") },
                    selected = false,
                    onClick = { /* 検索画面へ */ }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Default.Dashboard, "ダッシュボード") },
                    label = { Text("ダッシュボード") },
                    selected = false,
                    onClick = { /* ダッシュボード画面へ */ }
                )
            }
        }
        
        // FAB設定
        setFab {
            FloatingActionButton(
                onClick = { /* アイテム追加 */ }
            ) {
                Icon(Icons.Default.Add, "追加")
            }
        }
    }
    
    // メインコンテンツ
    when (state.state) {
        is State.Loading -> CircularProgressIndicator()
        is State.Success -> {
            LazyColumn {
                items(state.items) { item ->
                    ItemCard(item = item)
                }
            }
        }
        is State.Error -> Text(state.state.message)
    }
}
```

## 深いリンク（Deep Link）

### 深いリンクの定義

```kotlin
// 深いリンクの定義
composable<Route.ItemDetail>(
    deepLinks = listOf(
        navDeepLink {
            uriPattern = "arumo://item/{itemId}"
        }
    )
) { backStackEntry ->
    val itemId = backStackEntry.arguments?.getString("itemId")?.toIntOrNull() ?: 0
    ItemDetailScreen(itemId = itemId)
}
```

### 外部からの深いリンク処理

```kotlin
// AndroidManifest.xmlでの設定
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="arumo" />
    </intent-filter>
</activity>
```

## ナビゲーション状態の管理

### 現在の画面の取得

```kotlin
val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
```

### ナビゲーション履歴の管理

```kotlin
// 履歴の確認
val backStackEntries = navController.currentBackStackEntryAsState()

// 特定の画面が履歴に含まれているかチェック
fun hasRouteInBackStack(route: Route): Boolean {
    return navController.currentBackStack.value.any { 
        it.destination.route == route.toString() 
    }
}
```

## エラーハンドリング

### ナビゲーションエラーの処理

```kotlin
LaunchedEffect(Unit) {
    navigationViewModel.effect.collect { effect ->
        when (effect) {
            is NavigationEffect.Navigate -> {
                try {
                    navController.navigate(effect.route)
                } catch (e: Exception) {
                    // エラーハンドリング
                    Log.e("Navigation", "Navigation failed", e)
                }
            }
            is NavigationEffect.ShowToast -> {
                // Toast表示
            }
        }
    }
}
```

## テスト

### NavigationViewModelのテスト

```kotlin
@Test
fun `when Navigate effect is emitted, navigation should be triggered`() = runTest {
    // Given
    val route = Route.Home
    
    // When
    viewModel.handleEffect(NavigationEffect.Navigate(route))
    
    // Then
    val effect = viewModel.effect.first()
    assertTrue(effect is NavigationEffect.Navigate)
    assertEquals(route, (effect as NavigationEffect.Navigate).route)
}
```

### 画面遷移のテスト

```kotlin
@Test
fun `when navigating to Home, HomeScreen should be displayed`() {
    // Given
    val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    
    // When
    navController.navigate(Route.Home)
    
    // Then
    assertEquals(Route.Home.toString(), navController.currentDestination?.route)
}
```

## ベストプラクティス

### 1. 画面遷移の一貫性
```kotlin
// 一貫した遷移パターンの使用
sealed interface NavigationAction {
    data class Navigate(val route: Route) : NavigationAction
    data class NavigateAndClear(val route: Route) : NavigationAction
    data object PopBackStack : NavigationAction
}
```

### 2. パラメータの型安全性
```kotlin
// 型安全なパラメータ定義
@Serializable
data class ItemDetailParams(
    val itemId: Int,
    val showEdit: Boolean = false
)
```

### 3. 画面の独立性
```kotlin
// 画面間の依存関係を最小化
@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    // 画面実装
}
```

## 注意事項

1. **状態管理**: ナビゲーション状態はViewModelで管理
2. **型安全性**: Routeの定義は型安全に行う
3. **パフォーマンス**: 不要な再描画を避ける
4. **エラーハンドリング**: ナビゲーションエラーを適切に処理
5. **テスト可能性**: ナビゲーションロジックをテスト可能に設計 