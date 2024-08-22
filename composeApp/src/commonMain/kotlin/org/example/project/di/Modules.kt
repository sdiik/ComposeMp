package org.example.project.di

import org.example.project.dependencies.MyRepository
import org.example.project.dependencies.MyRepositoryImp
import org.example.project.dependencies.MyViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformModule: Module

val sharedModule = module {
    singleOf(::MyRepositoryImp).bind<MyRepository>()
    viewModelOf(::MyViewModel)
}
