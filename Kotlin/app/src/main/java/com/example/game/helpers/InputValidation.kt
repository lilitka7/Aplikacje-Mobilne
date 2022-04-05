package com.example.game.helpers
import android.app.Activity
import android.content.Context

import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class InputValidation (private val context: Context) {
        
        fun isInputEditTextFilled(name: EditText, textInputLayout: EditText, message: String): Boolean {
            val value = name.text.toString().trim()
            if (value.isEmpty()) {
                textInputLayout.error = message
                hideKeyboardFrom(name)
                return false
            }
            return true
        }
        
        fun isInputEditTextMatches(textInputEditText1: EditText, textInputEditText2: EditText, textInputLayout: EditText, message: String): Boolean {
            val value1 = textInputEditText1.text.toString().trim()
            val value2 = textInputEditText2.text.toString().trim()
            if (!value1.contentEquals(value2)) {
                textInputLayout.error = message
                hideKeyboardFrom(textInputEditText2)
                return false
            }
            return true
        }
        
        private fun hideKeyboardFrom(view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }