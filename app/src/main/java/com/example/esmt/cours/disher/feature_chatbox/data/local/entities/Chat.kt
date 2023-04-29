package com.example.esmt.cours.disher.feature_chatbox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val senderLabel: String,
    val dateSent: String,
    val timeSent: String,
    val conversationName: String = "Conversation 1"
)