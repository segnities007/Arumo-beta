package com.segnities007.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Expense
    @OptIn(ExperimentalTime::class)
    constructor(
        val id: Int = 0,
        val hostId: String = "",
        val name: String = "",
        val amount: Int = 0,
        val categories: Map<ConsumableCategory, Int> = emptyMap(),
        val createAt: Instant = Clock.System.now(),
        val updateAt: Instant = Clock.System.now(),
    )
