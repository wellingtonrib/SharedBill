package br.com.jwar.sharedbill.groups.presentation.ui.list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun NewGroupBottomSheet(
    modifier: Modifier = Modifier,
    onGroupCreate: () -> Unit = {},
    onGroupJoin: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .wrapContentHeight(Alignment.Top)
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        Button(onClick = { onGroupCreate() }) {
            Text(text = stringResource(id = R.string.label_group_create))
        }
        Button(onClick = { onGroupJoin() }) {
            Text(text = stringResource(id = R.string.label_group_join))
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewNewGroupBottomSheet() {
    SharedBillTheme {
        Scaffold {
            NewGroupBottomSheet()
        }
    }
}
