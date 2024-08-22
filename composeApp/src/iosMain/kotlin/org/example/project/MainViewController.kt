package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.example.project.di.initKoin
import org.example.project.networking.InsultCensorClient
import org.example.project.networking.createHttpClient

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
        client = remember {
            InsultCensorClient(createHttpClient(Darwin.create()))
        }
    )
}