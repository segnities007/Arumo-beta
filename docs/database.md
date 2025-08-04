---
layout: default
title: Database
---

# Database

## 概要

Arumo-betaでは、Roomデータベースを使用してローカルデータの永続化を行っています。このドキュメントでは、データベースの設計、Entity、DAO、およびデータ変換について説明します。

## データベース設計

### データベース構造

アプリケーションでは、各エンティティに対して個別のデータベースを定義しています：

- `ItemDatabase` - アイテム管理用
- `StorageDatabase` - ストレージ管理用
- `UserDatabase` - ユーザー管理用
- `ExpenseDatabase` - 出費管理用

### 基本的なデータベース定義

```kotlin
@Database(entities = [ItemEntity::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
```

## Entity定義

### ItemEntity

アイテムのデータベースエンティティです。

```kotlin
@Entity(tableName = "items")
data class ItemEntity
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String = "",
        val amount: Int = 0,
        val count: Int = 0,
        val categoryName: String = ConsumableCategory.UNCATEGORIZED.name,
        val createAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
        val updateAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
    ) {
        @OptIn(ExperimentalTime::class)
        fun toModel(): Item =
            Item(
                id = id,
                name = name,
                amount = amount,
                count = count,
                category = ConsumableCategory.valueOf(categoryName),
                createAt = Instant.fromEpochMilliseconds(createAtMillis),
                updateAt = Instant.fromEpochMilliseconds(updateAtMillis),
            )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun fromModel(item: Item): ItemEntity =
                ItemEntity(
                    id = item.id,
                    name = item.name,
                    amount = item.amount,
                    count = item.count,
                    categoryName = item.category.name,
                    createAtMillis = item.createAt.toEpochMilliseconds(),
                    updateAtMillis = item.updateAt.toEpochMilliseconds(),
                )
        }
    }
```

### StorageEntity

ストレージのデータベースエンティティです。

```kotlin
@Entity(tableName = "storages")
data class StorageEntity
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey
        val id: String = "",
        val hostId: String = "",
        val name: String = "",
        val iconUri: String = "",
        val description: String = "",
        val itemIDsJson: String = "[]", // JSON文字列で保存
        val expenseIDsJson: String = "[]", // JSON文字列で保存
        val editableUserIdsJson: String = "{}", // JSON文字列で保存
        val createAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
        val updateAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
    ) {
        @OptIn(ExperimentalTime::class)
        fun toModel(): Storage =
            Storage(
                id = id,
                hostId = hostId,
                name = name,
                iconUri = if (iconUri.isNotEmpty()) iconUri.toUri() else null,
                description = description,
                itemIDs = Json.decodeFromString(itemIDsJson),
                expenseIDs = Json.decodeFromString(expenseIDsJson),
                editableUserIds = Json.decodeFromString(editableUserIdsJson),
                createAt = Instant.fromEpochMilliseconds(createAtMillis),
                updateAt = Instant.fromEpochMilliseconds(updateAtMillis),
            )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun fromModel(storage: Storage): StorageEntity =
                StorageEntity(
                    id = storage.id,
                    hostId = storage.hostId,
                    name = storage.name,
                    iconUri = storage.iconUri?.toString() ?: "",
                    description = storage.description,
                    itemIDsJson = Json.encodeToString(storage.itemIDs),
                    expenseIDsJson = Json.encodeToString(storage.expenseIDs),
                    editableUserIdsJson = Json.encodeToString(storage.editableUserIds),
                    createAtMillis = storage.createAt.toEpochMilliseconds(),
                    updateAtMillis = storage.updateAt.toEpochMilliseconds(),
                )
        }
    }
```

### UserEntity

ユーザーのデータベースエンティティです。

```kotlin
@Entity(tableName = "users")
data class UserEntity
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey
        val id: String = "",
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val iconUri: String = "",
        val isPrime: Boolean = false,
    ) {
        fun toModel(): User =
            User(
                id = id,
                name = name,
                email = email,
                password = password,
                iconUri = if (iconUri.isNotEmpty()) iconUri.toUri() else null,
                isPrime = isPrime,
            )

        companion object {
            fun fromModel(user: User): UserEntity =
                UserEntity(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    password = user.password,
                    iconUri = user.iconUri?.toString() ?: "",
                    isPrime = user.isPrime,
                )
        }
    }
```

### ExpenseEntity

出費のデータベースエンティティです。

```kotlin
@Entity(tableName = "expenses")
data class ExpenseEntity
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val hostId: String = "",
        val name: String = "",
        val amount: Int = 0,
        val categoriesJson: String = "{}", // JSON文字列で保存
        val createAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
        val updateAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
    ) {
        @OptIn(ExperimentalTime::class)
        fun toModel(): Expense =
            Expense(
                id = id,
                hostId = hostId,
                name = name,
                amount = amount,
                categories = Json.decodeFromString(categoriesJson),
                createAt = Instant.fromEpochMilliseconds(createAtMillis),
                updateAt = Instant.fromEpochMilliseconds(updateAtMillis),
            )

        companion object {
            @OptIn(ExperimentalTime::class)
            fun fromModel(expense: Expense): ExpenseEntity =
                ExpenseEntity(
                    id = expense.id,
                    hostId = expense.hostId,
                    name = expense.name,
                    amount = expense.amount,
                    categoriesJson = Json.encodeToString(expense.categories),
                    createAtMillis = expense.createAt.toEpochMilliseconds(),
                    updateAtMillis = expense.updateAt.toEpochMilliseconds(),
                )
        }
    }
```

## DAO（Data Access Object）

### ItemDao

アイテムのデータアクセスオブジェクトです。

```kotlin
@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ItemEntity)

    @Delete
    suspend fun delete(item: ItemEntity)

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Flow<ItemEntity?>

    @Query("SELECT * FROM items WHERE id >= :id ORDER BY id ASC LIMIT 20")
    fun getRecentItemsFromId(id: Int): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE categoryName = :category AND id >= :id ORDER BY id ASC LIMIT 20")
    fun getItemsByCategoryFromId(category: String, id: Int): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items ORDER BY updateAtMillis DESC")
    fun getAllItems(): Flow<List<ItemEntity>>
}
```

### StorageDao

ストレージのデータアクセスオブジェクトです。

```kotlin
@Dao
interface StorageDao {
    @Insert
    suspend fun create(storage: StorageEntity)

    @Delete
    suspend fun delete(storage: StorageEntity)

    @Query("SELECT * FROM storages WHERE id = :id")
    fun getStorageById(id: String): Flow<StorageEntity?>

    @Query("SELECT * FROM storages ORDER BY updateAtMillis DESC")
    fun getAllStorages(): Flow<List<StorageEntity>>
}
```

### UserDao

ユーザーのデータアクセスオブジェクトです。

```kotlin
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<UserEntity>>
}
```

### ExpenseDao

出費のデータアクセスオブジェクトです。

```kotlin
@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses WHERE id = :id")
    fun getExpenseById(id: Int): Flow<ExpenseEntity?>

    @Query("SELECT * FROM expenses ORDER BY updateAtMillis DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
}
```

## データベースファクトリ

### DatabaseFactory

データベースインスタンスの作成を管理します。

```kotlin
object DatabaseFactory {
    fun createItemDatabase(context: Context): ItemDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ItemDatabase::class.java,
            "item_database"
        ).build()
    }

    fun createStorageDatabase(context: Context): StorageDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StorageDatabase::class.java,
            "storage_database"
        ).build()
    }

    fun createUserDatabase(context: Context): UserDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }

    fun createExpenseDatabase(context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java,
            "expense_database"
        ).build()
    }
}
```

## データ変換パターン

### Entity ↔ Model変換

各Entityには、Modelとの変換メソッドが定義されています：

```kotlin
// EntityからModelへの変換
val item = itemEntity.toModel()

// ModelからEntityへの変換
val itemEntity = ItemEntity.fromModel(item)
```

### リスト変換

```kotlin
// EntityリストからModelリストへの変換
val items = itemEntities.map { it.toModel() }

// ModelリストからEntityリストへの変換
val itemEntities = items.map { ItemEntity.fromModel(it) }
```

## データベース操作の実装例

### アイテムの追加

```kotlin
suspend fun addItem(item: Item) {
    val itemEntity = ItemEntity.fromModel(item)
    itemDao.upsert(itemEntity)
}
```

### アイテムの取得

```kotlin
fun getItems(): Flow<List<Item>> {
    return itemDao.getAllItems()
        .map { entities -> entities.map { it.toModel() } }
}
```

### カテゴリ別アイテム取得

```kotlin
suspend fun getItemsByCategory(category: ConsumableCategory): List<Item> {
    return itemDao.getItemsByCategoryFromId(
        category = category.name,
        id = 0
    ).first().map { it.toModel() }
}
```

## マイグレーション

### データベースバージョン管理

```kotlin
@Database(
    entities = [ItemEntity::class], 
    version = 2,
    exportSchema = true
)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // マイグレーション処理
                database.execSQL("ALTER TABLE items ADD COLUMN priority INTEGER DEFAULT 0")
            }
        }
    }
}
```

## パフォーマンス最適化

### インデックスの追加

```kotlin
@Entity(
    tableName = "items",
    indices = [
        Index(value = ["categoryName"]),
        Index(value = ["updateAtMillis"])
    ]
)
data class ItemEntity(
    // ...
)
```

### クエリの最適化

```kotlin
// 効率的なクエリ
@Query("SELECT * FROM items WHERE categoryName = :category ORDER BY updateAtMillis DESC LIMIT :limit")
fun getItemsByCategoryWithLimit(category: String, limit: Int): Flow<List<ItemEntity>>
```

## エラーハンドリング

### データベース操作のエラー処理

```kotlin
suspend fun safeUpsertItem(item: Item): Result<Unit> {
    return try {
        val itemEntity = ItemEntity.fromModel(item)
        itemDao.upsert(itemEntity)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

### トランザクション処理

```kotlin
@Transaction
suspend fun updateItemWithStorage(item: Item, storage: Storage) {
    // アイテム更新
    val itemEntity = ItemEntity.fromModel(item)
    itemDao.upsert(itemEntity)
    
    // ストレージ更新
    val storageEntity = StorageEntity.fromModel(storage)
    storageDao.create(storageEntity)
}
```

## テスト

### DAOのテスト

```kotlin
@Test
fun `when item is inserted, it should be retrievable`() = runTest {
    // Given
    val item = Item(name = "Test Item", amount = 100)
    val itemEntity = ItemEntity.fromModel(item)
    
    // When
    itemDao.upsert(itemEntity)
    val retrievedEntity = itemDao.getItemById(item.id).first()
    
    // Then
    assertNotNull(retrievedEntity)
    assertEquals(item.name, retrievedEntity?.name)
}
```

### データベースのテスト

```kotlin
@RunWith(AndroidJUnit4::class)
class ItemDatabaseTest {
    private lateinit var database: ItemDatabase
    private lateinit var itemDao: ItemDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = database.itemDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndReadItem() = runTest {
        // Given
        val item = Item(name = "Test Item", amount = 100)
        val itemEntity = ItemEntity.fromModel(item)
        
        // When
        itemDao.upsert(itemEntity)
        val retrievedEntity = itemDao.getItemById(item.id).first()
        
        // Then
        assertNotNull(retrievedEntity)
        assertEquals(item.name, retrievedEntity?.name)
    }
}
```

## ベストプラクティス

### 1. データベース設計
- 正規化されたテーブル設計
- 適切なインデックスの設定
- 外部キー制約の使用

### 2. パフォーマンス
- 大量データのページネーション
- 不要なクエリの回避
- 適切なキャッシュ戦略

### 3. データ整合性
- トランザクションの使用
- バリデーションの実装
- エラーハンドリング

### 4. セキュリティ
- SQLインジェクション対策
- 機密データの暗号化
- アクセス制御の実装

## 注意事項

1. **メインスレッド**: データベース操作はバックグラウンドスレッドで実行
2. **メモリ管理**: 大量データの適切な管理
3. **バージョン管理**: マイグレーションの慎重な設計
4. **パフォーマンス**: クエリの最適化とインデックスの活用
5. **テスト**: データベース操作の包括的なテスト 