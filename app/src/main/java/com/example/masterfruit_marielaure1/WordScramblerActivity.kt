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

class WordScramblerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Masterfruitmarielaure1Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    WordScramblerPlay()
                }
            }
        }
    }
}

enum class DifficultyWord {
    EASY, MEDIUM, HARD
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordScramblerPlay(modifier: Modifier = Modifier) {
    var difficulty by remember { mutableStateOf<DifficultyWord?>(null) }
    var word by remember { mutableStateOf("") }
    var scrambledWord by remember { mutableStateOf("") }
    var maxAttempts by remember { mutableStateOf(10) }
    var attempts by remember { mutableStateOf(0) }
    var timeLimit by remember { mutableStateOf(0) }
    var remainingTime by remember { mutableStateOf(0) }
    var showGame by remember { mutableStateOf(false) }
    var inputWord by remember { mutableStateOf("") }

    LaunchedEffect(showGame) {
        if (showGame) {
            when (difficulty) {
                DifficultyWord.EASY -> {
                    word = getRandomWord(3, 5)
                    timeLimit = 30
                }
                DifficultyWord.MEDIUM -> {
                    word = getRandomWord(6, 8)
                    timeLimit = 60
                }
                DifficultyWord.HARD -> {
                    word = getRandomWord(9, 12)
                    timeLimit = 90
                }
                else -> {
                    word = ""
                    timeLimit = 0
                }
            }
            scrambledWord = scrambleWord(word)
            remainingTime = timeLimit
            attempts = 0
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Word Scrambler",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.secondary
        )

        if (showGame) {
            Text(
                text = "Difficulté: ${difficulty?.name ?: ""}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Mot mélangé: $scrambledWord",
                modifier = Modifier.padding(vertical = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            OutlinedTextField(
                value = inputWord,
                onValueChange = { inputWord = it },
                label = { Text("Entrer un mot") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.padding(vertical = 16.dp),
                enabled = attempts < maxAttempts
            )

            Text(
                text = "Tentatives: $attempts/$maxAttempts",
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Temps restant: $remainingTime seconds",
                modifier = Modifier.padding(vertical = 8.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Button(
                onClick = {
                    if (inputWord == word) {
                        showGame = false
                        difficulty = null
                        word = ""
                        scrambledWord = ""
                        maxAttempts = 10
                        attempts = 0
                        timeLimit = 0
                        remainingTime = 0
                        inputWord = ""

                    } else {
                        if (attempts >= maxAttempts) {
                            showGame = false
                            difficulty = null
                            word = ""
                            scrambledWord = ""
                            maxAttempts = 10
                            attempts = 0
                            timeLimit = 0
                            remainingTime = 0
                            inputWord = ""
                        }
                    }

                    attempts++
                    remainingTime--

                    if (remainingTime <= 0) {

                        showGame = false
                        difficulty = null
                        word = ""
                        scrambledWord = ""
                        maxAttempts = 10
                        attempts = 0
                        timeLimit = 0
                        remainingTime = 0
                        inputWord = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Valider")
            }

        } else {
            Text(
                text = "Choisissez la difficulté",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.secondary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                DifficultyWord.values().forEach { diff ->
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
                Text("Commencer")
            }
        }
    }
}


private fun getRandomWord(minLength: Int, maxLength: Int): String {
    val wordList = listOf("cookie", "chocolat", "banane", "ordinateur", "bouteille", "chat", "chien")
    val filteredWords = wordList.filter { it.length in minLength..maxLength }
    return filteredWords.random()
}

private fun scrambleWord(word: String): String {
    val charArray = word.toCharArray()
    charArray.shuffle()
    return String(charArray)
}

@Preview(showBackground = true)
@Composable
fun WordScramblerGamePreview() {
    Masterfruitmarielaure1Theme {
        WordScramblerPlay()
    }
}
