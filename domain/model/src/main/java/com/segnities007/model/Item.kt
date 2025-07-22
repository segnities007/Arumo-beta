package com.segnities007.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(tableName = "items")
data class Item
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey(autoGenerate = true)val id: Int = 0,
        val name: String = "",
        val amount: Int = 0,
        val count: Int = 0,
        val category: ConsumableCategory = ConsumableCategory.UNCATEGORIZED,
        val createAt: Instant = Clock.System.now(),
        val updateAt: Instant = Clock.System.now(),
    )
