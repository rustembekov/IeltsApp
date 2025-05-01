package com.example.support.core.di

import androidx.lifecycle.ViewModel
import com.example.support.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavHolder @Inject constructor(
    val navigator: Navigator
) : ViewModel()
