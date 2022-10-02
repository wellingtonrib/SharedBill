package br.com.jwar.sharedbill.presentation.ui.screens.group_create.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.presentation.ui.screens.group_create.GroupEditContract.State
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
import br.com.jwar.sharedbill.presentation.ui.widgets.LoadingContent

@Composable
fun GroupEditContent(
    state: State,
    snackHostState: SnackbarHostState = SnackbarHostState(),
    onCreateGroupClick: (name: String) -> Unit = {}
) {
    when (state) {
        is State.Saving -> LoadingContent()
        is State.Idle -> GroupEditForm(onCreateGroupClick)
    }
    SnackbarHost(
        hostState = snackHostState,
        modifier = Modifier.fillMaxWidth().wrapContentHeight(Alignment.Bottom)
    )
}


@Composable
fun GroupEditForm(
    onCreateGroupClick: (name: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        var groupName by remember { mutableStateOf(TextFieldValue("")) }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            value = groupName,
            label = { Text(text = "Enter the group name") },
            onValueChange = { groupName = it }
        )

        Button(onClick = { onCreateGroupClick(groupName.text) }) {
            Text(text = "Create Group")
        }
    }
}

@Preview
@Composable
fun previewGroupEditContent() {
    SharedBillTheme {
        Scaffold {
            GroupEditContent(state = State.Idle)
        }
    }
}