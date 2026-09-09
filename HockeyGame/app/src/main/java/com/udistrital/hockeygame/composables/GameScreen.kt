package com.udistrital.hockeygame.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udistrital.hockeygame.R
import kotlin.Unit


@Composable
fun GameScreen(onBack: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                color = Color(0xFF3498DB),
                topLeft = Offset.Zero,
                size = size
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