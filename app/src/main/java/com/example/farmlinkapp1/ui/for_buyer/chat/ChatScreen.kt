package com.example.farmlinkapp1.ui.for_buyer.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.farmlinkapp1.data.MongoDB
import com.example.farmlinkapp1.model.ChatMessage

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier
) {
    val chatViewModel: ChatViewModel = viewModel()
    val chatItems by chatViewModel.getChatMessages().collectAsStateWithLifecycle(initialValue = emptyList())
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        // List of messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(chatItems) { msg->
                ChatBubble(msg)
            }
        }

        // Message input and send button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var text by remember { mutableStateOf("") }
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.White)
                    .height(40.dp)
                    .padding(8.dp)
            )

            IconButton(onClick = {
                if (text.isNotBlank()) {
                    chatViewModel.sendMessage(text, "12345")
                    text = ""
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val backgroundColor =
        if (message.senderId == MongoDB.getOwnerId())
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surfaceContainer

    val alignment =
        if (message.senderId == MongoDB.getOwnerId()) Alignment.BottomEnd
        else Alignment.BottomStart

    Box(
        contentAlignment = alignment,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Surface(
            color = backgroundColor,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = message.message,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
