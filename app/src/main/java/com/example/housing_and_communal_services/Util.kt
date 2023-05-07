package com.example.housing_and_communal_services

import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun showBars(flag:Boolean) {
    rememberSystemUiController().apply {
        this.isStatusBarVisible = flag
        this.isNavigationBarVisible = flag
        this.isStatusBarVisible = flag
    }
}