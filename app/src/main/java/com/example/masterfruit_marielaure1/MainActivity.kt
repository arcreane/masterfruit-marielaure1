package com.example.masterfruit_marielaure1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.masterfruit_marielaure1.ui.theme.Masterfruitmarielaure1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Masterfruitmarielaure1Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.padding(25.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Android Game", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(bottom = 16.dp))
                        Text("Choisissez un jeu !", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 32.dp))
                        Button(
                            onClick = {
                                startActivity(Intent(this@MainActivity, GuessANumberActivity::class.java))
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                                .height(50.dp)
                                .background(MaterialTheme.colorScheme.secondary),
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSecondary)
                        ) {
                            Text("Guess A Number", color = MaterialTheme.colorScheme.onSecondary)
                        }

                        Button(
                            onClick = {
                                startActivity(Intent(this@MainActivity, WordScramblerActivity::class.java))
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                                .height(50.dp)
                                .background(MaterialTheme.colorScheme.secondary),
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSecondary)
                        ) {
                            Text("Word Scrambler", color = MaterialTheme.colorScheme.onSecondary)
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Masterfruitmarielaure1Theme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(25.dp)) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(),
                    content = {
                        Text(
                            text = "Guess A Number",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                )
                Button(
                    onClick = { },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(),
                    content = {
                        Text(
                            text = "Word Scrambler",
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                )
            }
        }
    }
}

