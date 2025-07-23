package com.segnities007.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.segnities007.model.ConsumableCategory
import com.segnities007.model.Expense
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(tableName = "expenses")
data class ExpenseEntity
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val hostId: String = "",
        val name: String = "",
        val amount: Int = 0,
        val categoriesJson: String = "{}", // JSON文字列として保存
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
                categories = jsonToCategories(categoriesJson),
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
                    categoriesJson = categoriesToJson(expense.categories),
                    createAtMillis = expense.createAt.toEpochMilliseconds(),
                    updateAtMillis = expense.updateAt.toEpochMilliseconds(),
                )

            private fun categoriesToJson(categories: Map<ConsumableCategory, Int>): String = Json.encodeToString(categories)

            private fun jsonToCategories(json: String): Map<ConsumableCategory, Int> =
                try {
                    Json.decodeFromString(json)
                } catch (e: Exception) {
                    emptyMap()
                }
        }
    }
