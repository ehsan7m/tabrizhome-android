package com.tabrizhome.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.LayoutDirection
import com.tabrizhome.app.ui.navigation.TabrizHomeNavHost
import com.tabrizhome.app.ui.theme.TabrizHomeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabrizHomeTheme {
                Scaffold { padding ->
                    TabrizHomeNavHost(
                        padding = padding,
                        layoutDirection = LayoutDirection.Rtl
                    )
                }
            }
        }
    }
}
