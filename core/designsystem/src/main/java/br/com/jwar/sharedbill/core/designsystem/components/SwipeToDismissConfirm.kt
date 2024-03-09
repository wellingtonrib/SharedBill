package br.com.jwar.sharedbill.core.designsystem.components

import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.core.designsystem.R
import kotlinx.coroutines.launch

@Composable
fun swipeToDismissConfirm(
    title: String = stringResource(R.string.label_confirm),
    text: String = stringResource(R.string.message_confirm_action),
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
): DismissState {
    val scope = rememberCoroutineScope()
    val confirmState = remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                confirmState.value = true
            }
            true
        }
    )

    if (confirmState.value) {
        fun dismiss() {
            onDismiss()
            scope.launch { dismissState.reset() }
            confirmState.value = false
        }

        AlertDialog(
            onDismissRequest = { dismiss() },
            title = { Text(title) },
            text = { Text(text) },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        dismiss()
                    }
                ) {
                    Text(stringResource(R.string.label_yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = { dismiss() }
                ) {
                    Text(stringResource(R.string.label_cancel))
                }
            }
        )
    }
    return dismissState
}
