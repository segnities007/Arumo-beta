package com.segnities007.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Sanitizer
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.ui.graphics.vector.ImageVector

enum class ConsumableCategory(
    val colorCode: String,
    val icon: ImageVector,
) {
    FOOD("#FF6347", Icons.Default.Fastfood),
    BEVERAGE("#1E90FF", Icons.Default.LocalDrink),
    DAILY_NECESSITIES("#32CD32", Icons.Default.ShoppingBasket),
    COSMETICS_BEAUTY("#DA70D6", Icons.Default.Sanitizer),
    PHARMACEUTICALS("#008080", Icons.Default.MedicalServices),
    KITCHEN_SUPPLIES("#FFD700", Icons.Default.Kitchen),
    CLEANING_LAUNDRY("#00CED1", Icons.Default.CleaningServices),
    BABY("#ADD8E6", Icons.Default.ChildCare),
    PETS("#D2B48C", Icons.Default.Pets),
    STATIONERY_GOODS("#4682B4", Icons.Default.Edit),
    UNCATEGORIZED("#A9A9A9", Icons.Default.QuestionMark),
}
