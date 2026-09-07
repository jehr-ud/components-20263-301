package com.udistrital.hockeygame.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.udistrital.hockeygame.R
import com.udistrital.hockeygame.enums.TypeScreen

@Composable
fun HomeScreen(onClickGame: () -> Unit){

    Column() {
        Text(stringResource(R.string.home_title))

        Text(stringResource(R.string.home_score))

        Button(onClick = onClickGame) {
            Text(stringResource(R.string.home_btn_play))
        }
    }

}