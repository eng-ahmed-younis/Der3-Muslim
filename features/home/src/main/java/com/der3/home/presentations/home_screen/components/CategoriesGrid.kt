package com.der3.home.presentations.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.der3.home.domain.ZekrCategory
import com.der3.home.domain.zekrCategories
import com.der3.ui.themes.Der3MuslimTheme
import java.util.Locale

@Composable
fun CategoriesGrid(
    modifier: Modifier = Modifier,
    categories: List<ZekrCategory>
) {

    LazyVerticalGrid(
        columns = GridCells
            .Fixed(2),
        userScrollEnabled = true,
        modifier = modifier
            .height(600.dp),
           // .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(categories) {index, category ->
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
                categories = zekrCategories
            )
        }
    }
}

