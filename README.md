# レシートを用いて消耗品を管理する多機能アプリ「Arumo-beta」

- レシート×消耗品管理アプリのonly-Android版

## メンバー

- Segnities007: フルスタック

## 技術スタック

- クライアント
    - 言語
        - Kotlin
    - フレームワーク等
        - Jetpack Compose
        - Compose Multiplatform
    - ライブラリ等
        - Room
        - Coil
        - Supabase-kt
        - Koin
    - アーキテクチャ
        - MVI
        - Clean Architecture
- サーバーサイド
    - Supabase

## 機能

- 認証機能
    - サインアップ / サインイン（メール + パスワード）
    - OAuthログイン（例: Google）
    - パスワードリセット
- User機能 (CRUD)
- カメラ機能
    - レシート読み込み
    - 手動読み込み
- レシート解析機能（Geminiによる自動解析 + 手動解析）
- 在庫管理機能
    - ローカル保存（無料ユーザー）
    - サーバ保存（有料ユーザー）
    - レシートからの自動登録
    - 手動登録/編集/削除
    - 在庫の期限管理（賞味期限など）
    - Storage（保管場所）単位で管理
- 通知機能
    - ローカル通知による在庫消費や賞味期限のリマインド
- 在庫情報共有管理機能
    - Storage単位で共有
    - 編集権限あり/なしの切り替え可能

## 画面

- 認証
    - サインイン画面
    - サインアップ
        - ログイン情報入力（email, password）
        - アカウント情報入力（名前、アイコンなど）
    - パスワードリセット画面
- ハブ
    - ホーム画面(Home)
        - 買い物リスト
        - 少ないものリスト
        - Fab（物追加：カメラ/手動選択）
    - 検索画面(Search)
        - カテゴリ別検索
        - 名前部分一致検索
    - 出費確認画面(Dashboard)
        - 今月の出費グラフ
        - 年間グラフ
        - 過去十年グラフ
    - プロフィール画面(Profile)
        - アカウント更新・削除
        - 設定変更
- 在庫管理関連
    - 在庫一覧（Storage単位）
    - アイテム編集・削除
    - 在庫追加（カメラ or 手動）
    - 通知設定画面

## Model

- User
    - id: uuid
    - name: String
    - email: String
    - password（hashed）
    - iconUrl
    - isPrime
    - setting
- Item（消耗品）
    - id: Long
    - category
    - name
    - amount
    - count
    - bestBefore（任意: 賞味期限）
    - createdAt
    - updatedAt
    - storageId
- Category（enum）
- Expense
    - id: String
    - hostId: String
    - name: String
    - amount: Int
    - categories: Map<ConsumableCategory, Int>
    - createAt: Instant
    - updateAt: Instant
- Storage（保管場所）
    - id: uuid
    - host_id: String
    - name: String
    - items: List<Item>
    - memberIDs: List<String>
    - editableMap: Map<String, Boolean>（ユーザーID → 編集権限）

---

## モジュール構成

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

---

## その他・補足

- **アカウントUD**…アカウント更新（Update）、削除（Delete）の略
- **ローカル通知**を使用した在庫消費・賞味期限リマインダー
- **Storage単位**での在庫管理・共有
    - ユーザーごとの編集権限も設定可能
- **Gemini API**を用いたレシートのOCRおよび品目解析
- **有料/無料**の違い：
    - 有料は在庫をサーバ保存可・共有可・通知拡張あり
    - 無料はローカル保存＋通知制限あり

