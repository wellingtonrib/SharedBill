package br.com.jwar.sharedbill.account.presentation.ui.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import br.com.jwar.sharedbill.account.presentation.R

@Composable
fun PrivacyPolicy(onPrivacyClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        val text = stringResource(R.string.message_privacy_police)
        val underlineText = text.split("\n").last()
        append(text)
        addStyle(
            style = SpanStyle(textDecoration = TextDecoration.Underline),
            start = text.indexOf(underlineText),
            end = text.indexOf(underlineText) + underlineText.length
        )
    }
    Text(
        modifier = Modifier.clickable { onPrivacyClick() },
        text = annotatedString, textAlign = TextAlign.Center
    )
}