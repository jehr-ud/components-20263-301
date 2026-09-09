package com.udistrital.hockeygame.composables

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.udistrital.hockeygame.R

@Composable
fun GameScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current
//
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
                color = Color(255, 255, 255, 255),
                topLeft = Offset(
                    (size.width - size.width*0.80f) / 2f,
                    (size.height - size.height*0.90f) / 2f
                ),
                size = Size(size.width*0.80f, size.height*0.90f)
            )

            drawRect(
                color = Color(255, 86, 86, 255),
                topLeft = Offset(
                    (size.width - size.width*0.80f) / 2f,
                    (size.height - size.height*0.91f) / 2f
                ),
                size = Size(size.width*0.80f, size.height*0.91f),
                style = Stroke(width = 4.dp.toPx())
            )

            drawCircle(
                color = Color(255, 86, 86, 255),
                radius = 100.dp.toPx(),
                center = Offset(size.width / 2f, size.height- 40.dp.toPx()),
                style = Stroke(width = 4.dp.toPx())
            )

            drawCircle(
                color = Color(255, 86, 86, 255),
                radius = 100.dp.toPx(),
                center = Offset(size.width / 2f, 40.dp.toPx()),
                style = Stroke(width = 4.dp.toPx())
            )

            drawLine(
                color = Color(255, 86, 86, 255),
                start = Offset(40.dp.toPx(), size.height / 2f),
                end = Offset((size.width-40.dp.toPx()), size.height / 2f),
                strokeWidth = 4.dp.toPx()
            )

            drawLine(
                color = Color(255, 86, 86, 255),
                start = Offset(40.dp.toPx(), size.height / 4f),
                end = Offset((size.width-40.dp.toPx()), size.height / 4f),
                strokeWidth = 4.dp.toPx()
            )

            drawLine(
                color = Color(255, 86, 86, 255),
                start = Offset(40.dp.toPx(), (size.height / 4f)*3),
                end = Offset((size.width-40.dp.toPx()), (size.height / 4f)*3),
                strokeWidth = 4.dp.toPx()
            )

            drawRect(
                color = Color(0, 140, 159, 255),
                topLeft = Offset.Zero,
                size = Size(size.width, size.height),
                style = Stroke(width = 80.dp.toPx())
            )

            val centroX = size.width / 2f

            val playerY = size.height - 180f

            val playerXLimitado = playerX.coerceIn(
                100f,
                size.width - 100f
            )

            drawCircle(
                color = Color.Black,
                radius = 30.dp.toPx(),
                center = Offset(size.width / 2f, size.height / 2f),
            )

            drawCircle(
                color = Color(red = 43, green = 138, blue = 255, alpha = 255),
                radius = 40.dp.toPx(),
                center = Offset(
                    playerXLimitado,
                    playerY),
                style = Stroke(width = 26.dp.toPx())

            )

            val enemyY = 180f

            drawCircle(
                color = Color(61,255,43),
                radius = 40.dp.toPx(),
                center = Offset(
                    centroX,
                    enemyY
                ),
                style = Stroke(width = 26.dp.toPx())

            )



            //Viejo
//            val centroX = size.width / 2f
//
//            val playerY = size.height - 180f
//
//            val playerXLimitado = playerX.coerceIn(
//                100f,
//                size.width - 100f
//            )

//            drawCircle(
//                color = Color(0xFFE74C3C),
//                radius = 80f,
//                center = Offset(
//                    playerXLimitado,
//                    playerY
//                )
//            )
//
//            drawCircle(
//                color = Color(0xFFC0392B),
//                radius = 80f,
//                center = Offset(
//                    playerXLimitado,
//                    playerY
//                ),
//                style = Stroke(width = 8f)
//            )
//
//
////            val enemyY = 180f
//
//            drawCircle(
//                color = Color(0xFF2ECC71),
//                radius = 80f,
//                center = Offset(
//                    centroX,
//                    enemyY
//                )
//            )
//
//            drawCircle(
//                color = Color(0xFF27AE60),
//                radius = 80f,
//                center = Offset(
//                    centroX,
//                    enemyY
//                ),
//                style = Stroke(width = 8f)
//            )
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

        Button(
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .width(120.dp)
                .height(48.dp)
                .shadow(10.dp, RoundedCornerShape(18.dp)),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00AEFF)
            )
        ) {
            Text(
                text = stringResource(R.string.home_btn_back),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}