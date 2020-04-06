package com.smith.light.utils

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.smith.light.R

class RectangleViewOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View?, outline: Outline?) {
        outline?.setRect(
            0,
            0,
            view!!.width,
            view.height
        )
    }
}
