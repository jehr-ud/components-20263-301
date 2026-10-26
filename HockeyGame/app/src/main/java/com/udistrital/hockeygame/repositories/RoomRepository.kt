package com.udistrital.hockeygame.repositories

import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.udistrital.hockeygame.data.models.PlayerModel
import com.udistrital.hockeygame.data.models.RoomModel
import kotlin.String

class RoomRepository {
    private val database = Firebase.database
    val roomRef = database.getReference("rooms")

    fun createRoom(): RoomModel{
        val model = RoomModel(
            player1= PlayerModel("uid", "email"),
            startAt= "fecha",
            turnPlayerUid= "uuid"
        )
        model.id = roomRef.key
        roomRef.setValue(model)


        return model
    }
}