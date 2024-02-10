package br.com.jwar.sharedbill.groups.presentation.ui.group_list.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.core.designsystem.theme.paddingMedium
import br.com.jwar.sharedbill.groups.presentation.R

@Composable
fun NewGroupBottomSheet(
    onGroupCreate: () -> Unit = {},
    onGroupJoin: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .heightIn(min = 200.dp, max = 200.dp)
            .paddingMedium()
            .padding(bottom = 120.dp)
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