package com.udistrital.hockeygame.viewmodels

import androidx.lifecycle.ViewModel
import com.udistrital.hockeygame.data.models.PlayerModel
import com.udistrital.hockeygame.data.models.RoomModel
import com.udistrital.hockeygame.repositories.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomViewModel: ViewModel() {

    val repository = RoomRepository()

    private val _model = MutableStateFlow(RoomModel(
        player1= PlayerModel("uid", "email"),
        startAt= "fecha",
        turnPlayerUid= "uuid"
    ))
    val model: StateFlow<RoomModel> = _model.asStateFlow()

    fun createRoom(){
        _model.value = repository.createRoom()
    }

}