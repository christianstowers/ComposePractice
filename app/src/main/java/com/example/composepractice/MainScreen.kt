package com.example.composepractice

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen() {
    val viewModel: MyViewModel = viewModel()

    CompositionLocalProvider(LocalViewModel provides viewModel) {
        AppContent()
    }
}

@Composable
fun AppContent() {
    val viewModel = LocalViewModel.current
    var message by remember { mutableStateOf("Press the button") }

    // Using LaunchedEffect to collect SharedFlow events
    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            message = event
        }
    }

    // Box layout for UI structuring
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = message, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { viewModel.sendEvent("Hello from SharedFlow!") }) {
                Text("Trigger Event")
            }
        }
    }
}
