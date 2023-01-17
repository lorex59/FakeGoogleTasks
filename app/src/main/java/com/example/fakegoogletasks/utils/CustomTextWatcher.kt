package com.example.fakegoogletasks.utils

import android.text.Editable
import android.text.TextWatcher


class CustomTextWatcher(
    var edit: (String, String) -> Unit,
    var field: String,
    var editField: String
) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        edit(field, field)
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}