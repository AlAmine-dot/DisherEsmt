package com.example.esmt.cours.disher.feature_chatbox.presentation.chatbox

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.esmt.cours.disher.R
import com.example.esmt.cours.disher.core.presentation.main_screen.UiEvent
import com.example.esmt.cours.disher.feature_chatbox.data.local.entities.Chat
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.ChatConfig
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.SenderLabel
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.dateFormatter
import com.example.esmt.cours.disher.feature_chatbox.domain.utils.timeFormatter
import com.example.esmt.cours.disher.feature_meals.presentation.home.HomeUiEvent
import com.example.esmt.cours.disher.feature_meals.presentation.meal_details.details_screen.MealDetailsUiEvent
import com.example.esmt.cours.disher.ui.theme.DarkTurquoise
import com.example.esmt.cours.disher.ui.theme.MeltyGreen
import com.example.esmt.cours.disher.ui.theme.MeltyGreenLO
import com.example.esmt.cours.disher.ui.theme.MeltyGreenVLO
import com.example.esmt.cours.disher.ui.theme.TextWhite
import java.time.LocalDateTime

@Composable
fun ChatboxScreen (
    onNavigate: (ChatboxUiEvent.Navigate) -> Unit,
    onPopBackStack: () -> Unit,
    chatboxViewModel: ChatboxViewModel = hiltViewModel(),
    sendMainUiEvent: (UiEvent) -> Unit,

    ){
//    LaunchedEffect(key1 = true) {
//        Log.d("testChatboxScreen", chatboxViewModel.toString())
//        chatboxViewModel.onEvent(ChatboxUiEvent.PromptChatAssistant("Good morning assistant, I would like a pasta based meal, very consistent and not time consuming."))
//    }

    val chatUiState by chatboxViewModel.uiState.collectAsState()
    val chatMessages = chatUiState.getChatMessages()
    val listState = rememberLazyListState()


    Scaffold(
        topBar = {
            ChatHeaderComponent(onPopBackStack)
        }
    ) {paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            ChatboxComponent(
                chatMessages,
                listState,
                Modifier
                    .weight(.90f)
                    .fillMaxWidth()
                    .background(TextWhite)
            )
        }
    }

}

@Composable
private fun ChatboxComponent(
    messages: List<Chat>,
    listState: LazyListState,
    modifier: Modifier
){
    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item{
            Spacer(modifier = Modifier.height(5.dp))
        }
        items(messages.size) {index ->
            ChatMessageItem(
                messages[index]
            )
        }
        item{
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

}

@Composable
private fun ChatMessageItem(
    chat: Chat
){
    val message = chat.text
    if(chat.senderLabel == SenderLabel.CHATGPT_SENDER_LABEL){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(70.dp)

        ){
            Column(
                modifier = Modifier
                    .weight(.2f)
                    .heightIn(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFF11998E),
                                    MeltyGreen,
                                )
                            )
                        )
                        .shadow(120.dp, CircleShape)
                    ,
                ){
                    Image(
                        painterResource(id = R.drawable.disherbuddy_32),
                        contentDescription = "DishBuddy profile picture",
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .align(Alignment.Center)
                            .size(22.dp)
                        ,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(.8f)
                    .heightIn(70.dp)
                ,
                horizontalAlignment = Alignment.Start,
    //            verticalArrangement = Arrangement
            ){
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .heightIn(50.dp)
                        .fillMaxWidth(.87f),
                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp,
                    bottomStart = 16.dp, bottomEnd = 16.dp),
                ) {
                    Text(
                        message,
                        style = MaterialTheme.typography.body1,
                        color = DarkTurquoise,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)

                    )
                }
            }
        }
    }else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(70.dp)

        ){
            Column(
                modifier = Modifier
                    .weight(.8f)
                    .heightIn(70.dp)
                ,
                horizontalAlignment = Alignment.End,
                //            verticalArrangement = Arrangement
            ){
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .heightIn(50.dp)
                        .fillMaxWidth(.8f),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 0.dp,
                        bottomStart = 16.dp, bottomEnd = 16.dp),
                ) {
                    Text(
                        message,
                        style = MaterialTheme.typography.body1,
                        color = DarkTurquoise,
                        modifier = Modifier
                            .padding(vertical = 5.dp, horizontal = 10.dp)

                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(.2f)
                    .heightIn(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .shadow(120.dp, CircleShape)
                    ,
                ){
                    val painter = rememberImagePainter(data = R.drawable.blank_profilepic,
                        builder = {
                            placeholder(R.drawable.blank_profilepic)
                            error(R.drawable.blank_profilepic)
                        }
                    )
                    Image(
                        painter = painter,
                        contentDescription = "Default user profile pic",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

}

@Composable
private fun ChatHeaderComponent(
    onPopBackStack: () -> Unit,
    ){
    Surface(
        elevation = 16.dp
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MeltyGreen)
                .heightIn(67.dp)
            ,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { onPopBackStack() }
            )
            Text(
                text = "DishBuddy Chat",
                style = MaterialTheme.typography.h5,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Info",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        //                    onInfoClicked()
                    }
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview(){
    val chatList = listOf(
        Chat(
            id = 1,
            text = "Hello, how may I assist you today ?",
            senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        ),
        Chat(
            id = 2,
            text = "Hi, I would like a pretty consistent meal without a lot of cooking",
            senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).plusMinutes(5).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        ),
        Chat(
            id = 3,
            text = "Sure, I can suggest some recipes for you. Do you have any dietary restrictions ?",
            senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).plusMinutes(10).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        ),
        Chat(
            id = 4,
            text = "Yes, I'm a vegetarian",
            senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).plusMinutes(15).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        ),
        Chat(
            id = 5,
            text = "Great, here are a few recipes that might interest you:...",
            senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).plusMinutes(20).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        ),
        Chat(
            id = 6,
            text = "Thank you, those look great",
            senderLabel = SenderLabel.HUMAN_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).plusMinutes(25).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        ),
        Chat(
            id = 7,
            text = "You're welcome. Let me know if you need anything else.",
            senderLabel = SenderLabel.CHATGPT_SENDER_LABEL,
            dateSent = LocalDateTime.now().minusDays(1).format(dateFormatter),
            timeSent = LocalDateTime.now().minusDays(1).plusMinutes(30).format(timeFormatter),
            conversationName = ChatConfig.DEFAULT_CONVO_NAME
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ChatHeaderComponent({})
        ChatboxComponent(
            messages = chatList, listState = rememberLazyListState(), modifier = Modifier
                .weight(.90f)
                .fillMaxWidth()
                .background(TextWhite)
        )
    }
}