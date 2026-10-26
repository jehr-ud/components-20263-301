package com.udistrital.hockeygame.data.models

data class RoomModel(
    val code: String,
    val player1: PlayerModel,
    val player2: PlayerModel?,

    val startAt: String,
    val winnerPlayerUid: String,
    val turnPlayerUid: String
)