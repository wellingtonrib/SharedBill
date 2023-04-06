package br.com.jwar.sharedbill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.navigation.NavGraph
import br.com.jwar.sharedbill.ui.AppBottomBar
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val snackbarHostState = remember { SnackbarHostState() }
            val navController = rememberAnimatedNavController(bottomSheetNavigator)
            SharedBillTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = { AppBottomBar(navController = navController) }
                ) {
                    NavGraph(navController = navController, snackbarHostState = snackbarHostState)
                }
            }
        }
    }
}