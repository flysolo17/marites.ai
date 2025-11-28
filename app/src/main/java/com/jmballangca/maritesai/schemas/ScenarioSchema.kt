package com.jmballangca.maritesai.schemas

import com.google.firebase.ai.type.Schema


val SCENARIO_SCHEMA = Schema.obj(
    properties = mapOf(
        "scenarios" to Schema.array(
            items = Schema.obj(
                properties = mapOf(
                    "title" to Schema.string("Title of the scenario, e.g., Talk to a Girl"),
                    "description" to Schema.string("Short description or instructions for the scenario"),
                    "questions" to Schema.array(
                        items = Schema.string("Predefined question the AI asks the user")
                    ),
                    "criteria" to Schema.obj(
                        properties = mapOf(
                            "grammar" to Schema.double("Points/weight for grammar"),
                            "fluency" to Schema.double("Points/weight for fluency"),
                            "vocabulary" to Schema.double("Points/weight for vocabulary"),
                            "pronunciation" to Schema.double("Points/weight for pronunciation")
                        ),
                        description = "Scoring criteria for the scenario"
                    )
                ),
                description = "Single scenario object with questions and scoring"
            )
        )
    ),
    description = "Schema for AI English tutor scenarios with title, description, questions, and scoring criteria"
)
