package br.com.jwar.sharedbill

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.jwar.sharedbill.MainContract.*
import br.com.jwar.sharedbill.account.presentation.navigation.AUTH_ROUTE
import br.com.jwar.sharedbill.core.designsystem.theme.SharedBillTheme
import br.com.jwar.sharedbill.navigation.NavGraph
import br.com.jwar.sharedbill.ui.AppBottomBar
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition { mainViewModel.uiState.value is State.Initializing }
        }
        setContent {
            val bottomSheetNavigator = rememberBottomSheetNavigator()
            val navController = rememberNavController(bottomSheetNavigator)
            val state = mainViewModel.uiState.collectAsState().value

            LaunchedEffect(Unit) {
                initializeIfNeeded(state)
                observeEffects(navController)
            }

            SharedBillTheme {
                Scaffold(
                    bottomBar = { AppBottomBar(navController = navController) }
                ) {
                    NavGraph(navController = navController)
                }
            }
        }
    }

    private fun initializeIfNeeded(state: State) {
        if (state !is State.Initialized) {
            mainViewModel.emitEvent { Event.OnInit }
        }
    }

    private suspend fun observeEffects(navController: NavHostController) {
        mainViewModel.uiEffect.collect { effect ->
            when (effect) {
                is Effect.NavigateToAuth -> navController.navigate(AUTH_ROUTE)
            }
        }
    }
}