
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon

@Composable
fun FormTopBar(
    title: String = "",
    onNavigateBack: () -> Unit,
    onSaveClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    AppTopBar(
        navigationBack = onNavigateBack,
        navigationIcon = { CloseNavigationIcon(onNavigateBack) },
        title = title,
        actions = {
            IconButton(onClick = { onSaveClick(); keyboardController?.hide() }) {
                Icon(Icons.Filled.Done, stringResource(id = R.string.description_done))
            }
        }
    )
}