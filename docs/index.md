---
layout: default
title: Arumo-beta API Reference
---

<div class="site-header">
  <h1>🚀 Arumo-beta API Reference</h1>
  <div class="subtitle">消耗品管理アプリの包括的なAPIドキュメント</div>
</div>

<div class="nav-links">
  <a href="#overview">概要</a>
  <a href="#architecture">アーキテクチャ</a>
  <a href="#quick-start">クイックスタート</a>
  <a href="#documentation">ドキュメント</a>
  <a href="https://github.com/segnities007/Arumobeta" target="_blank">GitHub</a>
</div>

<div class="section fade-in-up">
  <h2 id="overview">📋 概要</h2>
  
  <div class="alert alert-info">
    <strong>Arumo-beta</strong>は消耗品管理アプリのAPIリファレンスです。このドキュメントでは、アプリケーション内で使用される独自のAPIインターフェースについて説明します。
  </div>

  <div class="card">
    <div class="card-title">🎯 主な機能</div>
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
  <h2 id="architecture">🏗 アーキテクチャ</h2>
  
  <p>このプロジェクトは<strong>Clean Architecture</strong>パターンに従って設計されており、以下の層で構成されています：</p>

  <div class="card">
    <div class="card-title">📊 アーキテクチャ図</div>
    <div class="card-content">
      <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1rem; margin: 1rem 0;">
        <div style="background: linear-gradient(135deg, #3b82f6, #1d4ed8); color: white; padding: 1rem; border-radius: 8px;">
          <h4 style="margin: 0 0 0.5rem 0;">🎨 Presentation Layer</h4>
          <p style="margin: 0; font-size: 0.9rem;">Jetpack Compose UI</p>
        </div>
        <div style="background: linear-gradient(135deg, #8b5cf6, #7c3aed); color: white; padding: 1rem; border-radius: 8px;">
          <h4 style="margin: 0 0 0.5rem 0;">🧠 Domain Layer</h4>
          <p style="margin: 0; font-size: 0.9rem;">ビジネスロジック</p>
        </div>
        <div style="background: linear-gradient(135deg, #10b981, #059669); color: white; padding: 1rem; border-radius: 8px;">
          <h4 style="margin: 0 0 0.5rem 0;">💾 Data Layer</h4>
          <p style="margin: 0; font-size: 0.9rem;">データアクセス</p>
        </div>
      </div>
    </div>
  </div>

  <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 1.5rem; margin: 2rem 0;">
    <div class="card">
      <div class="card-title">🎨 Presentation Layer</div>
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
      <div class="card-title">🧠 Domain Layer</div>
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
      <div class="card-title">💾 Data Layer</div>
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
  <h2 id="quick-start">⚡ クイックスタート</h2>
  
  <div class="alert alert-success">
    <strong>💡 ヒント:</strong> 以下のコードサンプルをコピーして、すぐに開発を始められます。
  </div>

  <h3>基本的な使用例</h3>

  <div class="card">
    <div class="card-title">📦 アイテムの追加</div>
    <div class="card-content">
      <pre><code>// アイテムの追加
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
    <div class="card-title">🏠 ストレージの作成</div>
    <div class="card-content">
      <pre><code>// ストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)
storageRepository.createStorage(storage)</code></pre>
    </div>
  </div>

  <div class="card">
    <div class="card-title">🔍 カテゴリ別アイテム取得</div>
    <div class="card-content">
      <pre><code>// カテゴリ別アイテム取得の例
val foodItems = itemRepository.getItemsByCategoryFromId(
    id = 0,
    category = ConsumableCategory.FOOD.name
)</code></pre>
    </div>
  </div>
</div>

<div class="section fade-in-up">
  <h2 id="documentation">📚 ドキュメント構成</h2>
  
  <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 1.5rem;">
    <div class="card">
      <div class="card-title">
        <span class="badge badge-primary">API</span>
        Repository Interfaces
      </div>
      <div class="card-content">
        <p>リポジトリインターフェースの詳細な説明とメソッド一覧</p>
        <a href="repository-interfaces.md" class="btn btn-primary">詳細を見る</a>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">
        <span class="badge badge-secondary">Data</span>
        Data Models
      </div>
      <div class="card-content">
        <p>データモデルの定義とプロパティの詳細説明</p>
        <a href="data-models.md" class="btn btn-primary">詳細を見る</a>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">
        <span class="badge badge-accent">Enum</span>
        Enums
      </div>
      <div class="card-content">
        <p>列挙型の説明とカテゴリ一覧</p>
        <a href="enums.md" class="btn btn-primary">詳細を見る</a>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">
        <span class="badge badge-primary">Architecture</span>
        MVI Architecture
      </div>
      <div class="card-content">
        <p>MVIアーキテクチャの詳細と実装方法</p>
        <a href="mvi-architecture.md" class="btn btn-primary">詳細を見る</a>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">
        <span class="badge badge-secondary">Examples</span>
        Examples
      </div>
      <div class="card-content">
        <p>使用例とサンプルコード集</p>
        <a href="examples.md" class="btn btn-primary">詳細を見る</a>
      </div>
    </div>
  </div>
</div>

<div class="section fade-in-up">
  <h2>🛠 技術スタック</h2>
  
  <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem;">
    <div class="card">
      <div class="card-title">💻 言語</div>
      <div class="card-content">
        <span class="badge badge-primary">Kotlin</span>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">🎨 UI</div>
      <div class="card-content">
        <span class="badge badge-secondary">Jetpack Compose</span>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">🏗 アーキテクチャ</div>
      <div class="card-content">
        <span class="badge badge-accent">Clean Architecture</span>
        <span class="badge badge-accent">MVI</span>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">💾 データベース</div>
      <div class="card-content">
        <span class="badge badge-primary">Room</span>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">⚡ 非同期処理</div>
      <div class="card-content">
        <span class="badge badge-secondary">Coroutines</span>
        <span class="badge badge-secondary">Flow</span>
      </div>
    </div>
    
    <div class="card">
      <div class="card-title">🔧 依存性注入</div>
      <div class="card-content">
        <span class="badge badge-accent">Koin</span>
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
    <div class="card-title">👨‍💻 開発者</div>
    <div class="card-content">
      <p><strong>Segnities007</strong> - フルスタック開発</p>
      <a href="https://github.com/segnities007" class="btn btn-outline" target="_blank">GitHub Profile</a>
    </div>
  </div>
</div>

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