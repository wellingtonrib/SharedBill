package br.com.jwar.sharedbill.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import br.com.jwar.sharedbill.presentation.navigation.AppBottomBar
import br.com.jwar.sharedbill.presentation.navigation.NavGraph
import br.com.jwar.sharedbill.presentation.ui.theme.SharedBillTheme
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