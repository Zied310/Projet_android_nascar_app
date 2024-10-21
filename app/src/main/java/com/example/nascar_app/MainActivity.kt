package com.example.nascar_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nascar_app.screens.NewsScreen
import com.example.nascar_app.ui.theme.Nascar_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nascar_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    mainAppScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun mainAppScreen(modifier: Modifier) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Nascar App")
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White
                    )
                )
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
                    ){
                        val titles = listOf("News", "Events", "Profile")
                        titles.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title, color = Color.Black) },
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index }
                            )
                        }
                    }

                    when(selectedTabIndex){
                        0 -> NewsScreen(modifier = modifier)
                    }
                }
            }
        )
    }


}