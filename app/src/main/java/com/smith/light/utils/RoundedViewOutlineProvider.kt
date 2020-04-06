package com.smith.light.utils

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.smith.light.R

private const val TAG = "RoundedViewOutlineProvi"
class RoundedViewOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        val radius = view!!.resources.getDimension(R.dimen.borderRadius)
        outline?.setRoundRect(
            0,
            0,
            view.width,
            view.height,
            radius
        )
    }
}
