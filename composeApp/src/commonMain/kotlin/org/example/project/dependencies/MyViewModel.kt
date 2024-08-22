package org.example.project.dependencies

import androidx.lifecycle.ViewModel

class MyViewModel(
    private var repository: MyRepository
): ViewModel() {
    fun getHelloWorldString(): String {
        return  repository.helloWorld()
    }
}