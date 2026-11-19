package com.example.pzz6

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pzz6.ui.theme.Pzz6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemDark) }

            Pzz6Theme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PracticeScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}

@Composable
fun PracticeScreen(
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    // Уроки 6, 9, 10: State, remember и mutableStateOf
    var counter by remember { mutableIntStateOf(0) }
    var userText by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Практическая работа №6",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Переключение светлой и тёмной темы
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isDarkTheme) "Тёмная тема" else "Светлая тема",
                    style = MaterialTheme.typography.titleMedium
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onToggleTheme
                )
            }
        }

        // Уроки 6, 8, 9, 10: State + Recomposition + remember
        ClickCounterCard(
            count = counter,
            onIncrement = { counter++ },
            onReset = { counter = 0 }
        )

        // Урок 7: TextField и Checkbox
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "TextField и Checkbox",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )

                OutlinedTextField(
                    value = userText,
                    onValueChange = { userText = it },
                    label = { Text("Введите текст") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = if (userText.isEmpty()) "Текст пока не введён" else "Вы ввели: $userText",
                    style = MaterialTheme.typography.bodyLarge
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isChecked) "Флажок установлен" else "Флажок снят",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun ClickCounterCard(
    count: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit
) {
    // Урок 8: отслеживание рекомпозиции в Logcat
    SideEffect {
        Log.d("RecompositionDemo", "ClickCounterCard перерисована, count = $count")
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "State и Recomposition",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "Количество нажатий: $count",
                style = MaterialTheme.typography.bodyLarge
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onIncrement) {
                    Text("Нажать (+1)")
                }
                OutlinedButton(onClick = onReset) {
                    Text("Сброс")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun LightPreview() {
    Pzz6Theme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PracticeScreen(isDarkTheme = false, onToggleTheme = {})
        }
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun DarkPreview() {
    Pzz6Theme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            PracticeScreen(isDarkTheme = true, onToggleTheme = {})
        }
    }
}