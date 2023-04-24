package com.example.flows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val countdownFlow = flow<Int> {
        val startingValue = 15
        var currentValue = startingValue
        emit(startingValue) // so that it starts to emit at 10

        while (currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

//    Position of init block is important, make sure it is at the correct place.

    init {
//        collectFlow()
        restaurant()
    }

    private fun restaurant(){
        val flow = flow {
            delay(250L)
            emit("Snacks")
            delay(1000L)
            emit("Main Dish")
            delay(1000L)
            emit("Dessert")
        }

        viewModelScope.launch {
            flow.onEach {
                println("Flow: $it is delivered!")
            }
//                .buffer() // collects on a different coroutine
//                .conflate()
                .collect{
                    println("Flow: Now eating $it")
                    delay(1000L)
                    println("Flow: Finished eating $it")
                }
        }
    }

    private fun collectFlow(){
        val flow1 = flow {
            emit(1)
            delay(500L)
            emit(2)
        }

        viewModelScope.launch {
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }.collect{ value ->
                println("The value is $value")
            }


            countdownFlow
                .filter { time ->
                    time % 2 == 0
//                    Means we only want to collect even values
                }
                .map{ time ->
                    time * time
//                    squares the currentTime
                }
                .onEach { time ->
//                    It returns a flow
//                    println(time)
                }
                .collect{ time ->
//                println("The current timme is $time")
            }

//            In case of collectLatest, we get only The current time is 0 in the logcat. collectLatest cancels the previous emission if a new emission takes place. collect will emit old values also.
        }
    }
}













