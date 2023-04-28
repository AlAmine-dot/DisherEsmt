package com.example.esmt.cours.disher.feature_chatbox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Conversation

@Dao
interface ConversationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversation(conversation: Conversation)

    @Query("SELECT * FROM conversations")
    fun getConversations(): List<Conversation>

    @Delete
    fun removeConversation(conversation: Conversation)
}