package br.com.jwar.sharedbill.presentation.ui.screens.group_edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.domain.model.User

@Composable
fun GroupMemberCard(member: User, onMemberClick: (member: User) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        onClick = { onMemberClick(member) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = member.name)
        }
    }
}

