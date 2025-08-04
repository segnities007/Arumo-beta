# Arumo-beta API Documentation

## 概要

このドキュメントは、Arumo-betaアプリケーションの包括的なAPIリファレンスです。消耗品管理アプリの開発に必要なすべての情報が含まれています。

## 📚 ドキュメント構成

### メインページ
- **[index.md](index.md)** - アプリケーションの概要とクイックスタートガイド

### API Reference
- **[API_REFERENCE.md](API_REFERENCE.md)** - 完全なAPIリファレンス（統合版）
- **[repository-interfaces.md](repository-interfaces.md)** - リポジトリインターフェースの詳細
- **[data-models.md](data-models.md)** - データモデルの定義とプロパティ
- **[enums.md](enums.md)** - 列挙型の説明とカテゴリ一覧
- **[mvi-architecture.md](mvi-architecture.md)** - MVIアーキテクチャの詳細
- **[use-cases.md](use-cases.md)** - ビジネスロジックとUse Case層の実装
- **[navigation.md](navigation.md)** - 画面遷移とナビゲーションシステム
- **[database.md](database.md)** - RoomデータベースとEntity設計

## 🚀 クイックスタート

### 1. 基本的なアイテムの追加

```kotlin
// アイテムの作成
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)

// リポジトリを使用してアイテムを保存
itemRepository.upsertItem(item)
```

### 2. ストレージの作成

```kotlin
// ストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)

// リポジトリを使用してストレージを保存
storageRepository.createStorage(storage)
```

### 3. カテゴリ別アイテムの取得

```kotlin
// 食品カテゴリのアイテムを取得
val foodItems = itemRepository.getItemsByCategoryFromId(
    id = 0,
    category = ConsumableCategory.FOOD.name
)
```

### 4. Use Caseを使用したビジネスロジック

```kotlin
// Use Caseを使用したアイテム追加
val addItemUseCase = AddItemUseCase(itemRepository)
val result = addItemUseCase(AddItemUseCase.Params(
    name = "洗濯洗剤",
    amount = 500,
    count = 2,
    category = ConsumableCategory.CLEANING_LAUNDRY
))

result.fold(
    onSuccess = { /* 成功処理 */ },
    onFailure = { error -> /* エラー処理 */ }
)
```

### 5. 画面遷移の実装

```kotlin
// 基本的な画面遷移
navController.navigate(Route.Home)
navController.navigate(Route.Storage)

// パラメータ付き画面遷移
navController.navigate(Route.ItemDetail(itemId = 1))
```

## 🏗 アーキテクチャ

Arumo-betaは以下のアーキテクチャパターンを採用しています：

### Clean Architecture
- **Presentation Layer**: Jetpack Compose UI, MVI Architecture
- **Domain Layer**: Use Cases, Entities, Repository Interfaces
- **Data Layer**: Repository Implementations, Room Database

### MVI Pattern
- **Model**: アプリケーションの状態
- **View**: UIの表示とユーザーインタラクション
- **Intent**: ユーザーアクション

## 📋 主要コンポーネント

### Repository Interfaces
- `ItemRepository` - 消耗品の管理
- `StorageRepository` - 保管場所の管理
- `UserRepository` - ユーザー管理
- `ExpenseRepository` - 出費管理

### Data Models
- `Item` - 消耗品を表すデータクラス
- `Storage` - 保管場所を表すデータクラス
- `User` - ユーザーを表すデータクラス
- `Expense` - 出費を表すデータクラス

### Use Cases
- `AddItemUseCase` - アイテム追加のビジネスロジック
- `GetItemsByCategoryUseCase` - カテゴリ別アイテム取得
- `CalculateExpenseUseCase` - 出費計算
- `StorageManagementUseCase` - ストレージ管理

### Navigation
- `Route` - 画面遷移の定義
- `NavigationViewModel` - ナビゲーション状態管理
- `NavHost` - 画面遷移の実装

### Database
- `ItemEntity`, `StorageEntity`, `UserEntity`, `ExpenseEntity` - データベースエンティティ
- `ItemDao`, `StorageDao`, `UserDao`, `ExpenseDao` - データアクセスオブジェクト

### Enums
- `ConsumableCategory` - 消耗品のカテゴリ（11種類）

## 🛠 技術スタック

| カテゴリ | 技術 | バージョン |
|---------|------|-----------|
| **言語** | Kotlin | 1.9+ |
| **UI** | Jetpack Compose | 1.5+ |
| **UI** | Material 3 | 1.1+ |
| **アーキテクチャ** | Clean Architecture | - |
| **アーキテクチャ** | MVI Pattern | - |
| **データベース** | Room | 2.6+ |
| **データベース** | SQLite | - |
| **非同期処理** | Coroutines | 1.7+ |
| **非同期処理** | Flow | 1.7+ |
| **非同期処理** | StateFlow | 1.7+ |
| **依存性注入** | Koin | 3.5+ |
| **ナビゲーション** | Compose Navigation | 2.7+ |
| **テスト** | JUnit 5 | 5.9+ |
| **テスト** | MockK | 1.13+ |

## 📖 使用方法

### 1. リポジトリの使用

```kotlin
// 依存性注入でリポジトリを取得
class MyViewModel(
    private val itemRepository: ItemRepository
) : BaseViewModel<MyState, MyIntent, MyEffect>(initialViewState = MyState()) {
    
    fun addItem(name: String, amount: Int) {
        val item = Item(
            name = name,
            amount = amount,
            category = ConsumableCategory.FOOD
        )
        itemRepository.upsertItem(item)
    }
}
```

### 2. MVIパターンの実装

```kotlin
// State定義
data class MyState(
    val items: List<Item> = emptyList(),
    val state: State = State.Idle
) : ViewState

// Intent定義
sealed interface MyIntent : ViewIntent {
    data class LoadItems(val category: ConsumableCategory?) : MyIntent
    data class AddItem(val item: Item) : MyIntent
}

// Effect定義
sealed interface MyEffect : ViewEffect {
    data class ShowToast(val message: String) : MyEffect
    data class Navigate(val route: Route) : MyEffect
}
```

### 3. UIでの使用

```kotlin
@Composable
fun MyScreen(
    viewModel: MyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    when (state.state) {
        is State.Loading -> CircularProgressIndicator()
        is State.Success -> {
            LazyColumn {
                items(state.items) { item ->
                    ItemCard(item = item)
                }
            }
        }
        is State.Error -> Text(text = state.state.message)
    }
}
```

## 🔍 検索とナビゲーション

### コード検索
- **Repository**: `ItemRepository`, `StorageRepository`, `UserRepository`, `ExpenseRepository`
- **Models**: `Item`, `Storage`, `User`, `Expense`
- **Enums**: `ConsumableCategory`
- **Architecture**: `BaseViewModel`, `MVI`, `State`

### 機能別検索
- **データ管理**: Repository Interfaces, Data Models
- **UI**: MVI Architecture, Jetpack Compose
- **カテゴリ**: ConsumableCategory, Enums
- **実装例**: 使用例, ベストプラクティス

## 📝 開発ガイドライン

### 1. 命名規則
- **Repository**: `EntityRepository` (例: `ItemRepository`)
- **Model**: 単数形 (例: `Item`, `Storage`)
- **Intent**: `Action` + `Entity` (例: `AddItem`, `DeleteItem`)

### 2. エラーハンドリング
```kotlin
try {
    val result = repository.someOperation()
    _state.value = state.value.copy(state = State.Success)
} catch (e: Exception) {
    _state.value = state.value.copy(
        state = State.Error(e.message ?: "Unknown error")
    )
}
```

### 3. 非同期処理
```kotlin
viewModelScope.launch(Dispatchers.IO) {
    // 重い処理
    withContext(Dispatchers.Main) {
        // UI更新
    }
}
```

## 🧪 テスト

### Repository テスト
```kotlin
@Test
fun `when item is added, it should be saved`() = runTest {
    // Given
    val item = Item(name = "Test Item")
    
    // When
    itemRepository.upsertItem(item)
    
    // Then
    val savedItem = itemRepository.getItemById(item.id)
    assertEquals(item, savedItem)
}
```

### ViewModel テスト
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

## 📞 サポート

### よくある質問
1. **Q**: 新しいカテゴリを追加するには？
   **A**: `ConsumableCategory` enumに新しい値を追加してください。

2. **Q**: カスタムリポジトリを作成するには？
   **A**: 既存のリポジトリインターフェースを参考に、新しいインターフェースと実装を作成してください。

3. **Q**: MVIパターンで新しい画面を作成するには？
   **A**: `BaseViewModel`を継承し、`State`、`Intent`、`Effect`を定義してください。

### 貢献
このドキュメントの改善提案やバグ報告は、GitHubのIssueでお知らせください。

## 📄 ライセンス

このプロジェクトはMITライセンスの下で公開されています。

---

**開発者**: Segnities007  
**プロジェクト**: Arumo-beta  
**バージョン**: 1.0.0 