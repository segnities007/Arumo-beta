---
layout: default
title: Enums
---

# Enums

## 概要

Arumo-betaで使用される列挙型の詳細な説明です。これらの列挙型は、アプリケーション全体で一貫した値の管理を提供します。

## ConsumableCategory

消耗品のカテゴリを定義する列挙型です。各カテゴリには色コードとアイコンが関連付けられています。

```kotlin
enum class ConsumableCategory(
    val colorCode: String,
    val icon: ImageVector,
) {
    FOOD("#FF6347", Icons.Default.Fastfood),
    BEVERAGE("#1E90FF", Icons.Default.LocalDrink),
    DAILY_NECESSITIES("#32CD32", Icons.Default.ShoppingBasket),
    COSMETICS_BEAUTY("#DA70D6", Icons.Default.Sanitizer),
    PHARMACEUTICALS("#008080", Icons.Default.MedicalServices),
    KITCHEN_SUPPLIES("#FFD700", Icons.Default.Kitchen),
    CLEANING_LAUNDRY("#00CED1", Icons.Default.CleaningServices),
    BABY("#ADD8E6", Icons.Default.ChildCare),
    PETS("#D2B48C", Icons.Default.Pets),
    STATIONERY_GOODS("#4682B4", Icons.Default.Edit),
    UNCATEGORIZED("#A9A9A9", Icons.Default.QuestionMark),
}
```

### カテゴリ一覧

| カテゴリ | 色コード | アイコン | 説明 |
|---------|---------|---------|------|
| `FOOD` | `#FF6347` | Fastfood | 食品（米、パン、野菜、肉など） |
| `BEVERAGE` | `#1E90FF` | LocalDrink | 飲料（ジュース、お茶、コーヒーなど） |
| `DAILY_NECESSITIES` | `#32CD32` | ShoppingBasket | 日用品（トイレットペーパー、歯ブラシなど） |
| `COSMETICS_BEAUTY` | `#DA70D6` | Sanitizer | 化粧品・美容（シャンプー、化粧品など） |
| `PHARMACEUTICALS` | `#008080` | MedicalServices | 医薬品（風邪薬、ビタミン剤など） |
| `KITCHEN_SUPPLIES` | `#FFD700` | Kitchen | キッチン用品（調味料、調理器具など） |
| `CLEANING_LAUNDRY` | `#00CED1` | CleaningServices | 掃除・洗濯用品（洗剤、掃除道具など） |
| `BABY` | `#ADD8E6` | ChildCare | ベビー用品（おむつ、粉ミルクなど） |
| `PETS` | `#D2B48C` | Pets | ペット用品（ペットフード、おもちゃなど） |
| `STATIONERY_GOODS` | `#4682B4` | Edit | 文房具（ノート、ペンなど） |
| `UNCATEGORIZED` | `#A9A9A9` | QuestionMark | 未分類 |

### 使用例

```kotlin
// カテゴリの取得
val foodCategory = ConsumableCategory.FOOD
val beverageCategory = ConsumableCategory.BEVERAGE

// 色コードの取得
val foodColor = ConsumableCategory.FOOD.colorCode // "#FF6347"
val beverageColor = ConsumableCategory.BEVERAGE.colorCode // "#1E90FF"

// アイコンの取得
val foodIcon = ConsumableCategory.FOOD.icon
val beverageIcon = ConsumableCategory.BEVERAGE.icon

// アイテム作成時の使用
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)

// カテゴリ名の文字列取得
val categoryName = ConsumableCategory.FOOD.name // "FOOD"

// 文字列からカテゴリへの変換
val category = ConsumableCategory.valueOf("FOOD") // ConsumableCategory.FOOD
```

### カテゴリ別フィルタリング

```kotlin
// 特定のカテゴリのアイテムを取得
val foodItems = itemRepository.getItemsByCategoryFromId(
    id = 0,
    category = ConsumableCategory.FOOD.name
)

// 全カテゴリの列挙
ConsumableCategory.values().forEach { category ->
    println("${category.name}: ${category.colorCode}")
}
```

### UIでの使用

```kotlin
// Composeでの色の使用
Box(
    modifier = Modifier
        .background(Color(android.graphics.Color.parseColor(category.colorCode)))
) {
    // コンテンツ
}

// アイコンの表示
Icon(
    imageVector = category.icon,
    contentDescription = category.name
)
```

## State

MVIアーキテクチャで使用される状態を表す列挙型です。

```kotlin
sealed interface State {
    data object Idle : State
    data object Loading : State
    data object Success : State
    data class Error(val message: String) : State
}
```

### 状態の種類

| 状態 | 説明 | 使用場面 |
|------|------|----------|
| `Idle` | 初期状態 | 画面表示時、処理開始前 |
| `Loading` | 読み込み中 | データ取得中、処理実行中 |
| `Success` | 成功 | 処理完了時 |
| `Error` | エラー | エラー発生時（メッセージ付き） |

### 使用例

```kotlin
// ViewModelでの状態管理
class HomeViewModel : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialViewState = HomeState()
) {
    private fun loadItems() {
        viewModelScope.launch {
            _state.value = state.value.copy(state = State.Loading)
            try {
                val items = itemRepository.getItems()
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

// UIでの状態に応じた表示
when (state.state) {
    is State.Idle -> {
        // 初期表示
    }
    is State.Loading -> {
        CircularProgressIndicator()
    }
    is State.Success -> {
        // 成功時の表示
        LazyColumn {
            items(state.items) { item ->
                ItemCard(item = item)
            }
        }
    }
    is State.Error -> {
        // エラー時の表示
        Text(text = state.state.message)
    }
}
```

## カスタム列挙型の作成

新しい列挙型を作成する際のベストプラクティス：

```kotlin
// 基本的な列挙型
enum class SortOrder {
    ASCENDING,
    DESCENDING
}

// プロパティを持つ列挙型
enum class FilterType(
    val displayName: String,
    val icon: ImageVector
) {
    ALL("すべて", Icons.Default.List),
    FAVORITES("お気に入り", Icons.Default.Favorite),
    RECENT("最近", Icons.Default.Schedule)
}

// 関数を持つ列挙型
enum class NotificationType(
    val title: String,
    val description: String
) {
    LOW_STOCK("在庫不足", "アイテムの在庫が少なくなっています"),
    EXPIRY_DATE("賞味期限", "アイテムの賞味期限が近づいています"),
    SHARED_ITEM("共有アイテム", "新しいアイテムが共有されました");

    fun getFullMessage(): String {
        return "$title: $description"
    }
}
```

## 注意事項

1. **型安全性**: 列挙型を使用することで、コンパイル時に型安全性を確保できます
2. **一貫性**: アプリケーション全体で一貫した値の管理が可能です
3. **拡張性**: 新しい値の追加が容易です
4. **パフォーマンス**: 文字列比較よりも効率的です
5. **可読性**: コードの意図が明確になります 