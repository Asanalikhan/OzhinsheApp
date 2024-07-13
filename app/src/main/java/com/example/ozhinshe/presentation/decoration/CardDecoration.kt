package com.example.ozhinshe.presentation.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CardDecoration(
    private val leftOffSet: Int = 0,
    private val topOffset: Int = 0,
    private val rightOffset: Int = 0,
    private val bottomOffset: Int = 0
): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(leftOffSet, topOffset, rightOffset, bottomOffset)
    }
}