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

@Composable
fun GameScreen() {

    val context = LocalContext.current

    var gyroX by remember { mutableFloatStateOf(0f) }
    var gyroY by remember { mutableFloatStateOf(0f) }
    var gyroZ by remember { mutableFloatStateOf(0f) }

    var accelX by remember { mutableFloatStateOf(0f) }
    var accelY by remember { mutableFloatStateOf(0f) }
    var accelZ by remember { mutableFloatStateOf(0f) }

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

    Box(
        modifier = Modifier.fillMaxSize()
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

            drawCircle(
                color = Color(0xFFE74C3C),
                radius = 80f,
                center = Offset(
                    playerXLimitado,
                    playerY
                )
            )

            drawCircle(
                color = Color(0xFFC0392B),
                radius = 80f,
                center = Offset(
                    playerXLimitado,
                    playerY
                ),
                style = Stroke(width = 8f)
            )


            val enemyY = 180f

            drawCircle(
                color = Color(0xFF2ECC71),
                radius = 80f,
                center = Offset(
                    centroX,
                    enemyY
                )
            )

            drawCircle(
                color = Color(0xFF27AE60),
                radius = 80f,
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