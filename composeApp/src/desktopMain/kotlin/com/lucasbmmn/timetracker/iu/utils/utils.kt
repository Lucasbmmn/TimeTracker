package com.lucasbmmn.timetracker.iu.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

fun mnemonicUnderline(label: String, mnemonic: Char?): AnnotatedString  {
    return buildAnnotatedString {
        var underlined = false
        label.forEach { char ->
            if (!underlined && mnemonic != null && char.equals(mnemonic, ignoreCase = true)) {
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(char)
                }
                underlined = true
            } else append(char)
        }
    }
}