package com.danilkomyshev.byteshare.util

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.setVisibility(isVisible: Boolean) {
    if (isVisible) this.show() else this.hide()
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.toggle() {
    if (this.visibility == View.VISIBLE)
        this.hide()
    else
        this.show()
}

fun View.gone() {
    this.visibility = View.GONE
}

fun showViews(vararg views: View) {
    views.forEach { it.show() }
}

fun hideViews(vararg views: View) {
    views.forEach { it.hide() }
}

fun toggleViews(vararg views: View) {
    views.forEach { it.toggle() }
}
