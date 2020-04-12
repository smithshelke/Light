package com.smith.light.ui.verses

import android.content.Context
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import com.smith.light.R
import com.smith.light.utils.px

class VersePopupWindow(private val context: Context) : PopupWindow(context) {

    private lateinit var onOptionClickListener: OnOptionClickListener

    enum class Options {
        HIGHLIGHT_TEXT,
        MARK_FAVOURITES
    }

    init {
        setupView()
    }

    private fun setupView() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_verse_popup, null)
        height = WindowManager.LayoutParams.WRAP_CONTENT
        width = WindowManager.LayoutParams.WRAP_CONTENT
        val background = MaterialShapeDrawable()
        background.apply {
            val popupCornerRadius = context.resources.getDimension(R.dimen.popupCorners)
            val shape = ShapeAppearanceModel().toBuilder().setAllCornerSizes(popupCornerRadius)
                .setTopEdge(TriangleEdgeTreatment(8.px.toFloat(), false)).build()
            shapeAppearanceModel = shape
            fillColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.darkGrey))
            alpha = 242
        }

        setBackgroundDrawable(background)

        elevation = context.resources.getDimension(R.dimen.popupElevation)
        isOutsideTouchable = true
        isFocusable = true
        contentView = view
    }

    fun show(anchor: View) {
        val location = IntArray(2)
        anchor.getLocationInWindow(location)
        showAtLocation(anchor, Gravity.END or Gravity.TOP, 16.px, location[1] + anchor.height)
    }

    fun setOnOptionClickListener(onOptionClickListener: OnOptionClickListener) {
        this.onOptionClickListener = onOptionClickListener
    }

    interface OnOptionClickListener {
        fun onOptionClick(options: Options)
    }
}
