package br.com.jwar.sharedbill.account.presentation.ui.auth.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.jwar.sharedbill.account.presentation.R
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme

@Composable
fun SignInButton(
    onSignInClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .background(
                color = Color(0XFF4285F4),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onSignInClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp),
            painter = painterResource(R.drawable.btn_google),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = stringResource(R.string.label_sign_in_with_google),
            color = Color(0XFFFFFFFF)
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewSignInButton() {
    SharedBillTheme {
        Scaffold {
            SignInButton()
        }
    }
}