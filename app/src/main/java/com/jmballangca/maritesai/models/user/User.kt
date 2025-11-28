package com.jmballangca.maritesai.models.user

import java.util.Calendar
import java.util.Date


data class User(
    val id : String ? = null,
    val name : String ? = null,
    val birthDate : Date ? = null,
    val level : EnglishLevel = EnglishLevel.BEGINNER,
    val goals : List<LearningGoal> = emptyList(),
    val style: ConversationStyle = ConversationStyle.FRIENDLY,
    val formality : FormalityLevel = FormalityLevel.FORMAL,
    val focus: SpeakingFocus = SpeakingFocus.FLUENCY,
    val createdAt : Date = Date()
)




val JOHN_DOE = User(
    id = "user_001",
    name = "John Doe",
    birthDate = Calendar.getInstance().apply {
        set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) - 25)
        set(Calendar.MONTH, Calendar.JANUARY)
        set(Calendar.DAY_OF_MONTH, 1)
    }.time,
    level = EnglishLevel.INTERMEDIATE, // assuming some basic skills
    goals = listOf(LearningGoal.INTERVIEW),
    style = ConversationStyle.PROFESSIONAL, // professional tone for interviews
    formality = FormalityLevel.FORMAL,
    focus = SpeakingFocus.GRAMMAR, // focus on correct grammar for interviews
    createdAt = Date()
)



