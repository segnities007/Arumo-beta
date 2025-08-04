---
layout: default
title: Use Cases
---

# Use Cases

## 概要

Use Case層は、Clean ArchitectureのDomain層に属し、アプリケーションのビジネスロジックを担当します。この層では、具体的なユーザーアクションに対応するビジネスルールを実装します。

## Use Caseの設計原則

### 1. 単一責任の原則
各Use Caseは一つの具体的なビジネスアクションのみを担当します。

### 2. 依存性の逆転
Repositoryインターフェースに依存し、具体的な実装には依存しません。

### 3. 純粋関数
外部の副作用を避け、入力に対して予測可能な出力を返します。

## 基本的なUse Case構造

```kotlin
abstract class UseCase<in P, R> {
    suspend operator fun invoke(parameters: P): Result<R>
}

abstract class FlowUseCase<in P, R> {
    operator fun invoke(parameters: P): Flow<R>
}
```

## 実装例

### AddItemUseCase

アイテム追加のビジネスロジックを実装します。

```kotlin
class AddItemUseCase(
    private val itemRepository: ItemRepository
) : UseCase<AddItemUseCase.Params, Unit> {

    data class Params(
        val name: String,
        val amount: Int,
        val count: Int,
        val category: ConsumableCategory,
        val storageId: String? = null
    )

    override suspend fun invoke(parameters: Params): Result<Unit> {
        return try {
            // バリデーション
            validateParams(parameters)
            
            // アイテム作成
            val item = Item(
                name = parameters.name,
                amount = parameters.amount,
                count = parameters.count,
                category = parameters.category
            )
            
            // リポジトリに保存
            itemRepository.upsertItem(item)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun validateParams(params: Params) {
        require(params.name.isNotBlank()) { "アイテム名は必須です" }
        require(params.amount >= 0) { "金額は0以上である必要があります" }
        require(params.count > 0) { "数量は1以上である必要があります" }
    }
}
```

### GetItemsByCategoryUseCase

カテゴリ別アイテム取得のビジネスロジックを実装します。

```kotlin
class GetItemsByCategoryUseCase(
    private val itemRepository: ItemRepository
) : FlowUseCase<GetItemsByCategoryUseCase.Params, List<Item>> {

    data class Params(
        val category: ConsumableCategory?,
        val limit: Int = 20,
        val offset: Int = 0
    )

    override fun invoke(parameters: Params): Flow<List<Item>> {
        return if (parameters.category != null) {
            itemRepository.getItemsByCategoryFromId(
                id = parameters.offset,
                category = parameters.category.name
            ).asFlow()
        } else {
            itemRepository.getItems()
        }
    }
}
```

### CalculateExpenseUseCase

出費計算のビジネスロジックを実装します。

```kotlin
class CalculateExpenseUseCase(
    private val itemRepository: ItemRepository,
    private val expenseRepository: ExpenseRepository
) : UseCase<CalculateExpenseUseCase.Params, Expense> {

    data class Params(
        val name: String,
        val items: List<Item>,
        val hostId: String
    )

    override suspend fun invoke(parameters: Params): Result<Expense> {
        return try {
            // カテゴリ別金額を計算
            val categoryAmounts = parameters.items
                .groupBy { it.category }
                .mapValues { (_, items) -> items.sumOf { it.amount * it.count } }
            
            // 総金額を計算
            val totalAmount = categoryAmounts.values.sum()
            
            // 出費オブジェクトを作成
            val expense = Expense(
                name = parameters.name,
                amount = totalAmount,
                categories = categoryAmounts,
                hostId = parameters.hostId
            )
            
            // リポジトリに保存
            expenseRepository.upsertExpense(expense)
            
            Result.success(expense)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### StorageManagementUseCase

ストレージ管理のビジネスロジックを実装します。

```kotlin
class StorageManagementUseCase(
    private val storageRepository: StorageRepository,
    private val itemRepository: ItemRepository
) : UseCase<StorageManagementUseCase.Params, Storage> {

    sealed class Params {
        data class CreateStorage(
            val name: String,
            val description: String,
            val hostId: String,
            val iconUri: Uri? = null
        ) : Params()
        
        data class AddItemToStorage(
            val storageId: String,
            val itemId: Int
        ) : Params()
        
        data class RemoveItemFromStorage(
            val storageId: String,
            val itemId: Int
        ) : Params()
    }

    override suspend fun invoke(parameters: Params): Result<Storage> {
        return try {
            when (parameters) {
                is Params.CreateStorage -> createStorage(parameters)
                is Params.AddItemToStorage -> addItemToStorage(parameters)
                is Params.RemoveItemFromStorage -> removeItemFromStorage(parameters)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun createStorage(params: Params.CreateStorage): Result<Storage> {
        val storage = Storage(
            id = UUID.randomUUID().toString(),
            name = params.name,
            description = params.description,
            hostId = params.hostId,
            iconUri = params.iconUri
        )
        
        storageRepository.createStorage(storage)
        return Result.success(storage)
    }
    
    private suspend fun addItemToStorage(params: Params.AddItemToStorage): Result<Storage> {
        val storage = storageRepository.getStorageById(params.storageId)
            ?: throw IllegalArgumentException("ストレージが見つかりません")
        
        val updatedStorage = storage.copy(
            itemIDs = storage.itemIDs + params.itemId
        )
        
        storageRepository.saveStorage(updatedStorage)
        return Result.success(updatedStorage)
    }
    
    private suspend fun removeItemFromStorage(params: Params.RemoveItemFromStorage): Result<Storage> {
        val storage = storageRepository.getStorageById(params.storageId)
            ?: throw IllegalArgumentException("ストレージが見つかりません")
        
        val updatedStorage = storage.copy(
            itemIDs = storage.itemIDs - params.itemId
        )
        
        storageRepository.saveStorage(updatedStorage)
        return Result.success(updatedStorage)
    }
}
```

## ViewModelでの使用例

```kotlin
class HomeViewModel(
    private val getItemsUseCase: GetItemsByCategoryUseCase,
    private val addItemUseCase: AddItemUseCase
) : BaseViewModel<HomeState, HomeIntent, HomeEffect>(
    initialViewState = HomeState()
) {
    
    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadItems -> loadItems(intent)
            is HomeIntent.AddItem -> addItem(intent)
        }
    }
    
    private fun loadItems(intent: HomeIntent.LoadItems) {
        viewModelScope.launch {
            _state.value = state.value.copy(state = State.Loading)
            
            getItemsUseCase(GetItemsByCategoryUseCase.Params(
                category = intent.category
            )).collect { items ->
                _state.value = state.value.copy(
                    items = items,
                    state = State.Success
                )
            }
        }
    }
    
    private fun addItem(intent: HomeIntent.AddItem) {
        viewModelScope.launch {
            _state.value = state.value.copy(state = State.Loading)
            
            val result = addItemUseCase(AddItemUseCase.Params(
                name = intent.name,
                amount = intent.amount,
                count = intent.count,
                category = intent.category
            ))
            
            result.fold(
                onSuccess = {
                    _state.value = state.value.copy(state = State.Success)
                    handleEffect(HomeEffect.ShowToast("アイテムを追加しました"))
                },
                onFailure = { exception ->
                    _state.value = state.value.copy(
                        state = State.Error(exception.message ?: "アイテムの追加に失敗しました")
                    )
                }
            )
        }
    }
}
```

## テスト

### Use Caseのテスト

```kotlin
@Test
fun `when AddItemUseCase is invoked with valid params, it should succeed`() = runTest {
    // Given
    val params = AddItemUseCase.Params(
        name = "Test Item",
        amount = 100,
        count = 1,
        category = ConsumableCategory.FOOD
    )
    coEvery { itemRepository.upsertItem(any()) } just Runs
    
    // When
    val result = addItemUseCase(params)
    
    // Then
    assertTrue(result.isSuccess)
    coVerify { itemRepository.upsertItem(any()) }
}

@Test
fun `when AddItemUseCase is invoked with invalid params, it should fail`() = runTest {
    // Given
    val params = AddItemUseCase.Params(
        name = "",
        amount = -1,
        count = 0,
        category = ConsumableCategory.FOOD
    )
    
    // When & Then
    assertThrows<IllegalArgumentException> {
        addItemUseCase(params)
    }
}
```

## ベストプラクティス

### 1. エラーハンドリング
```kotlin
sealed class UseCaseError : Exception() {
    data class ValidationError(val message: String) : UseCaseError()
    data class NetworkError(val cause: Throwable) : UseCaseError()
    data class DatabaseError(val cause: Throwable) : UseCaseError()
}
```

### 2. バリデーション
```kotlin
private fun validateItem(item: Item) {
    require(item.name.isNotBlank()) { "アイテム名は必須です" }
    require(item.amount >= 0) { "金額は0以上である必要があります" }
    require(item.count > 0) { "数量は1以上である必要があります" }
}
```

### 3. 依存性注入
```kotlin
class UseCaseModule {
    fun provideAddItemUseCase(itemRepository: ItemRepository): AddItemUseCase {
        return AddItemUseCase(itemRepository)
    }
    
    fun provideGetItemsUseCase(itemRepository: ItemRepository): GetItemsByCategoryUseCase {
        return GetItemsByCategoryUseCase(itemRepository)
    }
}
```

## 注意事項

1. **単一責任**: 各Use Caseは一つのビジネスアクションのみを担当
2. **純粋関数**: 外部の副作用を避け、予測可能な出力を返す
3. **エラーハンドリング**: 適切な例外処理とエラー型の定義
4. **テスト可能性**: モック可能な設計でテストを容易にする
5. **パフォーマンス**: 必要に応じてキャッシュや最適化を実装 