package com.anggit97.formbuilder

import android.widget.LinearLayout

class FormButton : FormObject() {
    var title: String? = null
    var backgroundColor: Int? = null
    var backgroundResource: Int? = null
    var textColor: Int? = null
    var runnable: Runnable? = null
    var params: LinearLayout.LayoutParams? = null

    fun setTitle(title: String?): FormButton {
        this.title = title
        return this
    }

    fun setBackgroundResource(backgroundResource: Int?): FormButton {
        this.backgroundResource = backgroundResource
        return this
    }

    fun setBackgroundColor(backgroundColor: Int?): FormButton {
        this.backgroundColor = backgroundColor
        return this
    }

    fun setTextColor(textColor: Int?): FormButton {
        this.textColor = textColor
        return this
    }

    fun setRunnable(runnable: Runnable?): FormButton {
        this.runnable = runnable
        return this
    }

    fun setParams(params: LinearLayout.LayoutParams?): FormButton {
        this.params = params
        return this
    }
}