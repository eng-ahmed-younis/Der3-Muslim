package com.der3.home.presentations.all_categories

import Der3TopAppBar
import LoadingDialog
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.der3.ui.themes.Der3MuslimTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.der3.data.provider.ZekrCategoriesProvider
import com.der3.home.presentations.all_categories.components.CategoryRow
import com.der3.home.presentations.all_categories.mvi.AllCategoryIntent
import com.der3.home.presentations.all_categories.mvi.AllCategoryState
import com.der3.home.presentations.custom_reminder.mvi.AddCustomReminderIntent
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.ui.R
import com.der3.ui.components.ReminderNameField
import com.der3.ui.style.ShiftSystemBarStyle
import com.der3.ui.themes.AppColors
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@Composable
fun AllCategoriesRoute(
    onNavigate: (Screens) -> Unit = {}
) {

    val viewModel = hiltViewModel<AllCategoryViewModel>()
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.effects.onEach {
            when (it) {
                is MviEffect.Navigate -> onNavigate(it.screen)

            }
        }.launchIn(scope)
    }

    ShiftSystemBarStyle(
        statusBarColor = AppColors.gray50,
        isStatusBarVisible = true,
        useDarkStatusBarIcons = true,
        isEdgeToEdgeEnabled = true,
        isNavigationBarVisible = false,
        navigationBarColor = AppColors.gray50,
        useDarkNavigationBarIcons = false
    )

    AllCategoryScreen(
        state = viewModel.viewState,
        onNavigate = onNavigate,
        onIntent = viewModel::onIntent
    )


}


@OptIn(FlowPreview::class)
@Composable
fun AllCategoryScreen(
    state: AllCategoryState,
    onNavigate: (Screens) -> Unit = {},
    onIntent: (AllCategoryIntent) -> Unit = {}
) {


    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = searchText) {
        snapshotFlow { searchText }
            .debounce(300)
            .distinctUntilChanged()
            .collect {
                onIntent(AllCategoryIntent.UpdateSearchQuery(it))
            }
    }

    LoadingDialog(visible = state.isLoading)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.gray50)
    ) {
        Der3TopAppBar(
            title = stringResource(id = R.string.home_categories_title),
            backgroundColor = AppColors.gray50,
            titleColor = AppColors.gray900Text,
            navigationIconColor = AppColors.gray900Text,
            actionIconColor = AppColors.green800,
            onBackClick = {
                onNavigate(Screens.Back())
            }
        )

        ReminderNameField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            label = stringResource(id = R.string.reminder_name_hint),
            value = searchText,
            iconAction = Icons.Default.Search,
            borderColor = AppColors.gray500,
            backgroundColor = AppColors.white,
            labelColor = AppColors.gray500,
            iconActionColor = AppColors.gray500,
            onValueChange = {
                Log.d("AllCategoryScreen", "onValueChange: $it")
                searchText = it
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = (state.categories.size * 100).dp,
                    max = ((state.categories.size + 3) * 100).dp
                )
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(state.categories){ index , category ->
                CategoryRow(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    category = state.categories[index],
                    onClick = {
                       // onIntent(AllCategoryIntent.SelectCategory(category))
                    }
                )
            }
        }


     /*   SectionLabel(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.reminder_name_label)
        )*/

    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AllCategoryScreenPreview() {
    Der3MuslimTheme(
        language = Locale.Builder().setLanguage("ar").build()
    ) {
        AllCategoryScreen(
            state = AllCategoryState(
                isLoading = true,
                categories = ZekrCategoriesProvider.categories
            ),
            onIntent = {}
        )
    }
}