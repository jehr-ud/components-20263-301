package com.udistrital.hockeygame.composables

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun GameScreen() {

    val context = LocalContext.current

    var gyroX by remember { mutableFloatStateOf(0f) }
    var gyroY by remember { mutableFloatStateOf(0f) }
    var gyroZ by remember { mutableFloatStateOf(0f) }

    var accelX by remember { mutableFloatStateOf(0f) }
    var accelY by remember { mutableFloatStateOf(0f) }
    var accelZ by remember { mutableFloatStateOf(0f) }

    // Ball state
    var ballX by remember { mutableFloatStateOf(500f) }
    var ballY by remember { mutableFloatStateOf(500f) }
    var ballVx by remember { mutableFloatStateOf(15f) }
    var ballVy by remember { mutableFloatStateOf(15f) }
    val ballRadius = 40f

    // Screen dimensions
    var screenWidth by remember { mutableFloatStateOf(0f) }
    var screenHeight by remember { mutableFloatStateOf(0f) }

    val sensorManager = remember {
        context.getSystemService(SensorManager::class.java)
    }

    DisposableEffect(sensorManager) {

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {

                when (event.sensor.type) {

                    Sensor.TYPE_GYROSCOPE -> {
                        gyroX = event.values[0]
                        gyroY = event.values[1]
                        gyroZ = event.values[2]
                    }

                    Sensor.TYPE_ACCELEROMETER -> {
                        accelX = event.values[0]
                        accelY = event.values[1]
                        accelZ = event.values[2]
                    }
                }
            }

            override fun onAccuracyChanged(
                sensor: Sensor?,
                accuracy: Int
            ) {
                // TODO: check this
            }
        }

        sensorManager.registerListener(
            listener,
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            SensorManager.SENSOR_DELAY_GAME
        )

        sensorManager.registerListener(
            listener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_GAME
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    var playerX by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(accelX) {
        playerX += -accelX * 5f
    }

    // Constants for positions
    val playerRadius = 80f
    val enemyRadius = 80f

    // Game loop for ball physics
    LaunchedEffect(screenWidth, screenHeight) {
        if (screenWidth > 0 && screenHeight > 0) {
            while (true) {
                // Move ball
                ballX += ballVx
                ballY += ballVy

                // 1. Boundary Collisions
                if (ballX <= ballRadius) {
                    ballX = ballRadius
                    ballVx *= -1
                } else if (ballX >= screenWidth - ballRadius) {
                    ballX = screenWidth - ballRadius
                    ballVx *= -1
                }

                if (ballY <= ballRadius) {
                    ballY = ballRadius
                    ballVy *= -1
                } else if (ballY >= screenHeight - ballRadius) {
                    ballY = screenHeight - ballRadius
                    ballVy *= -1
                }

                // 2. Player Collision (Red Disk)
                val playerY = screenHeight - 180f
                val playerXLimitado = playerX.coerceIn(100f, screenWidth - 100f)

                val distToPlayer = sqrt((ballX - playerXLimitado).pow(2) + (ballY - playerY).pow(2))
                if (distToPlayer <= (ballRadius + playerRadius)) {
                    // Simple collision response: reverse Y and push out
                    ballVy = -abs(ballVy)
                    ballY = playerY - playerRadius - ballRadius
                }

                // 3. Enemy Collision (Green Disk)
                val enemyY = 180f
                val enemyX = screenWidth / 2f
                val distToEnemy = sqrt((ballX - enemyX).pow(2) + (ballY - enemyY).pow(2))
                if (distToEnemy <= (ballRadius + enemyRadius)) {
                    ballVy = abs(ballVy)
                    ballY = enemyY + enemyRadius + ballRadius
                }

                delay(16.milliseconds) // ~60 FPS
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                screenWidth = coordinates.size.width.toFloat()
                screenHeight = coordinates.size.height.toFloat()
            }
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            drawRect(
                color = Color(0xFF3498DB),
                topLeft = Offset.Zero,
                size = size
            )

            val centroX = size.width / 2f
            val playerY = size.height - 180f
            val playerXLimitado = playerX.coerceIn(
                100f,
                size.width - 100f
            )

            // Draw Player
            drawCircle(
                color = Color(0xFFE74C3C),
                radius = playerRadius,
                center = Offset(
                    playerXLimitado,
                    playerY
                )
            )

            drawCircle(
                color = Color(0xFFC0392B),
                radius = playerRadius,
                center = Offset(
                    playerXLimitado,
                    playerY
                ),
                style = Stroke(width = 8f)
            )
            drawCircle(
                color = Color(0xFFFFFFFF),
                radius = 40f,
                center = Offset(
                    playerXLimitado,
                    playerY
                ),
                style = Stroke(width = 8f)
            )

            // Draw Ball
            drawCircle(
                color = Color.Yellow,
                radius = ballRadius,
                center = Offset(ballX, ballY)
            )

            // Draw Enemy
            val enemyY = 180f

            drawCircle(
                color = Color(0xFF2ECC71),
                radius = enemyRadius,
                center = Offset(
                    centroX,
                    enemyY
                )
            )

            drawCircle(
                color = Color(0xFF27AE60),
                radius = enemyRadius,
                center = Offset(
                    centroX,
                    enemyY
                ),
                style = Stroke(width = 8f)
            )
        }


        Text(
            text = """
                GIROSCOPIO
                
                X: %.2f
                Y: %.2f
                Z: %.2f
                
                ACELERÓMETRO
                
                X: %.2f
                Y: %.2f
                Z: %.2f
            """.trimIndent().format(
                gyroX,
                gyroY,
                gyroZ,
                accelX,
                accelY,
                accelZ
            ),
            modifier = Modifier.align(Alignment.TopStart),
            color = Color.White,
            fontSize = 16.sp
        )
    }
}