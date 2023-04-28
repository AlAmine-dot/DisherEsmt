package com.example.esmt.cours.disher.feature_chatbox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat

@Dao
interface ChatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat: Chat)

    @Query("SELECT * FROM chats")
    fun getChats(): List<Chat>

    @Query("SELECT * FROM chats WHERE conversationName " +
            "LIKE :convoName ORDER BY fullTimeStamp DESC LIMIT 6")
    fun getLastSixChats(convoName: String): List<Chat>

    @Delete
    fun removeChat(chat: Chat)

    @Query("DELETE FROM chats")
    fun removeAllChats()

    @Query("DELETE FROM chats WHERE conversationName LIKE :convoName")
    fun removeAllChatsByConversation(convoName: String)

    // Idem ici (on remplacera cette merde après)
    @RawQuery
    fun getChatsRawQuery(query: SupportSQLiteQuery): List<Chat>

    fun getChatsByConvo(conversationName: String): List<Chat> {
        val query = SimpleSQLiteQuery(
            "SELECT * FROM chats WHERE conversationName LIKE ?;",
            arrayOf(conversationName)
        )
        return getChatsRawQuery(query)
    }

    @Query("DELETE FROM chats WHERE conversationName LIKE :convoName")
    fun removeAllChatsByConvo(convoName: String)
    // End Chat Section

}