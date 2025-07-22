package com.segnities007.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(tableName = "expenses")
data class Expense
    @OptIn(ExperimentalTime::class)
    constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val hostId: String = "",
        val name: String = "",
        val amount: Int = 0,
        val categories: Map<ConsumableCategory, Int> = emptyMap(),
        val createAt: Instant = Clock.System.now(),
        val updateAt: Instant = Clock.System.now(),
    )
