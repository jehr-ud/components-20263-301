package com.udistrital.hockeygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.udistrital.hockeygame.composables.GameScreen
import com.udistrital.hockeygame.composables.HomeScreen
import com.udistrital.hockeygame.enums.TypeScreen
import com.udistrital.hockeygame.ui.theme.HockeyGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HockeyGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentScreen by remember { mutableStateOf(TypeScreen.HOME) }

                    if (currentScreen == TypeScreen.HOME) {
                        HomeScreen() {
                            currentScreen = TypeScreen.GAME
                        }
                    } else if (currentScreen == TypeScreen.GAME) {
                        GameScreen(onBack = {
                            currentScreen = TypeScreen.HOME
                        })
                    }
                }
            }
        }
    }
}