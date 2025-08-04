# Arumo-beta API Reference

## 概要

Arumo-betaは消耗品管理アプリのAPIリファレンスです。このドキュメントでは、アプリケーション内で使用される独自のAPIインターフェースについて説明します。

## アーキテクチャ

このプロジェクトはClean Architectureパターンに従って設計されており、以下の層で構成されています：

- **Domain Layer**: ビジネスロジックとエンティティ
- **Data Layer**: データアクセスとリポジトリ実装
- **Presentation Layer**: UIとユーザーインタラクション

## Repository Interfaces

### ItemRepository

消耗品（アイテム）の管理を行うリポジトリインターフェースです。

```kotlin
interface ItemRepository {
    suspend fun upsertItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun getItemById(id: Int): Item?
    suspend fun getRecentlyItemsFromId(id: Int): List<Item>
    suspend fun getItemsByCategoryFromId(id: Int, category: String): List<Item>
    fun getItems(): Flow<List<Item>>
}
```

#### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `upsertItem` | アイテムを追加または更新 | `item: Item` | `Unit` |
| `deleteItem` | アイテムを削除 | `item: Item` | `Unit` |
| `getItemById` | IDでアイテムを取得 | `id: Int` | `Item?` |
| `getRecentlyItemsFromId` | 指定ID以降の最近のアイテムを取得 | `id: Int` | `List<Item>` |
| `getItemsByCategoryFromId` | 指定ID以降のカテゴリ別アイテムを取得 | `id: Int, category: String` | `List<Item>` |
| `getItems` | 全アイテムをFlowで取得 | なし | `Flow<List<Item>>` |

### StorageRepository

保管場所（ストレージ）の管理を行うリポジトリインターフェースです。

```kotlin
interface StorageRepository {
    suspend fun createStorage(storage: Storage)
    suspend fun saveStorage(storage: Storage)
    suspend fun deleteStorage(storage: Storage)
    suspend fun getSavedStorageId(): String
    suspend fun getStorageById(id: String): Storage?
    suspend fun getStorages(): List<Storage>
}
```

#### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `createStorage` | 新しいストレージを作成 | `storage: Storage` | `Unit` |
| `saveStorage` | ストレージを保存 | `storage: Storage` | `Unit` |
| `deleteStorage` | ストレージを削除 | `storage: Storage` | `Unit` |
| `getSavedStorageId` | 保存されたストレージIDを取得 | なし | `String` |
| `getStorageById` | IDでストレージを取得 | `id: String` | `Storage?` |
| `getStorages` | 全ストレージを取得 | なし | `List<Storage>` |

### UserRepository

ユーザー管理を行うリポジトリインターフェースです。

```kotlin
interface UserRepository {
    suspend fun upsertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getSavedUserId(): String
    suspend fun getUserById(id: String): User?
    suspend fun getUsers(): List<User>
}
```

#### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `upsertUser` | ユーザーを追加または更新 | `user: User` | `Unit` |
| `deleteUser` | ユーザーを削除 | `user: User` | `Unit` |
| `getSavedUserId` | 保存されたユーザーIDを取得 | なし | `String` |
| `getUserById` | IDでユーザーを取得 | `id: String` | `User?` |
| `getUsers` | 全ユーザーを取得 | なし | `List<User>` |

### ExpenseRepository

出費管理を行うリポジトリインターフェースです。

```kotlin
interface ExpenseRepository {
    suspend fun upsertExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    fun getExpenseById(id: Int): Flow<Expense?>
    fun getExpenses(): Flow<List<Expense>>
}
```

#### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `upsertExpense` | 出費を追加または更新 | `expense: Expense` | `Unit` |
| `deleteExpense` | 出費を削除 | `expense: Expense` | `Unit` |
| `getExpenseById` | IDで出費を取得 | `id: Int` | `Flow<Expense?>` |
| `getExpenses` | 全出費をFlowで取得 | なし | `Flow<List<Expense>>` |

## Data Models

### Item

消耗品を表すデータクラスです。

```kotlin
data class Item(
    val id: Int = 0,
    val name: String = "",
    val amount: Int = 0,
    val count: Int = 0,
    val category: ConsumableCategory = ConsumableCategory.UNCATEGORIZED,
    val createAt: Instant = Clock.System.now(),
    val updateAt: Instant = Clock.System.now(),
)
```

#### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `Int` | アイテムの一意識別子 | `0` |
| `name` | `String` | アイテム名 | `""` |
| `amount` | `Int` | 金額 | `0` |
| `count` | `Int` | 数量 | `0` |
| `category` | `ConsumableCategory` | カテゴリ | `UNCATEGORIZED` |
| `createAt` | `Instant` | 作成日時 | 現在時刻 |
| `updateAt` | `Instant` | 更新日時 | 現在時刻 |

### Storage

保管場所を表すデータクラスです。

```kotlin
data class Storage(
    val id: String = "",
    val hostId: String = "",
    val name: String = "",
    val iconUri: Uri? = null,
    val description: String = "",
    val itemIDs: List<Int> = emptyList(),
    val expenseIDs: List<Int> = emptyList(),
    val createAt: Instant = Clock.System.now(),
    val updateAt: Instant = Clock.System.now(),
    val editableUserIds: Map<String, Boolean> = emptyMap(),
)
```

#### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `String` | ストレージの一意識別子 | `""` |
| `hostId` | `String` | ホストユーザーID | `""` |
| `name` | `String` | ストレージ名 | `""` |
| `iconUri` | `Uri?` | アイコンURI | `null` |
| `description` | `String` | 説明 | `""` |
| `itemIDs` | `List<Int>` | 含まれるアイテムIDリスト | `emptyList()` |
| `expenseIDs` | `List<Int>` | 含まれる出費IDリスト | `emptyList()` |
| `createAt` | `Instant` | 作成日時 | 現在時刻 |
| `updateAt` | `Instant` | 更新日時 | 現在時刻 |
| `editableUserIds` | `Map<String, Boolean>` | 編集権限を持つユーザーIDマップ | `emptyMap()` |

### User

ユーザーを表すデータクラスです。

```kotlin
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val iconUri: Uri? = null,
    val isPrime: Boolean = false,
)
```

#### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `String` | ユーザーの一意識別子 | `""` |
| `name` | `String` | ユーザー名 | `""` |
| `email` | `String` | メールアドレス | `""` |
| `password` | `String` | パスワード（ハッシュ化） | `""` |
| `iconUri` | `Uri?` | アイコンURI | `null` |
| `isPrime` | `Boolean` | プレミアム会員かどうか | `false` |

### Expense

出費を表すデータクラスです。

```kotlin
data class Expense(
    val id: Int = 0,
    val hostId: String = "",
    val name: String = "",
    val amount: Int = 0,
    val categories: Map<ConsumableCategory, Int> = emptyMap(),
    val createAt: Instant = Clock.System.now(),
    val updateAt: Instant = Clock.System.now(),
)
```

#### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `Int` | 出費の一意識別子 | `0` |
| `hostId` | `String` | ホストユーザーID | `""` |
| `name` | `String` | 出費名 | `""` |
| `amount` | `Int` | 金額 | `0` |
| `categories` | `Map<ConsumableCategory, Int>` | カテゴリ別金額マップ | `emptyMap()` |
| `createAt` | `Instant` | 作成日時 | 現在時刻 |
| `updateAt` | `Instant` | 更新日時 | 現在時刻 |

## Enums

### ConsumableCategory

消耗品のカテゴリを定義する列挙型です。

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

#### カテゴリ一覧

| カテゴリ | 色コード | アイコン | 説明 |
|---------|---------|---------|------|
| `FOOD` | `#FF6347` | Fastfood | 食品 |
| `BEVERAGE` | `#1E90FF` | LocalDrink | 飲料 |
| `DAILY_NECESSITIES` | `#32CD32` | ShoppingBasket | 日用品 |
| `COSMETICS_BEAUTY` | `#DA70D6` | Sanitizer | 化粧品・美容 |
| `PHARMACEUTICALS` | `#008080` | MedicalServices | 医薬品 |
| `KITCHEN_SUPPLIES` | `#FFD700` | Kitchen | キッチン用品 |
| `CLEANING_LAUNDRY` | `#00CED1` | CleaningServices | 掃除・洗濯用品 |
| `BABY` | `#ADD8E6` | ChildCare | ベビー用品 |
| `PETS` | `#D2B48C` | Pets | ペット用品 |
| `STATIONERY_GOODS` | `#4682B4` | Edit | 文房具 |
| `UNCATEGORIZED` | `#A9A9A9` | QuestionMark | 未分類 |

## MVI Architecture

### BaseViewModel

MVI（Model-View-Intent）アーキテクチャのベースクラスです。

```kotlin
abstract class BaseViewModel<
    State : ViewState,
    Intent : ViewIntent,
    Effect : ViewEffect,
>(
    initialViewState: State,
) : ViewModel()
```

#### 状態管理

```kotlin
sealed interface State {
    data object Idle : State
    data object Loading : State
    data object Success : State
    data class Error(val message: String) : State
}
```

#### インターフェース

- `ViewState`: UIの状態を表すインターフェース
- `ViewIntent`: ユーザーアクションを表すインターフェース
- `ViewEffect`: 副作用を表すインターフェース

## 使用例

### アイテムの追加

```kotlin
// ItemRepositoryの使用例
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)

itemRepository.upsertItem(item)
```

### ストレージの作成

```kotlin
// StorageRepositoryの使用例
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)

storageRepository.createStorage(storage)
```

### カテゴリ別アイテム取得

```kotlin
// カテゴリ別アイテム取得の例
val foodItems = itemRepository.getItemsByCategoryFromId(
    id = 0,
    category = ConsumableCategory.FOOD.name
)
```

## 注意事項

1. **非同期処理**: すべてのリポジトリメソッドは`suspend`関数または`Flow`を使用して非同期処理を行います。

2. **データの永続化**: ローカルデータベース（Room）を使用してデータを永続化します。

3. **リアクティブプログラミング**: `Flow`を使用してリアクティブなデータ更新を実現しています。

4. **エラーハンドリング**: MVIアーキテクチャの`Error`状態を使用してエラーを適切に処理します。

## バージョン情報

- **プロジェクト名**: Arumo-beta
- **アーキテクチャ**: Clean Architecture + MVI
- **言語**: Kotlin
- **データベース**: Room
- **UI**: Jetpack Compose 