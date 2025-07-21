package com.segnities007.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String): List<String> = Json.decodeFromString(value)

    @TypeConverter
    fun fromStringBooleanMap(value: Map<String, Boolean>): String = Json.encodeToString(value)

    @TypeConverter
    fun toStringBooleanMap(value: String): Map<String, Boolean> = Json.decodeFromString(value)

    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun fromInstant(value: Instant): Long = value.toEpochMilliseconds()

    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun toInstant(value: Long): Instant = Instant.fromEpochMilliseconds(value)
}