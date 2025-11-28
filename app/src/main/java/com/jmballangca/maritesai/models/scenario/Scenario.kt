package com.jmballangca.maritesai.models.scenario

import org.json.JSONArray
import org.json.JSONObject
import java.util.Date


data class Scenario(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val questions: List<String> = emptyList(),
    val criteria: Map<String, Int> = emptyMap(),
    val createdAt: Date = Date()
)

// Extension function to convert the AI response JSONObject to List<Scenario>
fun JSONObject.toScenarioArray(): List<Scenario> {
    val scenarios = mutableListOf<Scenario>()

    val jsonArray: JSONArray? = this.optJSONArray("scenarios")
    if (jsonArray != null) {
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.optJSONObject(i) ?: continue

            val id = obj.optString("id", null)
            val title = obj.optString("title", null)
            val description = obj.optString("description", null)

            // Convert questions array
            val questionsJson = obj.optJSONArray("questions")
            val questions = mutableListOf<String>()
            if (questionsJson != null) {
                for (j in 0 until questionsJson.length()) {
                    questions.add(questionsJson.optString(j))
                }
            }

            // Convert criteria object
            val criteriaJson = obj.optJSONObject("criteria")
            val criteria = mutableMapOf<String, Int>()
            if (criteriaJson != null) {
                val keys = criteriaJson.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    criteria[key] = criteriaJson.optInt(key, 0)
                }
            }

            scenarios.add(
                Scenario(
                    id = id,
                    title = title,
                    description = description,
                    questions = questions,
                    criteria = criteria
                )
            )
        }
    }

    return scenarios
}