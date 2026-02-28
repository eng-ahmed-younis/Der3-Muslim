package com.der3.home.presentations.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.der3.data.provider.ZekrCategoriesProvider
import com.der3.ui.models.CategoryUi
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun CategoriesGrid(
    modifier: Modifier = Modifier,
    categories: List<CategoryUi>
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        userScrollEnabled = true,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            bottom = 8.dp
        ),
        modifier = modifier.height(600.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(categories.take(6)) { index, category ->
            CategoryCard(category = category)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CategoriesGridPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            CategoriesGrid(
                categories = ZekrCategoriesProvider.categories
            )
        }
    }
}

