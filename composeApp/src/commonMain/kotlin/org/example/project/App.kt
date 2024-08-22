package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import composemp.composeapp.generated.resources.Res
import composemp.composeapp.generated.resources.compose_multiplatform
import composemp.composeapp.generated.resources.hello_world
import kotlinx.coroutines.launch
import org.example.project.dependencies.DbClient
import org.example.project.dependencies.MyViewModel
import org.example.project.networking.InsultCensorClient
import org.example.project.utils.NetworkError
import org.example.project.utils.onError
import org.example.project.utils.onSuccess
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(client: InsultCensorClient) {
    MaterialTheme {
        KoinContext {
            NavHost(
                navController = rememberNavController(),
                startDestination = "home"
            ) {
                composable(route = "home") {
                    var viewModel = koinViewModel<MyViewModel>()

                    var censoredText by remember {
                        mutableStateOf<String?>(null)
                    }
                    var uncensoredText by remember {
                        mutableStateOf<String>("")
                    }
                    var isLoading by remember {
                        mutableStateOf(false)
                    }
                    var errorMessage by remember {
                        mutableStateOf<NetworkError?>(null)
                    }

                    val scope = rememberCoroutineScope()

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            text = viewModel.getHelloWorldString()
                        )
                        TextField(
                            value = uncensoredText,
                            onValueChange = { uncensoredText = it },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text("Uncensored tect")
                            }
                        )
                        Button(onClick = {
                            scope.launch {
                                isLoading = true
                                errorMessage = null

                                client.censorWords(uncensoredText)
                                    .onSuccess {
                                        censoredText = it
                                    }
                                    .onError {
                                        errorMessage = it
                                    }
                                isLoading = false
                            }
                        }) {
                            if(isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(15.dp),
                                    strokeWidth = 1.dp,
                                    color = Color.White
                                )
                            } else {
                                Text("Censor!")
                            }
                        }
                        censoredText?.let {
                            Text(it)
                        }
                        errorMessage?.let {
                            Text(
                                text = it.name,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}