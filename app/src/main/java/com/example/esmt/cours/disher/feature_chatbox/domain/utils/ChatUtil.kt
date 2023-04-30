package com.example.esmt.cours.disher.feature_chatbox.domain.utils


import java.time.format.DateTimeFormatter
import java.util.*
import android.graphics.*
import android.icu.text.SimpleDateFormat

//val timeFormatter: SimpleDateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
//val dateFormatter: SimpleDateFormat = SimpleDateFormat("MM.dd.yy", Locale.getDefault())

val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd.yy")


object SenderLabel {
    var HUMAN_SENDER_LABEL = "Me"
    const val DEFAULT_HUMAN_LABEL = "Me"
    const val CHATGPT_SENDER_LABEL = "ChatGPT"
    const val DEFAULT_SYSTEM_CONTEXT = "Tu es un chef cuistot italien gentil et serviable. Ne répond pas aux questions qui ne concernent pas trop la cuisine, tu en ignore la réponse."
}

@Suppress("unused")
object ChatConfig {
    const val GPT_3_5_TURBO = "gpt-3.5-turbo"
    const val GPT_4 = "gpt-4"

    const val DEFAULT_CONVO_NAME = "Default"

    const val SCROLL_ANIMATION_DELAY = 1500L

    const val DEFAULT_CONVO_CONTEXT = "You are my helpful assistant"

    private val aiAdjectives = listOf(
        "Sarcastic",
        "Helpful",
        "Unhelpful",
        "Optimistic",
        "Pessimistic",
        "Excited",
        "Joyful",
        "Charming",
        "Inspiring",
        "Nonchalant",
        "Relaxed",
        "Loud",
        "Annoyed"
    )

    private val randomChatGptAdjective = aiAdjectives.random()

    val conversationalContext = listOf(
        "Be as ${randomChatGptAdjective.lowercase()} as possible.",
        "You are my ${randomChatGptAdjective.lowercase()} assistant",
        "Play the role of the ${randomChatGptAdjective.lowercase()} bot",
        "Act as if you are extremely ${randomChatGptAdjective.lowercase()}",
        "Act as if you are the only ${randomChatGptAdjective.lowercase()} AI"
    )

    val exampleConvoContext = "\"${conversationalContext.random()}\""

    val speakPrompts = listOf(
        "What would you like to ask?",
        "Speak now... please",
        "LOL spit it out already...",
        "* ChatGPT is yawning... *",
        "Speaketh you may",
        "Listening for dat beautiful voice...",
        "Hello Human"
    )
}