package com.example.cine_guide.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cine_guide.R

@Composable
fun ImageWithTextOverlay(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        // Replace with your image resource
        Image(
            painter = painterResource(id = R.drawable.strange),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Semi-transparent overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
        )
        // Text overlay
        Text(
            text = "Get to know",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 350.dp, start = 24.dp)
        )
        Text(
            text = "About your",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 400.dp, start = 24.dp)
        )
        Text(
            text = "Fabourite T.V",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 450.dp, start = 24.dp)
        )
        Text(
            text = "Shows and",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 500.dp, start = 24.dp)
        )
        Text(
            text = "Movies.",
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 550.dp, start = 24.dp)
        )

        Button(
            onClick = {
                navController.navigate("activity")
            }, modifier = Modifier.padding(start = 45.dp, end = 45.dp, top = 690.dp)
        ) {
            Text(
                "Get Started",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}