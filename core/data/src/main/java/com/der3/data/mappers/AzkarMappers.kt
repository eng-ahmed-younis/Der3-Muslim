package com.der3.data.mappers

import com.der3.data.model.AzkarCategory
import com.der3.data.provider.ZekrCategoriesProvider
import com.der3.ui.models.CategoryUi



fun List<AzkarCategory>.toUiCategories(): List<CategoryUi> {
    // Create a map of category IDs to CategoryUi objects
    val uiMap: Map<Int, CategoryUi> = ZekrCategoriesProvider.categories.associateBy { it.id }

    return mapNotNull { jsonCategory ->
        val uiCategory = uiMap[jsonCategory.id] ?: return@mapNotNull null
        uiCategory.copy(
            title = jsonCategory.category,  // ← real title from JSON
            count = "${jsonCategory.items.size} ذكراً"  // ← real count from JSON
        )
    }
}