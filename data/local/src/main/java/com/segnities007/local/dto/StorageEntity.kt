package com.segnities007.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.segnities007.model.Storage
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Entity(tableName = "storages")
data class StorageEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val id: String = "",
    val hostId: String = "",
    val name: String = "",
    val description: String = "",
    val itemIDsJson: String = "[]",         // JSON文字列で保存
    val expenseIDsJson: String = "[]",      // JSON文字列で保存
    val editableUserIdsJson: String = "{}", // JSON文字列で保存
    val createAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
    val updateAtMillis: Long = Clock.System.now().toEpochMilliseconds(),
) {
    @OptIn(ExperimentalTime::class)
    fun toModel(): Storage = Storage(
        id = id,
        hostId = hostId,
        name = name,
        description = description,
        itemIDs = jsonToIntList(itemIDsJson),
        expenseIDs = jsonToIntList(expenseIDsJson),
        editableUserIds = jsonToEditableUserIds(editableUserIdsJson),
        createAt = Instant.fromEpochMilliseconds(createAtMillis),
        updateAt = Instant.fromEpochMilliseconds(updateAtMillis),
    )

    companion object {
        @OptIn(ExperimentalTime::class)
        fun fromModel(storage: Storage): StorageEntity = StorageEntity(
            id = storage.id,
            hostId = storage.hostId,
            name = storage.name,
            description = storage.description,
            itemIDsJson = intListToJson(storage.itemIDs),
            expenseIDsJson = intListToJson(storage.expenseIDs),
            editableUserIdsJson = editableUserIdsToJson(storage.editableUserIds),
            createAtMillis = storage.createAt.toEpochMilliseconds(),
            updateAtMillis = storage.updateAt.toEpochMilliseconds(),
        )

        private fun intListToJson(list: List<Int>): String =
            Json.encodeToString(list)

        private fun jsonToIntList(json: String): List<Int> =
            try {
                Json.decodeFromString(json)
            } catch (e: Exception) {
                emptyList()
            }

        private fun editableUserIdsToJson(map: Map<String, Boolean>): String =
            Json.encodeToString(map)

        private fun jsonToEditableUserIds(json: String): Map<String, Boolean> =
            try {
                Json.decodeFromString(json)
            } catch (e: Exception) {
                emptyMap()
            }
    }
}