package com.segnities007.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Storage
    @OptIn(ExperimentalTime::class)
    constructor(
        val id: String = "",
        val hostId: String = "",
        val name: String = "",
        val description: String = "",
        val itemIDs: List<Int> = emptyList(),
        val expenseIDs: List<Int> = emptyList(),
        val createAt: Instant = Clock.System.now(),
        val updateAt: Instant = Clock.System.now(),
        val editableUserIds: Map<String, Boolean> = emptyMap(),
    )
