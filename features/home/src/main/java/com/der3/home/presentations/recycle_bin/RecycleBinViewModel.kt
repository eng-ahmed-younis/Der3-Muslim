package com.der3.home.presentations.recycle_bin

import androidx.lifecycle.viewModelScope
import com.der3.home.domain.model.DeletedZekrUiModel
import com.der3.home.domain.model.ZekrUiModel
import com.der3.home.presentations.recycle_bin.mvi.RecycleBinAction
import com.der3.home.presentations.recycle_bin.mvi.RecycleBinIntent
import com.der3.home.presentations.recycle_bin.mvi.RecycleBinReducer
import com.der3.home.presentations.recycle_bin.mvi.RecycleBinState
import com.der3.mvi.MviBaseViewModel
import com.der3.mvi.MviEffect
import com.der3.screens.Screens
import com.der3.shared.data.source.local.entity.FavoriteEntity
import com.der3.shared.domain.use_case.fav.AddToFavoriteUseCase
import com.der3.shared.domain.use_case.recycler.ClearRecycleBinUseCase
import com.der3.shared.domain.use_case.recycler.DeleteRecyclerItemByIdUseCase
import com.der3.shared.domain.use_case.recycler.GetRecycleBinItemsUseCase
import com.der3.utils.TimeFormatUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecycleBinViewModel @Inject constructor(
    private val getAllRecycleBinItems: GetRecycleBinItemsUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val deleteRecyclerItemByIdUseCase: DeleteRecyclerItemByIdUseCase,
    private val clearRecycleBinUseCase: ClearRecycleBinUseCase
) : MviBaseViewModel<RecycleBinState, RecycleBinAction, RecycleBinIntent>(
    initialState = RecycleBinState(),
    reducer = RecycleBinReducer()
) {

    init {
        loadItems()
    }

    override fun handleIntent(intent: RecycleBinIntent) {
        when (intent) {
            is RecycleBinIntent.RestoreItem -> {
                restoreItem(intent.id)
            }

            is RecycleBinIntent.DeletePermanently -> {
                deletePermanently(intent.id)
            }

            is RecycleBinIntent.EmptyBin -> {
                emptyBin()
            }

            is RecycleBinIntent.Retry -> {
                loadItems()
            }

            is RecycleBinIntent.DismissError -> {
                onAction(RecycleBinAction.ClearError)
            }

            is RecycleBinIntent.Back -> {
                onEffect(MviEffect.Navigate(screen = Screens.Back()))
            }
        }
    }

    private fun loadItems() {

        getAllRecycleBinItems.invoke().onStart {
                onAction(RecycleBinAction.Loading(true))
            }.onEach { items ->
                onAction(RecycleBinAction.ItemsLoaded(items = items.map {
                    DeletedZekrUiModel(
                        zekr = ZekrUiModel(
                            id = it.id,
                            text = it.text,
                            categoryName = it.categoryName,
                            repeatCount = it.repeatCount,
                            audioPath = it.audioPath
                        ), deletedSince = TimeFormatUtils.getRelativeTimeSpanString(it.deletedAt)
                    )
                }))
            }.catch { e ->
                onAction(RecycleBinAction.Error(e.message ?: "Unknown error"))
            }.onCompletion {
                onAction(RecycleBinAction.Loading(false))
            }.launchIn(viewModelScope)
    }

    private fun restoreItem(id: Int) {
        // Logic to restore item
        viewState.items.find { it.zekr.id == id }?.let { item ->
            viewModelScope.launch {
                onAction(RecycleBinAction.Loading(true))
                try {
                    addToFavoriteUseCase.invoke(
                        FavoriteEntity(
                            id = item.zekr.id,
                            text = item.zekr.text,
                            audioPath = item.zekr.audioPath,
                            repeatCount = item.zekr.repeatCount,
                            categoryName = item.zekr.categoryName,
                            categoryId = item.zekr.categoryId ?: -1
                        )
                    )
                    deleteRecyclerItemByIdUseCase.invoke(id = id)
                    // No need to call loadItems() because getAllRecycleBinItems returns a Flow
                } catch (e: Exception) {
                    onAction(RecycleBinAction.Error(e.message ?: "Restore failed"))
                } finally {
                    onAction(RecycleBinAction.Loading(false))
                }
            }
        }
    }

    private fun deletePermanently(id: Int) {
        // Logic to delete permanently
    }

    private fun emptyBin() {
        viewModelScope.launch {
            onAction(RecycleBinAction.Loading(true))
            try {
                clearRecycleBinUseCase.invoke()
                // No need to call loadItems() because getAllRecycleBinItems returns a Flow
            } catch (e: Exception) {
                onAction(RecycleBinAction.Error(e.message ?: "Failed to empty recycle bin"))

            } finally {
                onAction(RecycleBinAction.Loading(false))
            }
        }
    }


}
