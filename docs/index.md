---
layout: default
title: Introduction
---

<div class="content-header">
  <h1>🚀 Arumo-beta API Reference</h1>
  <p class="subtitle">消耗品管理アプリの包括的なAPIドキュメント</p>
</div>

<div class="content-body">
  <div class="section fade-in-up">
    <h2>📋 概要</h2>
    
    <div class="alert alert-info">
      <strong>Arumo-beta</strong>は消耗品管理アプリのAPIリファレンスです。このドキュメントでは、アプリケーション内で使用される独自のAPIインターフェースについて説明します。
    </div>

    <div class="card">
      <div class="card-title">
        <i class="fas fa-star"></i>
        主な機能
      </div>
      <div class="card-content">
        <ul>
          <li><strong>レシート解析</strong> - Gemini APIによる自動品目解析</li>
          <li><strong>在庫管理</strong> - ローカル/サーバー保存対応</li>
          <li><strong>通知機能</strong> - 在庫消費・賞味期限リマインダー</li>
          <li><strong>共有機能</strong> - Storage単位での在庫共有</li>
          <li><strong>出費管理</strong> - グラフ表示・分析機能</li>
        </ul>
      </div>
    </div>
  </div>

  <div class="section fade-in-up">
    <h2>🏗 アーキテクチャ</h2>
    
    <p>このプロジェクトは<strong>Clean Architecture</strong>パターンに従って設計されており、以下の層で構成されています：</p>

    <div class="grid grid-3">
      <div class="card">
        <div class="card-title">
          <i class="fas fa-palette"></i>
          Presentation Layer
        </div>
        <div class="card-content">
          <ul>
            <li>Jetpack Compose UI</li>
            <li>MVI Architecture</li>
            <li>ViewModel</li>
            <li>State Management</li>
          </ul>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-brain"></i>
          Domain Layer
        </div>
        <div class="card-content">
          <ul>
            <li>Use Cases</li>
            <li>Entities</li>
            <li>Repository Interfaces</li>
            <li>Business Logic</li>
          </ul>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-database"></i>
          Data Layer
        </div>
        <div class="card-content">
          <ul>
            <li>Repository Implementations</li>
            <li>Local Database (Room)</li>
            <li>Remote API (Supabase)</li>
            <li>Data Sources</li>
          </ul>
        </div>
      </div>
    </div>
  </div>

  <div class="section fade-in-up">
    <h2>⚡ クイックスタート</h2>
    
    <div class="alert alert-success">
      <strong>💡 ヒント:</strong> 以下のコードサンプルをコピーして、すぐに開発を始められます。
    </div>

    <h3>基本的な使用例</h3>

    <div class="card">
      <div class="card-title">
        <i class="fas fa-box"></i>
        アイテムの追加
      </div>
      <div class="card-content">
        <pre><code class="language-kotlin">// アイテムの追加
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)
itemRepository.upsertItem(item)</code></pre>
      </div>
    </div>

    <div class="card">
      <div class="card-title">
        <i class="fas fa-home"></i>
        ストレージの作成
      </div>
      <div class="card-content">
        <pre><code class="language-kotlin">// ストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)
storageRepository.createStorage(storage)</code></pre>
      </div>
    </div>

    <div class="card">
      <div class="card-title">
        <i class="fas fa-search"></i>
        カテゴリ別アイテム取得
      </div>
      <div class="card-content">
        <pre><code class="language-kotlin">// カテゴリ別アイテム取得の例
val foodItems = itemRepository.getItemsByCategoryFromId(
    id = 0,
    category = ConsumableCategory.FOOD.name
)</code></pre>
      </div>
    </div>

    <div class="card">
      <div class="card-title">
        <i class="fas fa-cogs"></i>
        Use Caseの使用
      </div>
      <div class="card-content">
        <pre><code class="language-kotlin">// Use Caseを使用したアイテム追加
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
)</code></pre>
      </div>
    </div>

    <div class="card">
      <div class="card-title">
        <i class="fas fa-route"></i>
        画面遷移
      </div>
      <div class="card-content">
        <pre><code class="language-kotlin">// 画面遷移の実装
navController.navigate(Route.Home)
navController.navigate(Route.Storage)

// パラメータ付き画面遷移
navController.navigate(Route.ItemDetail(itemId = 1))</code></pre>
      </div>
    </div>
  </div>

  <div class="section fade-in-up">
    <h2>📚 API Reference</h2>
    
    <div class="grid grid-2">
      <div class="card">
        <div class="card-title">
          <span class="badge badge-primary">API</span>
          <i class="fas fa-code"></i>
          Repository Interfaces
        </div>
        <div class="card-content">
          <p>リポジトリインターフェースの詳細な説明とメソッド一覧</p>
          <a href="repository-interfaces.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <span class="badge badge-secondary">Data</span>
          <i class="fas fa-database"></i>
          Data Models
        </div>
        <div class="card-content">
          <p>データモデルの定義とプロパティの詳細説明</p>
          <a href="data-models.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <span class="badge badge-accent">Enum</span>
          <i class="fas fa-list"></i>
          Enums
        </div>
        <div class="card-content">
          <p>列挙型の説明とカテゴリ一覧</p>
          <a href="enums.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <span class="badge badge-primary">Architecture</span>
          <i class="fas fa-sitemap"></i>
          MVI Architecture
        </div>
        <div class="card-content">
          <p>MVIアーキテクチャの詳細と実装方法</p>
          <a href="mvi-architecture.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <span class="badge badge-secondary">Business Logic</span>
          <i class="fas fa-cogs"></i>
          Use Cases
        </div>
        <div class="card-content">
          <p>ビジネスロジックとUse Case層の実装</p>
          <a href="use-cases.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <span class="badge badge-accent">Navigation</span>
          <i class="fas fa-route"></i>
          Navigation
        </div>
        <div class="card-content">
          <p>画面遷移とナビゲーションシステム</p>
          <a href="navigation.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <span class="badge badge-primary">Database</span>
          <i class="fas fa-database"></i>
          Database
        </div>
        <div class="card-content">
          <p>RoomデータベースとEntity設計</p>
          <a href="database.html" class="btn btn-primary">
            <i class="fas fa-arrow-right"></i>
            詳細を見る
          </a>
        </div>
      </div>
      

    </div>
  </div>

  <div class="section fade-in-up">
    <h2>🛠 技術スタック</h2>
    
    <div class="grid grid-4">
      <div class="card">
        <div class="card-title">
          <i class="fas fa-code"></i>
          言語
        </div>
        <div class="card-content">
          <span class="badge badge-primary">Kotlin 1.9+</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-palette"></i>
          UI
        </div>
        <div class="card-content">
          <span class="badge badge-secondary">Jetpack Compose</span>
          <span class="badge badge-secondary">Material 3</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-sitemap"></i>
          アーキテクチャ
        </div>
        <div class="card-content">
          <span class="badge badge-accent">Clean Architecture</span>
          <span class="badge badge-accent">MVI Pattern</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-database"></i>
          データベース
        </div>
        <div class="card-content">
          <span class="badge badge-primary">Room 2.6+</span>
          <span class="badge badge-primary">SQLite</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-bolt"></i>
          非同期処理
        </div>
        <div class="card-content">
          <span class="badge badge-secondary">Coroutines</span>
          <span class="badge badge-secondary">Flow</span>
          <span class="badge badge-secondary">StateFlow</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-cogs"></i>
          依存性注入
        </div>
        <div class="card-content">
          <span class="badge badge-accent">Koin 3.5+</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-route"></i>
          ナビゲーション
        </div>
        <div class="card-content">
          <span class="badge badge-primary">Compose Navigation</span>
        </div>
      </div>
      
      <div class="card">
        <div class="card-title">
          <i class="fas fa-test-tube"></i>
          テスト
        </div>
        <div class="card-content">
          <span class="badge badge-secondary">JUnit 5</span>
          <span class="badge badge-secondary">MockK</span>
        </div>
      </div>
    </div>
  </div>

  <div class="section fade-in-up">
    <h2>📄 ライセンス</h2>
    
    <div class="alert alert-info">
      <strong>MIT License</strong> - このプロジェクトはMITライセンスの下で公開されています。
    </div>
    
    <div class="card">
      <div class="card-title">
        <i class="fas fa-user"></i>
        開発者
      </div>
      <div class="card-content">
        <p><strong>Segnities007</strong> - フルスタック開発</p>
        <a href="https://github.com/segnities007" class="btn btn-outline" target="_blank">
          <i class="fab fa-github"></i>
          GitHub Profile
        </a>
      </div>
    </div>
  </div>
</div>

<style>
.mobile-menu-toggle {
  display: none;
  position: fixed;
  top: 1rem;
  left: 1rem;
  z-index: 1001;
  background: #22c55e;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0.75rem;
  font-size: 1.2rem;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .mobile-menu-toggle {
    display: block;
  }
}
</style>

<script>
// スムーズスクロール
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

// フェードインアニメーション
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

document.querySelectorAll('.fade-in-up').forEach(el => {
    el.style.opacity = '0';
    el.style.transform = 'translateY(30px)';
    el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
    observer.observe(el);
});
</script> 