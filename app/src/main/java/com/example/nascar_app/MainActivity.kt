package com.example.nascar_app

import EventsScreen
import LoginScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nascar_app.data.sharedPreference.LoginPreferenceManager
import com.example.nascar_app.screens.AddEventScreen
import com.example.nascar_app.screens.NewsScreen
import com.example.nascar_app.screens.ProfileScreen
import com.example.nascar_app.ui.theme.Nascar_appTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nascar_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }




    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun mainAppScreen(modifier: Modifier, navController: NavController, context : Context) {
        var selectedTabIndex by remember { mutableStateOf(0) }
        val context = LocalContext.current // Get the current context
        val loginPreferenceManager = LoginPreferenceManager(context)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Nascar App")
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = {
                            navController.navigate("login_form")
                            loginPreferenceManager.clearLoginDetails()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ExitToApp,
                                contentDescription = "Logout",
                                tint = Color.White // Change to your desired icon color
                            )
                        }
                    }
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
                        1 -> EventsScreen(modifier = modifier, navController = navController, context) // Pass navController
                        2 -> ProfileScreen(modifier = modifier, context = context)
                    }
                }
            }
        )
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        val context = LocalContext.current // Get the current context
        val loginPreferenceManager = LoginPreferenceManager(context)
        val (savedEmail, savedPassword) = loginPreferenceManager.getLoginDetails()


        if (savedEmail != null && savedPassword != null) {
            LaunchedEffect(Unit) {
                navController.navigate("main_app_screen") {
                    popUpTo("login_form") { inclusive = true }
                }
            }
        } else {
            // Navigate to the login screen
            LaunchedEffect(Unit) {
                navController.navigate("login_form")
            }
        }

        NavHost(navController = navController, startDestination = "login_form") {
            composable("login_form") { LoginScreen(modifier = Modifier, navController = navController, context = context) }
            composable("events_screen") { EventsScreen(modifier = Modifier, navController = navController, context = context) }
            composable("main_app_screen") { mainAppScreen(modifier = Modifier, navController = navController, context = context) }
            composable("add_event_screen") { AddEventScreen(modifier = Modifier, navController) }
        }
    }




}