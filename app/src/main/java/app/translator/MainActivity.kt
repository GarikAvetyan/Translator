package app.translator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import app.translator.core_res.ui.base.BaseActivity
import app.translator.core_res.ui.theme.TranslatorTheme
import app.translator.presentation.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranslatorTheme(dynamicColor = false) {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}
