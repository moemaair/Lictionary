package com.moemaair

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

suspend fun FlowPlayGround() {
    val myFlow = flow {
        for (i in 1..5) {
            emit(i)
        }
    }

    myFlow.collect {
        println(it)
    }

}

fun main() {
   runBlocking {
       FlowPlayGround()
   }
}