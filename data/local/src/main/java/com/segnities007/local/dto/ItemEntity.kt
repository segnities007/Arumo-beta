package com.segnities007.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.segnities007.model.ConsumableCategory
import com.segnities007.model.Item
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(tableName = "items")
data class ItemEntity @OptIn(ExperimentalTime::class) constructor(
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
    fun toModel(): Item = Item(
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
        fun fromModel(item: Item): ItemEntity = ItemEntity(
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