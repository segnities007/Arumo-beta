import { Rocket, Database, Code, BookOpen, Zap, Star } from 'lucide-react'
import { Link } from 'react-router-dom'

export default function Home() {
  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <div className="bg-gradient-to-br from-primary-500 via-primary-600 to-accent-500 text-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24">
          <div className="text-center">
            <h1 className="text-4xl md:text-6xl font-bold mb-6 animate-fade-in">
              🚀 Arumo-beta API Reference
            </h1>
            <p className="text-xl md:text-2xl mb-8 opacity-90">
              消耗品管理アプリの包括的なAPIドキュメント
            </p>
            <div className="flex flex-wrap justify-center gap-4">
              <Link
                to="/repository-interfaces"
                className="btn btn-secondary text-lg px-8 py-3"
              >
                <Code className="w-5 h-5" />
                API Reference
              </Link>
              <a
                href="https://github.com/segnities007/Arumobeta"
                target="_blank"
                rel="noopener noreferrer"
                className="btn btn-outline text-lg px-8 py-3 border-white text-white hover:bg-white hover:text-primary-600"
              >
                <Star className="w-5 h-5" />
                GitHub
              </a>
            </div>
          </div>
        </div>
      </div>

      {/* Features Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-16">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            主な機能
          </h2>
          <p className="text-xl text-gray-600 max-w-3xl mx-auto">
            Arumo-betaは消耗品管理に特化した多機能アプリです
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div className="card group">
            <div className="flex items-center mb-4">
              <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mr-4">
                <Rocket className="w-6 h-6 text-primary-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900">レシート解析</h3>
            </div>
            <p className="text-gray-600">
              Gemini APIによる自動品目解析で、レシートから消耗品を自動登録
            </p>
          </div>

          <div className="card group">
            <div className="flex items-center mb-4">
              <div className="w-12 h-12 bg-accent-100 rounded-lg flex items-center justify-center mr-4">
                <Database className="w-6 h-6 text-accent-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900">在庫管理</h3>
            </div>
            <p className="text-gray-600">
              ローカル/サーバー保存対応で、柔軟な在庫管理を実現
            </p>
          </div>

          <div className="card group">
            <div className="flex items-center mb-4">
              <div className="w-12 h-12 bg-success-100 rounded-lg flex items-center justify-center mr-4">
                <Zap className="w-6 h-6 text-success-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900">通知機能</h3>
            </div>
            <p className="text-gray-600">
              在庫消費・賞味期限リマインダーで、忘れ物を防止
            </p>
          </div>

          <div className="card group">
            <div className="flex items-center mb-4">
              <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mr-4">
                <Code className="w-6 h-6 text-primary-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900">共有機能</h3>
            </div>
            <p className="text-gray-600">
              Storage単位での在庫共有で、家族やルームメイトと協力
            </p>
          </div>

          <div className="card group">
            <div className="flex items-center mb-4">
              <div className="w-12 h-12 bg-accent-100 rounded-lg flex items-center justify-center mr-4">
                <BookOpen className="w-6 h-6 text-accent-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900">出費管理</h3>
            </div>
            <p className="text-gray-600">
              グラフ表示・分析機能で、家計管理をサポート
            </p>
          </div>

          <div className="card group">
            <div className="flex items-center mb-4">
              <div className="w-12 h-12 bg-success-100 rounded-lg flex items-center justify-center mr-4">
                <Star className="w-6 h-6 text-success-600" />
              </div>
              <h3 className="text-xl font-semibold text-gray-900">モダン技術</h3>
            </div>
            <p className="text-gray-600">
              Kotlin + Jetpack Compose + Clean Architecture
            </p>
          </div>
        </div>
      </div>

      {/* Quick Start Section */}
      <div className="bg-gray-50 py-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-12">
            <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
              ⚡ クイックスタート
            </h2>
            <p className="text-xl text-gray-600">
              以下のコードサンプルをコピーして、すぐに開発を始められます
            </p>
          </div>

          <div className="grid md:grid-cols-2 gap-8">
            <div className="card">
              <h3 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
                <Code className="w-5 h-5 mr-2 text-primary-500" />
                アイテムの追加
              </h3>
              <pre className="bg-gray-900 text-gray-100 p-4 rounded-lg overflow-x-auto">
                <code>{`// アイテムの追加
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)
itemRepository.upsertItem(item)`}</code>
              </pre>
            </div>

            <div className="card">
              <h3 className="text-xl font-semibold text-gray-900 mb-4 flex items-center">
                <Database className="w-5 h-5 mr-2 text-accent-500" />
                ストレージの作成
              </h3>
              <pre className="bg-gray-900 text-gray-100 p-4 rounded-lg overflow-x-auto">
                <code>{`// ストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)
storageRepository.createStorage(storage)`}</code>
              </pre>
            </div>
          </div>
        </div>
      </div>

      {/* Documentation Links */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            📚 ドキュメント
          </h2>
          <p className="text-xl text-gray-600">
            詳細なAPIリファレンスとガイド
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
          <Link to="/repository-interfaces" className="card group hover:scale-105 transition-transform">
            <div className="flex items-center mb-4">
              <div className="w-10 h-10 bg-primary-100 rounded-lg flex items-center justify-center mr-3">
                <Code className="w-5 h-5 text-primary-600" />
              </div>
              <h3 className="text-lg font-semibold text-gray-900">Repository Interfaces</h3>
            </div>
            <p className="text-gray-600">リポジトリインターフェースの詳細な説明とメソッド一覧</p>
          </Link>

          <Link to="/data-models" className="card group hover:scale-105 transition-transform">
            <div className="flex items-center mb-4">
              <div className="w-10 h-10 bg-accent-100 rounded-lg flex items-center justify-center mr-3">
                <Database className="w-5 h-5 text-accent-600" />
              </div>
              <h3 className="text-lg font-semibold text-gray-900">Data Models</h3>
            </div>
            <p className="text-gray-600">データモデルの定義とプロパティの詳細説明</p>
          </Link>

          <Link to="/enums" className="card group hover:scale-105 transition-transform">
            <div className="flex items-center mb-4">
              <div className="w-10 h-10 bg-success-100 rounded-lg flex items-center justify-center mr-3">
                <BookOpen className="w-5 h-5 text-success-600" />
              </div>
              <h3 className="text-lg font-semibold text-gray-900">Enums</h3>
            </div>
            <p className="text-gray-600">列挙型の説明とカテゴリ一覧</p>
          </Link>
        </div>
      </div>
    </div>
  )
} 