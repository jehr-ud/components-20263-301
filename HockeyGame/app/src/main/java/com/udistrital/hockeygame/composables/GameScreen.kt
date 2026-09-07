package com.udistrital.hockeygame.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun GameScreen(){
    Column() {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawRect(
                color = Color(0xFF3498DB),
                topLeft = Offset.Zero, // Start in top 0,0
                size = size // automatic value to draw in full screen
            )

            val centroX = size.width / 2f
            val centroY = size.height / 2f

            drawCircle(
                color = Color(0xFFE74C3C),
                radius = 120f,
                center = Offset(centroX, centroY)
            )
            // Border
            drawCircle(
                color = Color(0xFFC0392B),
                radius = 120f,
                center = Offset(centroX, centroY),
                style = Stroke(width = 8f)
            )

            // Stick 1
            drawCircle(
                color = Color(0xFFE74C3C),
                radius = 80f,
                center = Offset(480f, 150f)
            )
            // border
            drawCircle(
                color = Color(0xFFC0392B),
                radius = 80f,
                center = Offset(480f, 150f),
                style = Stroke(width = 8f)
            )

            // Stick 2

            drawCircle(
                color = Color(0xFFE74C3C),
                radius = 80f,
                center = Offset(centroX, centroY)
            )
             // border
            drawCircle(
                color = Color(0xFFC0392B),
                radius = 80f,
                center = Offset(centroX, centroY),
                style = Stroke(width = 8f)
            )


        }
    }
}