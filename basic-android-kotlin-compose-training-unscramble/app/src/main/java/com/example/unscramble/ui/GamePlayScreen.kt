package com.example.unscramble.ui

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.viewmodel.GameViewModel
import com.example.unscramble.viewmodel.GameViewModelFactory
import com.example.unscramble.network.ApiRepository
import com.example.unscramble.network.RetrofitInstance
import coil.compose.rememberImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unscramble.model.Opcion
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GamePlayScreen(difficulty: Int, navController: NavHostController) {
    val apiRepository = ApiRepository(RetrofitInstance.apiService)
    val gameViewModel: GameViewModel = viewModel(factory = GameViewModelFactory(apiRepository))

    LaunchedEffect(difficulty) {
        gameViewModel.fetchPreguntas(difficulty)
    }

    val preguntas by gameViewModel.preguntas.collectAsState(initial = emptyList())

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var answeredQuestionsCount by remember { mutableStateOf(0) }
    var isGameFinished by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(10) }
    var shuffledOptions by remember { mutableStateOf(listOf<Opcion>()) }

    fun moveToNextQuestion(isAnswerCorrect: Boolean) {
        answeredQuestionsCount++
        if (isAnswerCorrect) {
            score++
        }

        if (answeredQuestionsCount < 10) {
            currentQuestionIndex++
        } else {
            isGameFinished = true
        }
    }

    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    LaunchedEffect(currentQuestionIndex) {
        timer?.cancel()

        remainingTime = 10

        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                moveToNextQuestion(false)
            }
        }.start()

        val currentQuestion = preguntas.getOrNull(currentQuestionIndex)
        if (currentQuestion != null) {
            shuffledOptions = currentQuestion.opcions.shuffled()
        }
    }

    DisposableEffect(Unit) {
        onDispose { timer?.cancel() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF0C8FF8), Color(0xFF509AE3))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            when {
                preguntas.isEmpty() -> {
                    Text(
                        "Cargando preguntas...",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                        color = Color.White
                    )
                }
                isGameFinished -> {
                    Text(
                        "Juego finalizado!!! $score/10",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                        color = Color.White
                    )
                    Button(
                        onClick = { navController.navigate("game_screen") },
                        modifier = Modifier.padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF36538),
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text("Reiniciar", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
                else -> {
                    val currentQuestion = preguntas.getOrNull(currentQuestionIndex)

                    if (currentQuestion != null) {
                        Text(
                            "$remainingTime segundos",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            currentQuestion.pregunta,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                        )

                        val imageUrl = "http://10.0.2.2:5000${currentQuestion.imatge}"
                        if (!currentQuestion.imatge.isNullOrEmpty()) {
                            Image(
                                painter = rememberImagePainter(imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(300.dp)
                                    .fillMaxWidth()
                                    .padding(bottom = 2.dp)
                            )
                        } else {
                            Text(
                                "No hay imagen disponible",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                                color = Color.White
                            )
                        }

                        if (shuffledOptions.isEmpty()) {
                            shuffledOptions = currentQuestion.opcions.shuffled()
                        }

                        shuffledOptions.forEach { opcion: Opcion ->
                            Button(
                                onClick = {
                                    moveToNextQuestion(opcion.correcta)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .padding(horizontal = 6.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF36538),
                                    contentColor = Color.White
                                ),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(opcion.resposta, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                        }
                    } else {
                        isGameFinished = true
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePlayScreenPreview() {
    GamePlayScreen(difficulty = 1, navController = rememberNavController())
}
