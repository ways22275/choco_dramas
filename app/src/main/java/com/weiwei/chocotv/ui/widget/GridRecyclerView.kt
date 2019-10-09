package com.weiwei.chocotv.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.GridLayoutAnimationController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GridRecyclerView : RecyclerView {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    override fun attachLayoutAnimationParameters(
        child: View,
        params: ViewGroup.LayoutParams,
        index: Int,
        count: Int
    ) {

        if (adapter != null && layoutManager is GridLayoutManager) {

            val animationParams = GridLayoutAnimationController.AnimationParameters()
            params.layoutAnimationParameters = animationParams

            val columns = (layoutManager as GridLayoutManager).spanCount

            animationParams.count = count
            animationParams.index = index
            animationParams.columnsCount = columns
            animationParams.rowsCount = count / columns

            val invertedIndex = count - 1 - index
            animationParams.column = columns - 1 - invertedIndex % columns
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns

        } else {
            super.attachLayoutAnimationParameters(child, params, index, count)
        }
    }
}