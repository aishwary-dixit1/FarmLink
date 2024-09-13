package com.example.farmlinkapp1.ui.for_buyer.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    init {

    }

    var messages = mutableStateListOf<ChatMessage>()
    //messages = mutableStateListOf()

    fun sendMessage(message: String, chatRoomId: String) =
        viewModelScope.launch {
            MongoDB.sendMessage(message, chatRoomId)
        }

    fun getChatMessages() : Flow<List<ChatMessage>> {
        return MongoDB.getChatMessages()
    }
}
