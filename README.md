# Marites AI – Your AI English Teacher

**Marites AI** is an intelligent, scenario-based English learning app that helps users improve their speaking skills through interactive AI conversations. The app adapts to the user’s English level, goals, and preferred conversation style, providing personalized practice with instant feedback.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Future Improvements](#future-improvements)
- [License](#license)

---

## Overview

Marites AI focuses on **conversational learning**, offering structured roleplay scenarios for real-life situations such as:

- Job Interviews  
- Travel  
- Casual Conversation  
- Dating  
- Academic and Business English  

The AI evaluates user performance in areas such as **grammar, fluency, vocabulary, and pronunciation**, giving users measurable insights into their progress.

---

## Features

- **Conversational AI Tutor:** Practice English with AI in realistic scenarios.  
- **Personalized Learning:** Tailors difficulty, focus area, and style to the user profile.  
- **Scenario-Based Roleplay:** Predefined questions and scoring criteria for structured learning.  
- **Instant Feedback:** AI evaluates grammar, pronunciation, fluency, and vocabulary.  
- **Gamified Progress Tracking:** Track your learning milestones and improvement over time.  
- **Dynamic User Profiles:** Stores English level, learning goals, preferred style, and focus areas.  

---

## Tech Stack

- **Language:** Kotlin  
- **Framework:** Jetpack Compose (UI)  
- **Backend / AI Integration:** Firebase AI with Gemini-2.5  
- **Database:** Firestore (for user profiles and scenarios)  
- **JSON Parsing:** org.json (for scenario and AI response parsing)  

---

## Architecture

1. **User Onboarding:** Collects user profile information (English level, goals, conversation style, focus).  
2. **Scenario Generator:** Generates AI conversations based on user profile and scenario schema.  
3. **AI Conversation Engine:** Uses Gemini-2.5 to roleplay scenarios, ask predefined questions, and score responses.  
4. **Scoring & Feedback:** Evaluates user answers and provides structured feedback on grammar, fluency, pronunciation, and vocabulary.  
5. **UI Layer:** Compose-based screens for onboarding, scenario display, conversation, and feedback.  

---

## License

This project is licensed under the [MIT License](./LICENSE).

---

