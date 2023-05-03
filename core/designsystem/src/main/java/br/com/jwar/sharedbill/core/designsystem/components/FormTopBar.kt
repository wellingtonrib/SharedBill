
import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.jwar.sharedbill.core.designsystem.R
import br.com.jwar.sharedbill.core.designsystem.components.AppTopBar
import br.com.jwar.sharedbill.core.designsystem.components.CloseNavigationIcon
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun FormTopBar(
    title: String = "",
    onNavigateBack: () -> Unit = {},
    onSaveClick: () -> Unit = {},
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

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PreviewFormTopBar() {
    SharedBillTheme {
        Scaffold {
            FormTopBar(
                title = "Title",
            )
        }
    }
}