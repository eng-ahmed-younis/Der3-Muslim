package com.der3.sections.presentation.main_section.model

import androidx.compose.ui.graphics.vector.ImageVector


data class Section(
    val id: String,
    val type: SectionType,
    val title: String,
    val subTitle: String,
    val icon: ImageVector
)
