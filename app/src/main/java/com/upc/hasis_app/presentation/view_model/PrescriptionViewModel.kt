package com.upc.hasis_app.presentation.view_model

import androidx.lifecycle.ViewModel
import com.upc.hasis_app.domain.usecase.PreferencesUseCase
import com.upc.hasis_app.domain.usecase.RecipeUseCase
import javax.inject.Inject

class PrescriptionViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    private val preferencesUseCase: PreferencesUseCase
)  : ViewModel() {




}