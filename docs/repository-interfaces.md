---
layout: default
title: Repository Interfaces
---

# Repository Interfaces

## 概要

Arumo-betaでは、Clean Architectureパターンに従ってリポジトリインターフェースを定義しています。これにより、データアクセス層とビジネスロジック層を分離し、テスタビリティと保守性を向上させています。

## ItemRepository

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

### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `upsertItem` | アイテムを追加または更新 | `item: Item` | `Unit` |
| `deleteItem` | アイテムを削除 | `item: Item` | `Unit` |
| `getItemById` | IDでアイテムを取得 | `id: Int` | `Item?` |
| `getRecentlyItemsFromId` | 指定ID以降の最近のアイテムを取得 | `id: Int` | `List<Item>` |
| `getItemsByCategoryFromId` | 指定ID以降のカテゴリ別アイテムを取得 | `id: Int, category: String` | `List<Item>` |
| `getItems` | 全アイテムをFlowで取得 | なし | `Flow<List<Item>>` |

### 使用例

```kotlin
// アイテムの追加
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)
itemRepository.upsertItem(item)

// 特定のアイテムを取得
val item = itemRepository.getItemById(1)

// カテゴリ別アイテムを取得
val foodItems = itemRepository.getItemsByCategoryFromId(
    id = 0,
    category = ConsumableCategory.FOOD.name
)
```

## StorageRepository

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

### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `createStorage` | 新しいストレージを作成 | `storage: Storage` | `Unit` |
| `saveStorage` | ストレージを保存 | `storage: Storage` | `Unit` |
| `deleteStorage` | ストレージを削除 | `storage: Storage` | `Unit` |
| `getSavedStorageId` | 保存されたストレージIDを取得 | なし | `String` |
| `getStorageById` | IDでストレージを取得 | `id: String` | `Storage?` |
| `getStorages` | 全ストレージを取得 | なし | `List<Storage>` |

### 使用例

```kotlin
// ストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)
storageRepository.createStorage(storage)

// 特定のストレージを取得
val storage = storageRepository.getStorageById("storage123")

// 全ストレージを取得
val storages = storageRepository.getStorages()
```

## UserRepository

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

### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `upsertUser` | ユーザーを追加または更新 | `user: User` | `Unit` |
| `deleteUser` | ユーザーを削除 | `user: User` | `Unit` |
| `getSavedUserId` | 保存されたユーザーIDを取得 | なし | `String` |
| `getUserById` | IDでユーザーを取得 | `id: String` | `User?` |
| `getUsers` | 全ユーザーを取得 | なし | `List<User>` |

## ExpenseRepository

出費管理を行うリポジトリインターフェースです。

```kotlin
interface ExpenseRepository {
    suspend fun upsertExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    fun getExpenseById(id: Int): Flow<Expense?>
    fun getExpenses(): Flow<List<Expense>>
}
```

### メソッド詳細

| メソッド | 説明 | パラメータ | 戻り値 |
|---------|------|-----------|--------|
| `upsertExpense` | 出費を追加または更新 | `expense: Expense` | `Unit` |
| `deleteExpense` | 出費を削除 | `expense: Expense` | `Unit` |
| `getExpenseById` | IDで出費を取得 | `id: Int` | `Flow<Expense?>` |
| `getExpenses` | 全出費をFlowで取得 | なし | `Flow<List<Expense>>` |

## 実装の注意点

1. **非同期処理**: すべてのメソッドは`suspend`関数または`Flow`を使用
2. **エラーハンドリング**: 適切な例外処理を実装
3. **データの整合性**: トランザクション管理を考慮
4. **パフォーマンス**: 大量データの効率的な処理 