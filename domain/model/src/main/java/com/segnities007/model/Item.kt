package com.segnities007.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Item
    @OptIn(ExperimentalTime::class)
    constructor(
        val id: Int = 0,
        val name: String = "",
        val amount: Int = 0,
        val count: Int = 0,
        val category: ConsumableCategory = ConsumableCategory.UNCATEGORIZED,
        val createAt: Instant = Clock.System.now(),
        val updateAt: Instant = Clock.System.now(),
    )
