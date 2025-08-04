---
layout: default
title: Arumo-beta API Reference
nav_order: 1
---

# Arumo-beta API Reference

## 概要

Arumo-betaは消耗品管理アプリのAPIリファレンスです。このドキュメントでは、アプリケーション内で使用される独自のAPIインターフェースについて説明します。

## アーキテクチャ

このプロジェクトはClean Architectureパターンに従って設計されており、以下の層で構成されています：

- **Domain Layer**: ビジネスロジックとエンティティ
- **Data Layer**: データアクセスとリポジトリ実装
- **Presentation Layer**: UIとユーザーインタラクション

## クイックスタート

### 基本的な使用例

```kotlin
// アイテムの追加
val item = Item(
    name = "牛乳",
    amount = 200,
    count = 1,
    category = ConsumableCategory.FOOD
)
itemRepository.upsertItem(item)

// ストレージの作成
val storage = Storage(
    name = "冷蔵庫",
    description = "食品の保管場所",
    hostId = "user123"
)
storageRepository.createStorage(storage)
```

## ドキュメント構成

- [Repository Interfaces](repository-interfaces.md) - リポジトリインターフェースの詳細
- [Data Models](data-models.md) - データモデルの定義
- [Enums](enums.md) - 列挙型の説明
- [MVI Architecture](mvi-architecture.md) - アーキテクチャの詳細
- [Examples](examples.md) - 使用例とサンプルコード

## 技術スタック

- **言語**: Kotlin
- **アーキテクチャ**: Clean Architecture + MVI
- **データベース**: Room
- **UI**: Jetpack Compose
- **非同期処理**: Coroutines + Flow

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。 