import { Code, Database, User, TrendingUp } from 'lucide-react'

export default function RepositoryInterfaces() {
  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">Repository Interfaces</h1>
        <p className="text-xl text-gray-600">
          Arumo-betaでは、Clean Architectureパターンに従ってリポジトリインターフェースを定義しています。
        </p>
      </div>

      {/* ItemRepository */}
      <div className="card mb-8">
        <div className="flex items-center mb-6">
          <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mr-4">
            <Database className="w-6 h-6 text-primary-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900">ItemRepository</h2>
        </div>
        
        <p className="text-gray-600 mb-6">
          消耗品（アイテム）の管理を行うリポジトリインターフェースです。
        </p>

        <div className="bg-gray-900 rounded-lg p-6 mb-6">
          <pre className="text-gray-100 overflow-x-auto">
            <code>{`interface ItemRepository {
  suspend fun upsertItem(item: Item)
  suspend fun deleteItem(item: Item)
  suspend fun getItemById(id: Int): Item?
  suspend fun getRecentlyItemsFromId(id: Int): List<Item>
  suspend fun getItemsByCategoryFromId(id: Int, category: String): List<Item>
  fun getItems(): Flow<List<Item>>
}`}</code>
          </pre>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full border-collapse">
            <thead>
              <tr className="bg-primary-500 text-white">
                <th className="px-4 py-3 text-left">メソッド</th>
                <th className="px-4 py-3 text-left">説明</th>
                <th className="px-4 py-3 text-left">パラメータ</th>
                <th className="px-4 py-3 text-left">戻り値</th>
              </tr>
            </thead>
            <tbody>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">upsertItem</td>
                <td className="px-4 py-3">アイテムを追加または更新</td>
                <td className="px-4 py-3 font-mono text-sm">item: Item</td>
                <td className="px-4 py-3 font-mono text-sm">Unit</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">deleteItem</td>
                <td className="px-4 py-3">アイテムを削除</td>
                <td className="px-4 py-3 font-mono text-sm">item: Item</td>
                <td className="px-4 py-3 font-mono text-sm">Unit</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getItemById</td>
                <td className="px-4 py-3">IDでアイテムを取得</td>
                <td className="px-4 py-3 font-mono text-sm">id: Int</td>
                <td className="px-4 py-3 font-mono text-sm">Item?</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getRecentlyItemsFromId</td>
                <td className="px-4 py-3">指定ID以降の最近のアイテムを取得</td>
                <td className="px-4 py-3 font-mono text-sm">id: Int</td>
                <td className="px-4 py-3 font-mono text-sm">List&lt;Item&gt;</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getItemsByCategoryFromId</td>
                <td className="px-4 py-3">指定ID以降のカテゴリ別アイテムを取得</td>
                <td className="px-4 py-3 font-mono text-sm">id: Int, category: String</td>
                <td className="px-4 py-3 font-mono text-sm">List&lt;Item&gt;</td>
              </tr>
              <tr className="hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getItems</td>
                <td className="px-4 py-3">全アイテムをFlowで取得</td>
                <td className="px-4 py-3 font-mono text-sm">なし</td>
                <td className="px-4 py-3 font-mono text-sm">Flow&lt;List&lt;Item&gt;&gt;</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {/* StorageRepository */}
      <div className="card mb-8">
        <div className="flex items-center mb-6">
          <div className="w-12 h-12 bg-accent-100 rounded-lg flex items-center justify-center mr-4">
            <Database className="w-6 h-6 text-accent-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900">StorageRepository</h2>
        </div>
        
        <p className="text-gray-600 mb-6">
          保管場所（ストレージ）の管理を行うリポジトリインターフェースです。
        </p>

        <div className="bg-gray-900 rounded-lg p-6 mb-6">
          <pre className="text-gray-100 overflow-x-auto">
            <code>{`interface StorageRepository {
  suspend fun createStorage(storage: Storage)
  suspend fun saveStorage(storage: Storage)
  suspend fun deleteStorage(storage: Storage)
  suspend fun getSavedStorageId(): String
  suspend fun getStorageById(id: String): Storage?
  suspend fun getStorages(): List<Storage>
}`}</code>
          </pre>
        </div>

        <div className="overflow-x-auto">
          <table className="w-full border-collapse">
            <thead>
              <tr className="bg-accent-500 text-white">
                <th className="px-4 py-3 text-left">メソッド</th>
                <th className="px-4 py-3 text-left">説明</th>
                <th className="px-4 py-3 text-left">パラメータ</th>
                <th className="px-4 py-3 text-left">戻り値</th>
              </tr>
            </thead>
            <tbody>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">createStorage</td>
                <td className="px-4 py-3">新しいストレージを作成</td>
                <td className="px-4 py-3 font-mono text-sm">storage: Storage</td>
                <td className="px-4 py-3 font-mono text-sm">Unit</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">saveStorage</td>
                <td className="px-4 py-3">ストレージを保存</td>
                <td className="px-4 py-3 font-mono text-sm">storage: Storage</td>
                <td className="px-4 py-3 font-mono text-sm">Unit</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">deleteStorage</td>
                <td className="px-4 py-3">ストレージを削除</td>
                <td className="px-4 py-3 font-mono text-sm">storage: Storage</td>
                <td className="px-4 py-3 font-mono text-sm">Unit</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getSavedStorageId</td>
                <td className="px-4 py-3">保存されたストレージIDを取得</td>
                <td className="px-4 py-3 font-mono text-sm">なし</td>
                <td className="px-4 py-3 font-mono text-sm">String</td>
              </tr>
              <tr className="border-b border-gray-200 hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getStorageById</td>
                <td className="px-4 py-3">IDでストレージを取得</td>
                <td className="px-4 py-3 font-mono text-sm">id: String</td>
                <td className="px-4 py-3 font-mono text-sm">Storage?</td>
              </tr>
              <tr className="hover:bg-gray-50">
                <td className="px-4 py-3 font-mono text-sm">getStorages</td>
                <td className="px-4 py-3">全ストレージを取得</td>
                <td className="px-4 py-3 font-mono text-sm">なし</td>
                <td className="px-4 py-3 font-mono text-sm">List&lt;Storage&gt;</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      {/* UserRepository */}
      <div className="card mb-8">
        <div className="flex items-center mb-6">
          <div className="w-12 h-12 bg-success-100 rounded-lg flex items-center justify-center mr-4">
            <User className="w-6 h-6 text-success-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900">UserRepository</h2>
        </div>
        
        <p className="text-gray-600 mb-6">
          ユーザー管理を行うリポジトリインターフェースです。
        </p>

        <div className="bg-gray-900 rounded-lg p-6 mb-6">
          <pre className="text-gray-100 overflow-x-auto">
            <code>{`interface UserRepository {
  suspend fun upsertUser(user: User)
  suspend fun deleteUser(user: User)
  suspend fun getSavedUserId(): String
  suspend fun getUserById(id: String): User?
  suspend fun getUsers(): List<User>
}`}</code>
          </pre>
        </div>
      </div>

      {/* ExpenseRepository */}
      <div className="card">
        <div className="flex items-center mb-6">
          <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mr-4">
            <TrendingUp className="w-6 h-6 text-primary-600" />
          </div>
          <h2 className="text-2xl font-bold text-gray-900">ExpenseRepository</h2>
        </div>
        
        <p className="text-gray-600 mb-6">
          出費管理を行うリポジトリインターフェースです。
        </p>

        <div className="bg-gray-900 rounded-lg p-6 mb-6">
          <pre className="text-gray-100 overflow-x-auto">
            <code>{`interface ExpenseRepository {
  suspend fun upsertExpense(expense: Expense)
  suspend fun deleteExpense(expense: Expense)
  fun getExpenseById(id: Int): Flow<Expense?>
  fun getExpenses(): Flow<List<Expense>>
}`}</code>
          </pre>
        </div>
      </div>
    </div>
  )
} 