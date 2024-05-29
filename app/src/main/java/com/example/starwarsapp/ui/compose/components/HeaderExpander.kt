package com.example.starwarsapp.ui.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable

@Composable
fun HeaderExpander(
    isVisible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically (animationSpec = tween(durationMillis = 1000))
    ) {
        content()
    }
}