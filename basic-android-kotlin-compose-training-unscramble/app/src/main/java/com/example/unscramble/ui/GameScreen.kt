package com.example.unscramble.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.unscramble.R
import com.example.unscramble.ui.theme.UnscrambleTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GameScreen(navController: NavController) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    var difficulty by remember { mutableStateOf(1) }

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
            modifier = Modifier
                .padding(mediumPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selecciona la dificultad:",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = difficulty.toFloat(),
                onValueChange = { difficulty = it.toInt() },
                valueRange = 1f..4f,
                steps = 2,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFF36538),
                    activeTrackColor = Color(0xFFF36538)
                )
            )
            Text(
                text = "Dificultad: $difficulty",
                color = Color.White,
                modifier = Modifier.padding(vertical = mediumPadding / 2),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
                    .padding(top = 8.dp),
                onClick = {
                    navController.navigate("game_play_screen/$difficulty")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF36538),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(R.string.play),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen(navController = rememberNavController())
    }
}
