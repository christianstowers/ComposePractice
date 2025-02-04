package com.example.composepractice

import androidx.compose.runtime.compositionLocalOf

val LocalViewModel = compositionLocalOf<MyViewModel> {
    error("No ViewModel provided")
}