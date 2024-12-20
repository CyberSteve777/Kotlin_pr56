package me.cybersteve.kotlin_pr5

import org.koin.dsl.module

val myKoinModule = module {
    single { CustomVM() }
}