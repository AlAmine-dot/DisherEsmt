package com.example.esmt.cours.disher.core.util

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun EmojiView(
    emoji: String,
) {
    AndroidView(
        factory = { context ->
            AppCompatTextView(context).apply {
                text = emoji ?: "😟"
                textSize = 48.0F
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        },
        update = {
            it.apply {
                text = emoji ?: "😟"
            }
        },
    )
}