# Arumo-beta

レシートを用いて消耗品を管理する多機能アプリ「Arumo-beta」のonly-Android版

## 📚 API Documentation

このプロジェクトのAPIリファレンスは以下の方法で閲覧できます：

### 🌐 GitHub Pages（推奨）

美しいドキュメントサイトとして公開されています：
- **URL**: `https://segnities007.github.io/Arumobeta/`
- **自動更新**: リポジトリにプッシュすると自動的に更新されます

### 📖 ローカルドキュメント

- [API Reference](API_REFERENCE.md) - 包括的なAPIリファレンス
- [GitHub Pages ドキュメント](docs/) - サイト用ドキュメント

## 🚀 GitHub Pages の設定

### 1. リポジトリ設定でGitHub Pagesを有効化

1. リポジトリの **Settings** タブに移動
2. 左サイドバーの **Pages** をクリック
3. **Source** セクションで以下を設定：
   - **Source**: `Deploy from a branch`
   - **Branch**: `main` または `master`
   - **Folder**: `/ (root)`
4. **Save** をクリック

### 2. ドキュメントの自動生成

```bash
# ドキュメントサイトの構築
cd docs
bundle install
bundle exec jekyll serve
```

### 3. カスタムドメイン（オプション）

カスタムドメインを使用する場合：
1. **Custom domain** フィールドにドメインを入力
2. **Enforce HTTPS** にチェック
3. **Save** をクリック

## 📁 プロジェクト構造

```
Arumobeta/
├── docs/                    # GitHub Pages用ドキュメント
│   ├── _config.yml         # Jekyll設定
│   ├── index.md            # メインページ
│   └── repository-interfaces.md
├── API_REFERENCE.md        # 包括的APIリファレンス
├── app/                    # メインアプリモジュール
├── core/                   # 共通モジュール
├── data/                   # データ層
├── domain/                 # ドメイン層
└── feature/                # 機能モジュール
```

## 🛠 技術スタック

- **言語**: Kotlin
- **フレームワーク**: Jetpack Compose
- **アーキテクチャ**: Clean Architecture + MVI
- **データベース**: Room
- **非同期処理**: Coroutines + Flow
- **依存性注入**: Koin
- **画像処理**: Coil
- **バックエンド**: Supabase

## 📱 機能

- 認証機能（サインアップ/サインイン）
- レシート読み込み（カメラ機能）
- レシート解析（Gemini API）
- 在庫管理（ローカル/サーバー保存）
- 通知機能（在庫消費・賞味期限リマインダー）
- 在庫情報共有管理
- 出費確認・グラフ表示

## 🏗 モジュール構成

```
├── :data
│   ├── :local
│   ├── :remote
│   └── :repository
├── :domain
│   ├── :model
│   ├── :usecase
│   └── :repository
├── :feature
│   ├── :home
│   ├── :login
│   ├── :setting
│   ├── :search
│   ├── :storage
│   └── :dashboard
└── :core
    ├── :ui
    ├── :navigation
    └── :common
```

## 📋 Model

- **User**: ユーザー情報
- **Item**: 消耗品
- **Storage**: 保管場所
- **Expense**: 出費
- **ConsumableCategory**: 消耗品カテゴリ

## 🎯 画面構成

- **認証**: サインイン/サインアップ/パスワードリセット
- **ハブ**: ホーム/検索/出費確認/プロフィール
- **在庫管理**: 在庫一覧/アイテム編集/在庫追加/通知設定

## 📄 ライセンス

このプロジェクトはMITライセンスの下で公開されています。

## 👨‍💻 開発者

- **Segnities007**: フルスタック開発

---

## 📖 ドキュメント関連

### APIリファレンスの更新

1. `API_REFERENCE.md` を編集
2. `docs/` フォルダ内の対応ファイルも更新
3. プッシュするとGitHub Pagesが自動更新

### ローカルでのドキュメント確認

```bash
# Jekyllのインストール（初回のみ）
gem install jekyll bundler

# ドキュメントサイトの起動
cd docs
bundle install
bundle exec jekyll serve

# ブラウザで http://localhost:4000 にアクセス
```

### ドキュメントの構造

```
docs/
├── _config.yml              # Jekyll設定
├── index.md                 # メインページ
├── repository-interfaces.md # リポジトリインターフェース
├── data-models.md          # データモデル
├── enums.md                # 列挙型
├── mvi-architecture.md     # MVIアーキテクチャ
└── examples.md             # 使用例
```