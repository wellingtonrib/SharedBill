package br.com.jwar.sharedbill.presentation.ui.generic_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.R
import br.com.jwar.sharedbill.presentation.ui.theme.AppTheme
import br.com.jwar.sharedbill.presentation.ui.theme.HorizontalSpacerSmall

@Composable
fun SwipeToDismissBackground(
    action: SwipeToDismissAction,
    dismissState: DismissState
) {
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
    )
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primary, MaterialTheme.shapes.medium),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = stringResource(id = action.textRes), color = AppTheme.colors.onPrimary)
        HorizontalSpacerSmall()
        Icon(
            imageVector = action.iconRes,
            contentDescription =stringResource(id = action.descriptionRes),
            modifier = Modifier.scale(scale),
            tint = AppTheme.colors.onPrimary
        )
        HorizontalSpacerSmall()
    }
}

interface SwipeToDismissAction {
    val textRes: Int
    val iconRes: ImageVector
    val descriptionRes: Int
    val action: () -> Unit
}

class SwipeToDismissDeleteAction(
    override val textRes: Int = R.string.label_delete,
    override val iconRes: ImageVector = Icons.Default.Delete,
    override val descriptionRes: Int = R.string.description_delete,
    override val action: () -> Unit = {}
): SwipeToDismissAction

class SwipeToDismissLeaveAction(
    override val textRes: Int = R.string.label_leave,
    override val iconRes: ImageVector = Icons.Default.ExitToApp,
    override val descriptionRes: Int = R.string.description_leave,
    override val action: () -> Unit = {}
): SwipeToDismissAction