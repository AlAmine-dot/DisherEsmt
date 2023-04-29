package com.example.esmt.cours.disher.feature_chatbox.data.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.esmt.cours.disher.core.data.local.DisherDatabase
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Conversation
import com.example.esmt.cours.disher.feature_chatbox.data.remote.api.ChatApi
import com.example.esmt.cours.disher.feature_chatbox.domain.repository.ChatRepository
import javax.inject.Inject


class ChatRepositoryImpl @Inject constructor(
    private val db : DisherDatabase
): ChatRepository{

    private val chatsDao = db.chatsDao()
    private val conversationsDao = db.conversationsDao()

    companion object  {
        // Ici ça revient à juste lui passer la companion object de l'interface,
        // je trouve que c'est une façon pratique pour ne pas avoir à implémenter ses interfaces.
        private val chatApi = ChatApi
    }

    override suspend fun getChatGptResponse(
        lastSixChats: List<Chat>,
        userPrompt: String,
        system: MutableState<String>
    ): String {
        return chatApi.getChatGptResponse(
            lastSixChats = lastSixChats,
            userPrompt = userPrompt,
            system = system
        )
    }

    override suspend fun getChats(): List<Chat> {
        return chatsDao.getChats()
    }

    override suspend fun insertChat(chat: Chat) {
        Log.d("testRepo", chat.toString())
        chatsDao.insertChat(chat)
        Log.d("testRepo", chatsDao.toString())
    }

    override suspend fun getLastSixChat(convoName: String): List<Chat> {
        return chatsDao.getRecentChats()
    }

    override suspend fun deleteChat(chat: Chat) {
        chatsDao.removeChat(chat)
    }

    override suspend fun deleteAllChats() {
        chatsDao.removeAllChats()
    }

    override suspend fun insertConversation(convo: Conversation) {
        conversationsDao.insertConversation(convo)
    }

    override suspend fun getConversations(): List<Conversation> {
        return conversationsDao.getConversations()
    }

    override suspend fun removeConversation(conversation: Conversation) {
        conversationsDao.removeConversation(conversation)
    }

}