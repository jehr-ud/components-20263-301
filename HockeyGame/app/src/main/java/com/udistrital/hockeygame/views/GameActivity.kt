package com.udistrital.hockeygame.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.udistrital.hockeygame.views.screens.GameScreen
import com.udistrital.hockeygame.views.screens.HomeScreen
import com.udistrital.hockeygame.enums.TypeScreen
import com.udistrital.hockeygame.utils.CodeGenerator
import com.udistrital.hockeygame.views.screens.LobbyScreen
import com.udistrital.hockeygame.views.screens.RoomSelectionScreen
import com.udistrital.hockeygame.views.theme.HockeyGameTheme

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HockeyGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentScreen by remember { mutableStateOf(TypeScreen.HOME) }
                    var currentRoomCode by remember { mutableStateOf("") }
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        when (currentScreen) {
                            TypeScreen.HOME -> {
                                HomeScreen(
                                    onClickGame =  {
                                        currentScreen = TypeScreen.GAME
                                    },
                                    onClickMultiplayer = {
                                        currentScreen = TypeScreen.ROOM_SELECTION
                                    }
                                )
                            }

                            TypeScreen.ROOM_SELECTION -> {
                                RoomSelectionScreen(
                                    onCreateRoomClick = {
                                        currentRoomCode = CodeGenerator.generateRoomCode()
                                        currentScreen = TypeScreen.LOBBY
                                    },
                                    onJoinRoomClick = { roomCode ->
                                        currentRoomCode = roomCode
                                        currentScreen = TypeScreen.LOBBY
                                    }

                                )
                            }

                            TypeScreen.LOBBY -> {
                                LobbyScreen(
                                    roomCode = currentRoomCode,
                                    onCancelClick = {
                                        currentScreen = TypeScreen.ROOM_SELECTION
                                    },
                                    onStartGameClick = {
                                        currentScreen = TypeScreen.GAME
                                    }
                                )
                            }

                            TypeScreen.GAME -> {
                                GameScreen {
                                    currentScreen = TypeScreen.HOME
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}