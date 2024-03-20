package com.example.mapd721_a3_nkemjikaobi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapd721_a3_nkemjikaobi.ui.theme.MAPD721A3NkemjikaObiTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A3NkemjikaObiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }
                        composable("transition_animation") {
                            TransitionAnimationScreen(navController = navController)
                        }
                        composable("scale_animation") {
                            ScaleAnimationScreen(navController = navController)
                        }
                        composable("infinite_animation") {
                            InfiniteAnimationScreen(navController = navController)
                        }
                        composable("exit_animation") {
                            ExitAnimationScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("transition_animation") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Transition Animation", color = Color.White)
        }
        Button(
            onClick = { navController.navigate("scale_animation") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Scale Animation", color = Color.White)
        }
        Button(
            onClick = { navController.navigate("infinite_animation") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Infinite Animation", color = Color.White)
        }
        Button(
            onClick = { navController.navigate("exit_animation") },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Exit Animation", color = Color.White)
        }
    }
}

@Composable
fun TransitionAnimationScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val rocketPosition = remember { Animatable(0f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Rocket Image with position animation
        val rocketModifier = Modifier
            .padding(bottom = (200.dp * (1f - rocketPosition.value)))
            .fillMaxWidth()
        Image(
            painter = painterResource(id = R.drawable.rocket),
            contentDescription = null,
            modifier = rocketModifier.size(100.dp)
        )

        Button(
            onClick = {
                if (visible) {
                    scope.launch {
                        rocketPosition.animateTo(0f, tween(durationMillis = 2000))
                    }
                } else {
                    scope.launch {
                        rocketPosition.animateTo(1f, tween(durationMillis = 2000))
                    }
                }
                visible = !visible
            },
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(if (visible) "Launch Rocket" else "Land Rocket", color = Color.White)
        }
    }
}


@Composable
fun ScaleAnimationScreen(navController: NavController) {
    var isScaled by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LaunchedEffect(isScaled) {
            if (isScaled) {
                scale.animateTo(3f)
            } else {
                scale.animateTo(1f)
            }
        }

        val scaledModifier = Modifier.scale(scale.value)
        Button(
            onClick = {
                isScaled = !isScaled
            },
            modifier = Modifier
                .padding(16.dp)
                .then(scaledModifier)
        ) {
            Text(if (isScaled) "Click me to reset" else "Click me to animate")
        }
    }
}


@Composable
fun InfiniteAnimationScreen(navController: NavController) {
    // Implement infinite animation screen UI here
}

@Composable
fun ExitAnimationScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    var buttonText by remember { mutableStateOf("Press for enter animation") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom, // Align items to the bottom
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (!visible) {
                    visible = true
                    buttonText = "Press for exit animation"
                } else {
                    visible = false
                    buttonText = "Press for enter animation"
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(buttonText)
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom, // Align items to the bottom
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.robot),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721A3NkemjikaObiTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController = navController)
            }
            composable("transition_animation") {
                TransitionAnimationScreen(navController = navController)
            }
            composable("scale_animation") {
                ScaleAnimationScreen(navController = navController)
            }
            composable("infinite_animation") {
                InfiniteAnimationScreen(navController = navController)
            }
            composable("exit_animation") {
                ExitAnimationScreen(navController = navController)
            }
        }
    }
}