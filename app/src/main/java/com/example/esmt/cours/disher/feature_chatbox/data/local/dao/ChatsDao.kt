package com.example.esmt.cours.disher.feature_chatbox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat

@Dao
interface ChatsDao {

    // TESTED
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat)

    // TESTED
    @Query("SELECT * FROM chats")
    suspend fun getChats(): List<Chat>

    // TESTED
    @Delete
    suspend fun removeChat(chat: Chat)

    // TESTED
    @Query("DELETE FROM chats")
    suspend fun removeAllChats()

    // TESTED
    @Query("SELECT * FROM chats ORDER BY dateSent DESC, timeSent DESC LIMIT 6")
    suspend fun getRecentChats(): List<Chat>

//    @Query("DELETE FROM chats WHERE conversationName LIKE :convoName")
//    fun removeAllChatsByConversation(convoName: String)

    // Idem ici (on remplacera cette merde après)
//    @RawQuery
//    fun getChatsRawQuery(query: SupportSQLiteQuery): List<Chat>
//
//    fun getChatsByConvo(conversationName: String): List<Chat> {
//        val query = SimpleSQLiteQuery(
//            "SELECT * FROM chats WHERE conversationName LIKE ?;",
//            arrayOf(conversationName)
//        )
//        return getChatsRawQuery(query)
//    }

//    @Query("DELETE FROM chats WHERE conversationName LIKE :convoName")
//    fun removeAllChatsByConvo(convoName: String)
//    // End Chat Section

}