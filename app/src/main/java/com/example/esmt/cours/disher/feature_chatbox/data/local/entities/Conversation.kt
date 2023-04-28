package com.example.esmt.cours.disher.feature_chatbox.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class Conversation(@PrimaryKey val conversationName: String)