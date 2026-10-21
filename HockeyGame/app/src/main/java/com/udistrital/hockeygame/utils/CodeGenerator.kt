package com.udistrital.hockeygame.utils

import kotlin.random.Random

class CodeGenerator {
    companion object {
        private const val ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        private const val CODE_LENGTH = 6

        fun generateRoomCode(): String {
            return (1..CODE_LENGTH)
                .map { ALLOWED_CHARS[Random.nextInt(ALLOWED_CHARS.length)] }
                .joinToString("")
        }
    }
}