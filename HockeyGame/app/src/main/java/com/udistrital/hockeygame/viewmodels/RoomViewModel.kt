package com.udistrital.hockeygame.viewmodels

import androidx.lifecycle.ViewModel
import com.udistrital.hockeygame.repositories.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomViewModel: ViewModel() {

    val repository = RoomRepository()

    private val _code = MutableStateFlow("")
    val code: StateFlow<String> = _code.asStateFlow()

    fun createRoom(){
        _code.value = repository.createRoom()
    }

}