package com.example.masterfruit_marielaure1

import com.example.masterfruit_marielaure1.ui.theme.Masterfruitmarielaure1Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class GuessANumberActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Masterfruitmarielaure1Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    GuessANumberPlay()
                }
            }
        }
    }
}

enum class Difficulty {
    EASY, MEDIUM, HARD
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessANumberPlay(modifier: Modifier = Modifier) {
    var difficulty by remember { mutableStateOf<Difficulty?>(null) }
    var guess by remember { mutableStateOf("") }
    var attempts by remember { mutableStateOf(0) }
    var maxAttempts by remember { mutableStateOf(10) }
    var progress by remember { mutableStateOf(0f) }
    var score by remember { mutableStateOf(0) }
    val attemptsList = remember { mutableStateListOf<String>() }
    var randomNum by remember { mutableStateOf(0) }
    var showGame by remember { mutableStateOf(false) }
    var changeDifficulty by remember { mutableStateOf(false) }

    if (difficulty == null) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Android Game",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Choisissez un niveau de difficulté !",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Difficulty.values().forEach { diff ->
                    Row(Modifier.padding(8.dp)) {
                        RadioButton(
                            selected = difficulty == diff,
                            onClick = { difficulty = diff },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.secondary)
                        )
                        Text(
                            text = diff.name,
                            Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Button(
                onClick = { showGame = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Valider")
            }
        }
    } else {
        if (changeDifficulty) {
            difficulty = null
            guess = ""
            attempts = 0
            maxAttempts = 10
            progress = 0f
            score = 0
            attemptsList.clear()
            randomNum = 0
            showGame = false
            changeDifficulty = false
        }

        if (randomNum == 0) {
            when (difficulty) {
                Difficulty.EASY -> randomNum = Random.nextInt(1, 11)
                Difficulty.MEDIUM -> randomNum = Random.nextInt(1, 51)
                Difficulty.HARD -> randomNum = Random.nextInt(1, 101)
                else -> {}
            }
        }

        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = "Android Game",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Niveau de difficulté : ${difficulty?.name ?: ""}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )

            OutlinedTextField(
                value = guess,
                onValueChange = { guess = it },
                label = { Text("Entrer un nombre") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Button(
                onClick = {
                    val userGuess = guess.toIntOrNull()
                    if (userGuess != null && attempts < maxAttempts) {
                        attempts += 1
                        progress = attempts.toFloat() / maxAttempts
                        if (userGuess == randomNum) {
                            score = maxAttempts - attempts
                            attemptsList.add("$userGuess - Bien jouer!")
                        } else if (userGuess < randomNum) {
                            attemptsList.add("$userGuess - Trop petit")
                        } else if (userGuess > randomNum) {
                            attemptsList.add("$userGuess - Trop grand")
                        }
                        guess = ""
                    }

                    if (attempts == maxAttempts || userGuess == randomNum) {
                        showGame = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Valider")
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            attemptsList.forEach { attempt ->
                Text(
                    text = attempt,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }


            Text(
                text = "Score: $score",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            if (!showGame) {
                Button(
                    onClick = {
                        changeDifficulty = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Rejouer")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        difficulty = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Retourner à la liste de jeux")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuessANumberGamePreview() {
    Masterfruitmarielaure1Theme {
        GuessANumberPlay()
    }
}
