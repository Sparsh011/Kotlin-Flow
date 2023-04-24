package com.example.flows.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flows.ui.theme.FlowsTheme
import com.example.flows.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowsTheme {
                val viewModel = viewModel<MainViewModel>()
                val time = viewModel.countdownFlow.collectAsState(initial = 15)
                Box(modifier = Modifier.fillMaxSize()){
                    Text(
                        text = time.value.toString(),
                        fontSize = 30.sp,
                        modifier =  Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

