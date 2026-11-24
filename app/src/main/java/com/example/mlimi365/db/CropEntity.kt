package com.example.mlimi365.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crops")
data class CropEntity(
    @PrimaryKey val id: String,
    val name: String,
    val emoji: String,
    val category: String,
    val plantingSeason: String,
    val plantingGuide: String,
    val spacing: String,
    val fertilizer: String,
    val weeding: String,
    val harvesting: String
)
