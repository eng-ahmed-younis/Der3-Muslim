package com.der3.home.data.mappers

import com.der3.shared.domain.model.AzkarCategory
import com.der3.shared.domain.model.AzkarItem
import com.der3.shared.data.provider.ZekrCategoriesProvider
import com.der3.home.domain.model.ZekrUiModel
import com.der3.shared.data.source.local.entity.FavoriteEntity
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


fun AzkarCategory.toUiCategory(): CategoryUi? {    // Find the UI template matching this category ID
    val uiTemplate = ZekrCategoriesProvider.categories.find { it.id == this.id }
        ?: return null

    return uiTemplate.copy(
        title = this.category, // Map the Arabic title from JSON
        count = "${this.items.size} ذكراً" // Map the dynamic count
    )
}



fun AzkarCategory.toZekrUiModels(): List<ZekrUiModel>? {
    // If there are no items, we can return null or an empty list.
    // Given the return type is List<ZekrUiModel>?, we return null if empty.
    if (this.items.isEmpty()) return null

    return this.items.map { item ->
        ZekrUiModel(
            id = item.id,
            text = item.text,
            repeatCount = item.count,
            audioPath = item.audioPath
        )
    }
}

fun AzkarItem.toZekrUiModel(): ZekrUiModel {
    return ZekrUiModel(
        id = this.id,
        text = this.text,
        repeatCount = this.count,
        audioPath = this.audioPath
    )
}

fun FavoriteEntity.toZekrUiModel(): ZekrUiModel {
    return ZekrUiModel(
        id = this.id,
        text = this.text,
        category = this.categoryName,
        repeatCount = this.repeatCount,
        audioPath = this.audioPath,
        isFavorite = true
    )
}
