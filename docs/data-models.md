---
layout: default
title: Data Models
---

# Data Models

## 概要

Arumo-betaで使用されるデータモデルの詳細な説明です。これらのモデルは、アプリケーションのドメイン層で定義され、ビジネスロジックを表現します。

## Item

消耗品（アイテム）を表すデータクラスです。

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

### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `Int` | アイテムの一意識別子 | `0` |
| `name` | `String` | アイテム名 | `""` |
| `amount` | `Int` | 金額（円） | `0` |
| `count` | `Int` | 数量 | `0` |
| `category` | `ConsumableCategory` | カテゴリ | `UNCATEGORIZED` |
| `createAt` | `Instant` | 作成日時 | 現在時刻 |
| `updateAt` | `Instant` | 更新日時 | 現在時刻 |

### 使用例

```kotlin
// 基本的なアイテムの作成
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)

// 完全なアイテムの作成
val item = Item(
    id = 1,
    name = "洗濯洗剤",
    amount = 500,
    count = 2,
    category = ConsumableCategory.CLEANING_LAUNDRY,
    createAt = Clock.System.now(),
    updateAt = Clock.System.now()
)
```

## Storage

保管場所（ストレージ）を表すデータクラスです。

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

### プロパティ

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

### 使用例

```kotlin
// 基本的なストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)

// 完全なストレージの作成
val storage = Storage(
    id = "storage_001",
    hostId = "user123",
    name = "寝室のクローゼット",
    description = "衣類の保管場所",
    itemIDs = listOf(1, 2, 3),
    expenseIDs = listOf(1, 2),
    editableUserIds = mapOf("user456" to true, "user789" to false)
)
```

## User

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

### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `String` | ユーザーの一意識別子 | `""` |
| `name` | `String` | ユーザー名 | `""` |
| `email` | `String` | メールアドレス | `""` |
| `password` | `String` | パスワード（ハッシュ化） | `""` |
| `iconUri` | `Uri?` | アイコンURI | `null` |
| `isPrime` | `Boolean` | プレミアム会員かどうか | `false` |

### 使用例

```kotlin
// 基本的なユーザーの作成
val user = User(
    name = "田中太郎",
    email = "tanaka@example.com",
    password = "hashedPassword123"
)

// 完全なユーザーの作成
val user = User(
    id = "user_001",
    name = "田中太郎",
    email = "tanaka@example.com",
    password = "hashedPassword123",
    iconUri = Uri.parse("content://com.example.provider/avatar.jpg"),
    isPrime = true
)
```

## Expense

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

### プロパティ

| プロパティ | 型 | 説明 | デフォルト値 |
|-----------|----|------|-------------|
| `id` | `Int` | 出費の一意識別子 | `0` |
| `hostId` | `String` | ホストユーザーID | `""` |
| `name` | `String` | 出費名 | `""` |
| `amount` | `Int` | 総金額（円） | `0` |
| `categories` | `Map<ConsumableCategory, Int>` | カテゴリ別金額マップ | `emptyMap()` |
| `createAt` | `Instant` | 作成日時 | 現在時刻 |
| `updateAt` | `Instant` | 更新日時 | 現在時刻 |

### 使用例

```kotlin
// 基本的な出費の作成
val expense = Expense(
    name = "スーパーでの買い物",
    amount = 3000,
    hostId = "user123"
)

// カテゴリ別金額を含む出費の作成
val expense = Expense(
    id = 1,
    hostId = "user123",
    name = "月次買い物",
    amount = 15000,
    categories = mapOf(
        ConsumableCategory.FOOD to 8000,
        ConsumableCategory.BEVERAGE to 2000,
        ConsumableCategory.DAILY_NECESSITIES to 5000
    )
)
```

## データ変換

### Entity変換

各データモデルには、RoomデータベースのEntityとの変換メソッドが用意されています：

```kotlin
// ItemEntityとの変換
val itemEntity = ItemEntity.fromModel(item)
val item = itemEntity.toModel()

// StorageEntityとの変換
val storageEntity = StorageEntity.fromModel(storage)
val storage = storageEntity.toModel()

// UserEntityとの変換
val userEntity = UserEntity.fromModel(user)
val user = userEntity.toModel()

// ExpenseEntityとの変換
val expenseEntity = ExpenseEntity.fromModel(expense)
val expense = expenseEntity.toModel()
```

## 注意事項

1. **不変性**: すべてのデータクラスは不変（immutable）として設計されています
2. **デフォルト値**: すべてのプロパティには適切なデフォルト値が設定されています
3. **時刻管理**: `createAt`と`updateAt`は自動的に現在時刻が設定されます
4. **ID管理**: データベースの主キーは自動生成されます
5. **型安全性**: Kotlinの型システムを活用して型安全性を確保しています 