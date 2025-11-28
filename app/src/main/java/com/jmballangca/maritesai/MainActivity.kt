package com.jmballangca.maritesai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import com.jmballangca.maritesai.models.scenario.Scenario
import com.jmballangca.maritesai.models.scenario.toScenarioArray
import com.jmballangca.maritesai.models.user.JOHN_DOE
import com.jmballangca.maritesai.models.user.User
import com.jmballangca.maritesai.schemas.SCENARIO_SCHEMA
import com.jmballangca.maritesai.ui.theme.MaritesAITheme
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.collections.mutableListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaritesAITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestingScreen(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TestingScreen(
    modifier: Modifier = Modifier,

) {
    // Initialize the AI model
    val model = remember {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(
                modelName = "gemini-2.5-flash",
                generationConfig = generationConfig {
                    responseMimeType = "application/json"
                    responseSchema = SCENARIO_SCHEMA
                }
            )
    }

    // UI state
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf(
        emptyList<Scenario>()
    ) }
    val scope = rememberCoroutineScope()
    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    // Function to generate scenarios
    fun generateScenarios(user: User) {
        val prompt = content {
            text(
                """
                Generate English learning scenarios for the following user:
                Name: ${user.name}
                Age: ${user.birthDate}
                English Level: ${user.level}
                Goals: ${user.goals.joinToString()}
                Conversation Style: ${user.style}
                Formality: ${user.formality}
                Focus: ${user.focus}
                
                Return the output strictly according to the SCENARIO_SCHEMA in JSON format.
                """.trimIndent()
            )
        }

        scope.launch {
            try {
                isLoading = true
                val response = model.generateContent(prompt)
                // Assuming response is a JSON array of scenarios
                val scenarios = response.text // this is String?
                val jsonObject = JSONObject(scenarios)
                val scenariosList: List<Scenario> = jsonObject.toScenarioArray()
                result = scenariosList
            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }

    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = { generateScenarios(JOHN_DOE) }
            ) {
                Text("Generate Scenarios")
            }
        }

        if (isLoading) {
            item {
                CircularProgressIndicator()
            }
        }

        if (errorMessage != null) {
            item {
                Text(errorMessage.toString())
            }
        }
        items(result) { scenario ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Title
                    Text(
                        text = scenario.title ?: "No Title",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Description
                    Text(
                        text = scenario.description ?: "No Description",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Bulleted Questions
                    if (scenario.questions.isNotEmpty()) {
                        Text("Questions:", style = MaterialTheme.typography.labelMedium)
                        Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                            scenario.questions.forEach { question ->
                                Text("• $question", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Criteria
                    if (scenario.criteria.isNotEmpty()) {
                        Text("Scoring Criteria:", style = MaterialTheme.typography.labelMedium)
                        Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                            scenario.criteria.forEach { (key, value) ->
                                Text("$key: $value", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }

    }
}
