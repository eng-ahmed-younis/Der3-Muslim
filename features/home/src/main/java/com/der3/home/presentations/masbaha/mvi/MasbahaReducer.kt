package com.der3.home.presentations.masbaha.mvi

import com.der3.mvi.Reducer
import javax.inject.Inject

class MasbahaReducer @Inject constructor() : Reducer<MasbahaAction, MasbahaState> {
    override fun reduce(action: MasbahaAction, state: MasbahaState): MasbahaState {
        return when (action) {
            is MasbahaAction.OnLoading -> state.copy(isLoading = action.isLoading)
            is MasbahaAction.OnAzkarsLoaded -> {
                val selected = state.selectedAzkar ?: action.azkars.firstOrNull()
                state.copy(
                    azkars = action.azkars,
                    selectedAzkar = selected,
                    targetCount = selected?.count ?: state.targetCount,
                    isLoading = false
                )
            }
            is MasbahaAction.OnError -> state.copy(
                error = action.message,
                isLoading = false
            )
            is MasbahaAction.UpdateCount -> {
                // 1. Increment the current counter
                var newCount = state.currentCount + 1
                var selected = state.selectedAzkar
                var target = state.targetCount

                // 2. Check if the target is reached. 
                // We skip this check if targetCount is -1 (Unlimited/Open session).
                if (state.targetCount != -1 && newCount > state.targetCount) {
                    
                    // 3. If "Auto Change" is enabled, move to the next Zekr in the list
                    if (state.autoSwitch) {
                        val currentIndex = state.azkars.indexOf(state.selectedAzkar)
                        val nextIndex = (currentIndex + 1) % state.azkars.size
                        
                        selected = state.azkars[nextIndex]
                        // Update target to the new Zekr's default count
                        target = selected.count ?: state.targetCount
                        // Reset counter for the new Zekr (starting from 1)
                        newCount = 1
                    } else {
                        // If Auto Change is OFF, cap the counter at the target value
                        newCount = state.targetCount
                    }
                }

                // 4. Return updated state with new count and potentially a new Zekr/Target
                state.copy(
                    currentCount = newCount,
                    selectedAzkar = selected,
                    targetCount = target
                )
            }
            is MasbahaAction.ResetCount -> {
                state.copy(currentCount = 0)
            }
            
            is MasbahaAction.SetSelectedAzkar -> state.copy(
                selectedAzkar = action.azkar,
                targetCount = action.azkar.count ?: state.targetCount,
                currentCount = 0
            )
            is MasbahaAction.UpdateTargetCount -> state.copy(targetCount = action.target)
            is MasbahaAction.UpdateAutoSwitch -> state.copy(autoSwitch = action.enabled)
            is MasbahaAction.UpdateVibration -> state.copy(isVibrationEnabled = action.enabled)
            is MasbahaAction.UpdateSound -> state.copy(isSoundEnabled = action.enabled)
        }
    }
}