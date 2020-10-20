package com.zelyder.wordsdefinition.utilities

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.zelyder.wordsdefinition.ui.adapters.InputFieldListener

@BindingAdapter("actionListener")
fun EditText.setActionListener(listener: InputFieldListener) {
    this.setOnEditorActionListener { textView, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            listener.clickListener(textView.text.toString())
            false
        } else {
            false
        }
    }
}
